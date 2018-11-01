package com.application.catny.controller;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.application.catny.entity.Post;
import com.application.catny.entity.Reply;
import com.application.catny.mapper.ReplyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class ReplyController {
    @Autowired
    private ReplyMapper replyMapper;
    
    @RequestMapping("/reply")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页码",
                    dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每页显示条数",
                    dataType = "Integer", paramType = "query")
    })
    public PageInfo<Reply> getpostcontent(@RequestParam("id") String idstr,@RequestParam(defaultValue = "0") Integer page,
                              @RequestParam(value = "10",defaultValue = "0") Integer size,Model model) {
        int id = Integer.parseInt(idstr);
        List<Reply> list = replyMapper.getReplyList(id);
        PageHelper.startPage(page,size); 
        PageInfo<Reply> replyPageInfo = new PageInfo<>(list);
        return replyPageInfo;
    }
    
    @RequestMapping("/getPost")
    public Post getPost(int id) {
    	Post post = replyMapper.getPost(id);
    	return post;//实体类
    }
    
    @RequestMapping("getSubReplyList")//二级回复
    public List<Reply> getSubReplyList(@RequestParam("parentId") Integer parentId){
    	return replyMapper.getSubReplyList(parentId);
    }
    
    @RequestMapping("/createReply")
    public String createReply(@RequestParam("postId") String post,@RequestParam("authorId") String author,
                        @RequestParam("content") String content,@RequestParam("time")String timestr,
                        @RequestParam("parentId") String beingRepliedId,Model model){
        int postId = Integer.parseInt(post);
        int authorId = Integer.parseInt(author);
        
        Date time = Date.valueOf(timestr);
        Reply reply = new Reply();
        reply.setPostId(postId);
        reply.setAuthorId(authorId);
        reply.setContent(content);
        reply.setTime(time);
        reply.setMaskName(distributeMaskName(postId, authorId));
        if(!beingRepliedId.equals("0")) {//子回复
        	int parentId = Integer.parseInt(beingRepliedId);
        	reply.setParentId(parentId);
        	String ref = replyMapper.getMaskName(parentId);
        	reply.setReference(ref);
        }
        int i = replyMapper.createReply(reply);
        if(i>0){
           return "回复成功";
        }else {
            return "错误"; 
        }
    }
    
    private String distributeMaskName(int postId,int authorId) {
    	String maskName;
    	if(replyMapper.getAuthorIdFromPost(postId) == authorId) {//楼主
    		maskName = "匿名用户0";
    	}else {
    		if(replyMapper.getAuthorIdFromReply(postId) == authorId) {//已发言并分配
    			maskName = replyMapper.getMaskNameFromReply(postId,authorId);
    		}else {
    			int n = replyMapper.countReplier(postId) + 1;
    			maskName = "匿名用户" + Integer.toString(n);
      		}
    	}  	
    	return maskName;
    }
}
