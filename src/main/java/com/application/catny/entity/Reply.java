package com.application.catny.entity;

import java.sql.Date;

public class Reply {
    private int postId;
    private int id;
    private int authorId;
    private String content;
    private Date time;
    private String likes;
    private String dislikes;
    private String reference;
    private int parentId;
    private String maskName;//为用户随机分配的id名

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
    	return time;
    }
    
    public void setTime(Date time) {
    	this.time = time;
    }
    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getDislikes() {
        return dislikes;
    }

    public void setDislikes(String dislikes) {
        this.dislikes = dislikes;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = "@" + reference;
    }
    
    public int getParentId() {
    	return parentId;
    }
    
    public void setParentId(int parentId) {
    	this.parentId = parentId;
    }
    
    public String getMaskName() {
    	return maskName;
    }
    
    public void setMaskName(String maskName) {
    	this.maskName = maskName;
    }
}
