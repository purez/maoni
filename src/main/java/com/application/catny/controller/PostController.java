package com.application.catny.controller;

import com.application.catny.entity.Post;
import com.application.catny.entity.Reply;
import com.application.catny.mapper.PostMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostMapper postMapper;
    @RequestMapping("/post")
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码",
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",
                    dataType = "Integer", paramType = "query")
    })
    JSONObject getpostcontent(@RequestParam("id") String idstr,@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(value = "10",defaultValue = "0") Integer size,Model model) {
        JSONObject jsonObject = new JSONObject();
        int id = Integer.parseInt(idstr);
        Post post = postMapper.getPost(id);
        List<Reply> list = postMapper.getReply(id);
        PageHelper.startPage(page,size);
        PageInfo<Reply> replyPageInfo = new PageInfo<>(list);
        jsonObject.put("post",post);
        jsonObject.put("reply",replyPageInfo);
        return jsonObject;
    }

    @RequestMapping("/createre")
    @ResponseBody
    boolean createreply(@RequestParam("postid") String post,@RequestParam("authorid") String author,
                        @RequestParam("content") String content,@RequestParam("reference") String reference,Model model){
        int postid = Integer.parseInt(post);
        int authorid = Integer.parseInt(author);
        Reply reply = new Reply();
        reply.setPostid(postid);
        reply.setAuthorId(authorid);
        reply.setContent(content);
        reply.setReference(reference);
        int i = postMapper.createreply(reply);
        if(i>0){
            model.addAttribute("success","发布成功");
            return true;
        }else {
            model.addAttribute("fail","错误");
            return false;
        }
    }
}
