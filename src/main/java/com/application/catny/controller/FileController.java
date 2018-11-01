package com.application.catny.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

@RequestMapping("/file")
@RestController
public class FileController {

    @RequestMapping("")
    public ModelAndView goUploadImg() {
        //跳转到 templates 目录下的 uploadimg.html
        return new ModelAndView("uploadimg");
    }

    @RequestMapping("/s")
    @ResponseBody
    public String goUploadImg(HttpServletRequest request) {
        //跳转到 templates 目录下的 uploadimg.html
        return request.getSession().getServletContext().getRealPath("/");
    }
    //处理文件上传
    @RequestMapping(value="/testuploadimg", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImg(@RequestParam("file") MultipartFile[] files,
                     HttpServletRequest request) {
        String filePath = "/home/tomcat/apache-tomcat-8.5.8/webapps/ff/";//request.getSession().getServletContext().getRealPath("file/");
        for(MultipartFile f: files) {
            try {
                uploadFile(f.getInputStream(), filePath, f.getOriginalFilename());
            } catch (Exception e) {
                return e.getMessage();
            }
        }
        return "upload success";
    }

    public static void uploadFile(InputStream in, String filePath, String fileName) throws Exception {
        if(filePath.startsWith("C")){
            filePath = "D:\\";
        }
        File path = new File(filePath);
        File f = new File(filePath + fileName);
        if(!path.exists()){
            path.mkdirs();
        }
        if(f.exists()){
            f.delete();
        }
        FileOutputStream out = new FileOutputStream(f);
        byte[] buff = new byte[1024];
        while(-1 != in.read(buff)){
            out.write(buff);
        }
        out.flush();
        out.close();
    }

}
