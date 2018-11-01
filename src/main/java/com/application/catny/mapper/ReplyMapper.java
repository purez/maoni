package com.application.catny.mapper;
import com.application.catny.entity.Post;
import com.application.catny.entity.Reply;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface ReplyMapper {
    
    @Select("select * from reply where postid=#{postId} order by time desc")
    List<Reply> getReplyList(@Param("postId") Integer postId);
    
    @Select("select * from reply where parentid=#{parentId} order by time")
    List<Reply> getSubReplyList(@Param("parentId") int parentId);
    
    @Select("select * from post where id=#{id}")
    Post getPost(@Param("id") int id);
    
    @Select("select maskName from reply where id=#{parentId}")
    String getMaskName(@Param("parentId") int parentId);
    
    @Select("select authorid from post where id=#{postId}")
    int getAuthorIdFromPost(@Param("postId") int postId);
    
    @Select("select authorid from post where id=#{postId}")
    int getAuthorIdFromReply(@Param("postId") int postId);
    
    @Select("select maskName from reply where postid=#{postId} and authorid=#{authorId}")
    String getMaskNameFromReply(@Param("postId") int postId, @Param("authorId") int authorId);
    
    @Select("select count(distinct maskName) from reply where postid=#{postId}")
    int countReplier(@Param("postId") int postId);
    
    @Insert("insert into reply(postid,authorid,content,time,parentid,reference,maskName) "
    		+ "values(#{postId},#{authorId},#{content},#{time},#{parentId},#{reference},#{maskName})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int createReply(Reply reply);
}
