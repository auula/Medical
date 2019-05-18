package com.ryan.sms.medical.view;

import com.ryan.sms.medical.mapper.UserMapper;
import com.ryan.sms.medical.pojo.User;
import com.ryan.sms.medical.utils.JsonData;
import com.ryan.sms.medical.utils.JsonUtil;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/open")
public class OpenView {


    @Autowired
    UserMapper userMapper;


    @ResponseBody
    @GetMapping("/test")
    public List<User> test() {
        int insert = userMapper.insert("admin02", "admin02");
        if (insert == 1) {
            System.out.println("oj");
        }
        return userMapper.getAll();
    }

    @PostMapping("/reg")
    public void userRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("account");
        String password = request.getParameter("password");
        String vcode = request.getParameter("vcode");
        // 获取session中的验证码
        String sessionCode = (String) request.getSession().getAttribute("_captcha");
        // 判断验证码
        if (vcode == null || !sessionCode.equals(vcode)) {
            JsonUtil.outJson(response, new JsonData().build(-2000, "验证码错误!"));
            return;
        }
        if (userMapper.getOne(username) != null) {
            JsonUtil.outJson(response, new JsonData().build(-2000, "用户已经存在～"));
            return;
        }
        if (userMapper.insert(username, password) > 0) {
            JsonUtil.outJson(response, new JsonData().build(2000, "用户注册成功～"));
        }

    }


    @PostMapping("/login")
    public void userLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("account");
        String password = request.getParameter("password");
        String vcode = request.getParameter("vcode");
        // 获取session中的验证码
        String sessionCode = (String) request.getSession().getAttribute("_captcha");
        // 判断验证码
        if (vcode == null || !sessionCode.equals(vcode)) {
            JsonUtil.outJson(response, new JsonData().build(-2000, "验证码错误!"));
            return;
        }
        int type_id = Integer.valueOf(request.getParameter("t_id"));
        switch (type_id) {
            case 1:
                if (userMapper.login(username, password) == null) {
                    JsonUtil.outJson(response, new JsonData().build(-2000, "账号或者密码错误！"));
                } else {
                    request.getSession().setAttribute("LOGIN_USER",username);
                    request.getSession().setAttribute("USER_TYPE",type_id);
                    JsonUtil.outJson(response, new JsonData().build(2000, "登陆成功～").put("path","/user/"));
                }
                break;
            case 2:
                if (!username.equals("admin")&&password.equals("admin")) {
                    JsonUtil.outJson(response, new JsonData().build(-2000, "账号或者密码错误！"));
                }else {
                    request.getSession().setAttribute("LOGIN_USER",username);
                    request.getSession().setAttribute("USER_TYPE",type_id);
                    JsonUtil.outJson(response, new JsonData().build(2000, "登陆成功～").put("path","/admin/"));
                }
                break;
            default:
                JsonUtil.outJson(response, new JsonData().build(-2000, "非法请求～"));
        }
    }

    private String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                + "/";
    }

    @RequestMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        // 设置宽、高、位数
        //CaptchaUtil.out(50, 25, 4, req, resp);


        // 设置请求头为输出图片类型
        CaptchaUtil.setHeader(response);

        // 三个参数分别为宽、高、位数
        GifCaptcha Captcha = new GifCaptcha(50, 25, 4);

        // 设置字体
        Captcha.setFont(new Font("Verdana", Font.PLAIN, 18));  // 有默认字体，可以不用设置

        // 设置类型，纯数字、纯字母、字母数字混合
        Captcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        // 验证码存入session
        request.getSession().setAttribute("_captcha", Captcha.text());

        // 输出图片流
        try {
            Captcha.out(response.getOutputStream());
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

}
