package com.ryan.sms.medical.mapper;
import com.ryan.sms.medical.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM t_users")
    List<User> getAll();


    @Select("SELECT * FROM t_users WHERE status = 0")
    List<User> getAllAuditUser();


    @Insert("insert into t_users(username,password) values(#{username}, #{password})")
    int insert(@Param("username")String username,@Param("password")String password);


    @Select("SELECT * FROM t_users WHERE username = #{username}")
    User getOne(@Param("username") String username);

    @Select("SELECT * FROM t_users WHERE username = #{username} AND password = #{password}")
    User login(@Param("username") String username,@Param("password") String password);

    @Update("update t_users set password=#{password2} where username=#{username}")
    void resetPwd(@Param("username") String username,@Param("password2")String password2);

    @Update("update t_users set money=#{money} where username=#{username}")
    void topUp(@Param("username") String username,@Param("money")Integer money);
}
