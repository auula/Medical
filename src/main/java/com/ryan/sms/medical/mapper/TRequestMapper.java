package com.ryan.sms.medical.mapper;

import com.ryan.sms.medical.pojo.TRequest;
import com.ryan.sms.medical.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TRequestMapper {

    @Insert("insert into t_request(username,picUri,createTime) values(#{username}, #{picUri},#{createTime})")
    int insert(@Param("username") String username,
               @Param("picUri") String picUri,
               @Param("createTime") String createTime
    );

    @Select("SELECT * FROM t_request WHERE username = #{username}")
    List<TRequest> getOne(@Param("username") String username);


    @Select("SELECT * FROM t_request WHERE status = 0")
    List<TRequest> getAll();

    @Update("UPDATE t_request SET status=1 WHERE rid=#{rid}")
    void action(@Param("rid") Integer rid);
}
