package com.ryan.sms.medical.mapper;

import com.ryan.sms.medical.pojo.news;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NewsMapper {

    @Insert("insert into t_news(title,content,createTime) values(#{title}, #{content},#{createTime})")
    int insert(@Param("title") String title,
               @Param("content") String content,
               @Param("createTime") String createTime
    );


    @Select("SELECT * FROM t_news")
    List<news> getAll();

    @Delete("DELETE FROM t_news WHERE nid=#{nid}")
    void delete(@Param("nid") int nid);
}
