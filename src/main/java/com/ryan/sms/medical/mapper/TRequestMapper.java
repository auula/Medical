package com.ryan.sms.medical.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface TRequestMapper {

    @Insert("insert into t_request(username,picUri,createTime) values(#{username}, #{picUri},#{createTime})")
    int insert(@Param("username") String username,
               @Param("picUri") String picUri,
               @Param("createTime") String createTime
    );
}
