package com.application.catny.controller;


import com.application.catny.entity.User;
import com.application.catny.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.List;
import java.util.Properties;

@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserMapper u;

    @RequestMapping("/logined")
    public String index(HttpServletRequest r){
        String str = "This is your frist coming!";
        for(Cookie c: r.getCookies()){

            if("logined".equals(c.getName())){
                str = "you are " + u.getUsersFromLogined(c.getValue()).getUsername();
            }
        }
        return  str;
    }

    @RequestMapping("/addcookie")
    public String addcookie(String name,String value, HttpServletResponse r){
        r.addCookie(new Cookie(name,value));
        return "ok";
    }

    @RequestMapping("/register")
    public String register(String email, String password){
        String url = "http://ridm.xin/zhaoliying/activate?account=" + email + "&password" + password;
        MailSend(email,"猫匿注册账户验证","点击链接确认邮箱:<br> " + url);
        return "";
    }
    @RequestMapping("/activate")
    public String activate(String account,String password){
        User user = new User();
        if(null != u.getUserFromAccount(account)){
            return "该账号已被注册";
        }
        user.setAccount(account);
        user.setPassword(password);
        u.insert(user);
        return "success";
    }


    @RequestMapping("/login")
    public User Login(String username,String password){
        return u.checkUsers(username,password);
    }

    @RequestMapping("/json")
    public List<User> j(){
        return u.selectUsers();
    }

    @RequestMapping("/cookies")
    public User c(HttpServletRequest r){
        String username = "";
        String password = "";
        for(Cookie c: r.getCookies()){
            if(c.getName() == "username"){
                username = c.getValue();
            }
            if(c.getName() == "password"){
                password = c.getValue();
            }
        }
        return u.checkUsers(username,password);
    }

    private String MD5(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(s.getBytes("utf-8"));
            return toHex(bytes);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String toHex(byte[] bytes) {
        final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();
        StringBuilder ret = new StringBuilder(bytes.length * 2);
        for (int i=0; i<bytes.length; i++) {
            ret.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);
            ret.append(HEX_DIGITS[bytes[i] & 0x0f]);
        }
        return ret.toString();
    }

    public static void MailSend(String email,String title,String msg) {
        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        //此处填写SMTP服务器
        props.put("mail.smtp.host", "smtp.qq.com");
        //端口号，QQ邮箱给出了两个端口，但是另一个我一直使用不了，所以就给出这一个587
        props.put("mail.smtp.port", "587");
        // 此处填写你的账号
        props.put("mail.user", "814015815@qq.com");
        // 此处的密码就是前面说的16位STMP口令
        props.put("mail.password", "xsyhoxfrtsklbbja");

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = null;
        try {
            form = new InternetAddress(props.getProperty("mail.user"));

            message.setFrom(form);

            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(email);
            message.setRecipient(MimeMessage.RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject(title);

            // 设置邮件的内容体
            message.setContent(msg, "text/html;charset=UTF-8");

            // 最后当然就是发送邮件啦
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/cookielogin")
    public String cookieLogin(HttpServletRequest req,HttpServletResponse res){
        String username = null;
        String passwordMD5 = null;
        String logined = null;
        User user = null;
        for(Cookie c: req.getCookies()){
            if(c.getName().equals("username")){
                username = c.getValue();
                if(null == (user = u.getUserFromUsername(username))){
                    return "没有这个用户";
                }
            }
            if(c.getName().equals("password")){
                passwordMD5 = c.getValue();
            }
        }
        if(null == username || null == passwordMD5){
            return "缺少登录参数";
        }
        if(MD5(user.getPassword()).equals(passwordMD5)){
            logined = MD5(username + String.valueOf(System.currentTimeMillis()));
            res.addCookie(new Cookie("logined",logined));
            u.updataLoginedByUsername(username,logined);
            return "login success";
        }else{
            return "login failed";
        }
    }


}
