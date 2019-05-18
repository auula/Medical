package com.ryan.sms.medical.mapper;


import com.ryan.sms.medical.pojo.refund;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface RefundMapper {

    @Insert("insert into t_refund(username,requestTime) values(#{username}, #{requestTime})")
    int insert(@Param("username") String username,
               @Param("requestTime") String requestTime
    );

    @Select("SELECT * FROM t_refund WHERE status=0")
    List<refund> getAll();

    @Update("UPDATE t_refund SET status=1 WHERE uid=#{id}")
    void action(@Param("id") Integer id);

}
