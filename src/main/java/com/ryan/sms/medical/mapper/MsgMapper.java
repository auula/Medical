package com.ryan.sms.medical.mapper;


import com.ryan.sms.medical.pojo.Msg;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MsgMapper {

    @Insert("insert into t_msg(username,message,createTime) values(#{username}, #{message},#{createTime})")
    int insert(@Param("username") String username,
               @Param("message") String message,
               @Param("createTime") String createTime
    );

    @Select("SELECT * FROM t_msg WHERE username = #{username}")
    List<Msg> getMsgs(@Param("username") String username);

    @Delete("DELETE FROM t_msg WHERE mid=#{mid}")
    void delete(@Param("mid") int mid);
}
