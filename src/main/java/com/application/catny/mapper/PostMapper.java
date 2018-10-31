package com.application.catny.mapper;

import com.application.catny.entity.Partition;
import com.application.catny.entity.Post;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PostMapper {
    @Select("select * from partitions")
    List<Partition> getPartitions();

    @Select("select post.*,MAX(reply.time)AS lasttime from post,reply where post.id=reply.postid and belongid=#{belongid}" +
            " GROUP BY post.id ORDER BY lasttime DESC")
    List<Post> getAllPost(@Param("belongid") int belongid);

    @Insert("insert into post(title,content,authorid,time) values(#{title},#{content},#{authorid},#{time})")
    @Options(useGeneratedKeys = true,keyProperty = "id",keyColumn = "id")
    int createPost(Post post);
}
