package com.ryan.sms.medical.utils;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

    /**
     * response相应输出json字符串
     * @param response
     * @param data 返回数据实体
     */
    public static void outJson(HttpServletResponse response,Object data) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(JSON.toJSONString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}