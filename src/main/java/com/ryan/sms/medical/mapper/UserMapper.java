package com.ryan.sms.medical.mapper;
import com.ryan.sms.medical.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {

    @Select("SELECT * FROM t_users")
    List<User> getAll();


    @Select("SELECT * FROM t_users WHERE status = 0")
    List<User> getAllAuditUser();


    @Insert("INSERT INTO t_users(username,password) VALUES(#{username}, #{password})")
    int insert(@Param("username")String username,@Param("password")String password);


    @Select("SELECT * FROM t_users WHERE username = #{username}")
    User getOne(@Param("username") String username);

    @Select("SELECT * FROM t_users WHERE username = #{username} AND password = #{password}")
    User login(@Param("username") String username,@Param("password") String password);

    @Update("UPDATE t_users SET password=#{password2} WHERE username=#{username}")
    void resetPwd(@Param("username") String username,@Param("password2")String password2);

    @Update("UPDATE t_users SET money=#{money} WHERE username=#{username}")
    void topUp(@Param("username") String username,@Param("money")Integer money);


    @Update("UPDATE t_users SET status=1 WHERE uid=#{uid}")
    void audit(@Param("uid") Integer uid);

    @Update("UPDATE t_users SET money=0 WHERE username=#{username}")
    void down(@Param("username") String username);


    @Delete("DELETE FROM t_users WHERE uid=#{uid}")
    void del(@Param("uid") Integer uid);
}
