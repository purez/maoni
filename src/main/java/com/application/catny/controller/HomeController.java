package com.application.catny.controller;

import com.application.catny.entity.Partition;
import com.application.catny.entity.Post;
import com.application.catny.mapper.HomeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private HomeMapper homeMapper;

    @RequestMapping("/home.do")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码",
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",
                    dataType = "Integer", paramType = "query")
    })
    String getHome(@RequestParam(value = "belongid",defaultValue = "1") String belong,@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(value = "10",defaultValue = "0") Integer size,Model model){
        int belongid = Integer.parseInt(belong);
        JSONObject jsonObject = new JSONObject();
        List<Partition> partitions = homeMapper.getPartitions();
        List<Post> pageposts = homeMapper.getAllPost(belongid);
        PageHelper.startPage(page,size);
        PageInfo<Post> postPageInfo = new PageInfo<>(pageposts);
        jsonObject.put("partition",partitions);
        jsonObject.put("pagepost",postPageInfo);
        model.addAttribute("json",jsonObject);
        return "home.do";
    }

    @RequestMapping("/home")
    String Home(Model model){
        JSONObject jsonObject = new JSONObject();
        List<Partition> partitions = homeMapper.getPartitions();
        jsonObject.put("partition",partitions);
        model.addAttribute("json",jsonObject);
        return "home";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    boolean postsuc(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("time")
                   String timestr,@RequestParam("authorid") String author,@RequestParam("belongid") String belong,Model model){
        Post post = new Post();
        Date time = Date.valueOf(timestr);
        int authorid = Integer.parseInt(author);
        int belongid = Integer.parseInt(belong);
        post.setTitle(title);
        post.setContent(content);
        post.setTime(time);
        post.setAuthorid(authorid);
        post.setBelongid(belongid);
        int i =homeMapper.createPost(post);
        if(i>0) {
            model.addAttribute("success","发布成功");
            return true;
        }else{
            model.addAttribute("fail","错误");
            return false;
        }
    }
}
