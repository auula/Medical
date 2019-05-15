package com.ryan.sms.medical.mapper;

import com.ryan.sms.medical.pojo.Bill;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BillMapper {

    @Select("SELECT * FROM t_bill")
    List<Bill> getAll();




    @Insert("INSERT INTO t_bill(username,money,time) VALUES(#{username}, #{money},#{time})")
    int up(
            @Param("username")String username,
            @Param("money")Integer money,
            @Param("time") String time
    );

    @Insert("INSERT INTO t_bill(username,money,time,top) VALUES(#{username}, #{money},#{time},#{top})")
    int down(
            @Param("username")String username,
            @Param("money")Integer money,
            @Param("time") String time,
            @Param("top") Integer top
    );


    @Delete("DELETE FROM t_bill WHERE bid=#{bid}")
    void del(@Param("bid") Integer bid);

}
