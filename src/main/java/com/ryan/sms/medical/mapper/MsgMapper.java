package com.ryan.sms.medical.mapper;


import com.ryan.sms.medical.pojo.Msg;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MsgMapper {

    @Insert("insert into t_msg(username,message,createTime) values(#{username}, #{message},#{createTime})")
    int insert(@Param("username") String username,
               @Param("message") String message,
               @Param("createTime") String createTime
    );

    @Select("SELECT * FROM t_msg WHERE username = #{username}")
    List<Msg> getMsgs(@Param("username") String username);

    @Select("SELECT * FROM t_msg")
    List<Msg> getAllMsg();

    @Delete("DELETE FROM t_msg WHERE mid=#{mid}")
    void delete(@Param("mid") int mid);

    @Update("UPDATE t_msg SET reply=#{reply} WHERE mid=#{mid}")
    void reply(@Param("reply") String reply,@Param("mid")Integer mid);
}
