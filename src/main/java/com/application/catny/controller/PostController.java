package com.application.catny.controller;

import com.application.catny.entity.Partition;
import com.application.catny.entity.Post;
import com.application.catny.mapper.PostMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostMapper homeMapper;

    //获取分页帖子列表，帖子按最新回复从上到下排序
    @RequestMapping("/postlist")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码",
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",
                    dataType = "Integer", paramType = "query")
    })
    public PageInfo<Post> getHome(@RequestParam(value = "belongid",defaultValue = "1") String belong,@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(value = "10",defaultValue = "0") Integer size){
        int belongid = Integer.parseInt(belong);
        List<Post> pageposts = homeMapper.getAllPost(belongid);
        PageHelper.startPage(page,size);
        PageInfo<Post> postPageInfo = new PageInfo<>(pageposts);
        return postPageInfo;
    }

    //获取分区
    @RequestMapping("/home")
     public List<Partition> Home(){
        List<Partition> partitions = homeMapper.getPartitions();
        return partitions;
    }

    //发帖
    @RequestMapping(value = "/createPost",method = RequestMethod.POST)
    public String postsuc(@RequestParam("title") String title, @RequestParam("content") String content, @RequestParam("time")
                   String timestr,@RequestParam("authorid") String author,@RequestParam("belongid") String belong){
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
            return "发布成功";
        }else{
            return "发布失败";
        }
    }
}
