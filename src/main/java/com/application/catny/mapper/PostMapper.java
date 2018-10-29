package com.application.catny.mapper;

import com.application.catny.entity.Post;
import com.application.catny.entity.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select * from post where id=#{id}")
    Post getPost(@Param("id") int id);
    @Select("select * from reply where postid=#{postid}")
    List<Reply> getReply(@Param("postid") int postid);
    @Insert("insert into reply(postid,authorid,content,reference) values(#{postid},#{authorid},#{content},#{reference})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int createreply(Reply reply);
}
