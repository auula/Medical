package com.ryan.sms.medical.view;

import com.ryan.sms.medical.mapper.*;
import com.ryan.sms.medical.pojo.Msg;
import com.ryan.sms.medical.pojo.User;
import com.ryan.sms.medical.pojo.news;
import com.ryan.sms.medical.utils.JsonData;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserView {

    @Autowired
    HttpServletRequest request;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MsgMapper msgMapper;

    @Autowired
    TRequestMapper tRequestMapper;


    @Autowired
    BillMapper billMapper;

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    RefundMapper refundMapper;

    //获取文件存储路径
    @Value("${filePath}")
    private String filePath;

    @GetMapping("/msgList.html")
    public String toMegList(Model md) {
        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        List<Msg> admin = msgMapper.getMsgs(login_user);
        md.addAttribute("msgs", admin);
        return "user/msgList";
    }


    @GetMapping("/update.html")
    public String toUpdate() {
        return "user/update";
    }

    @GetMapping("/message.html")
    public String toMessage() {
        return "user/message";
    }

    @GetMapping("/news.html")
    public String toNews(Model md) {
        List<news> all = newsMapper.getAll();
        md.addAttribute("news", all);
        return "public/news";
    }

    @GetMapping("/resetPwd.html")
    public String resetPwd() {
        return "user/resetPwd";
    }

    @GetMapping("/top_up.html")
    public String top_UP() {
        return "user/top-up";
    }


    @ResponseBody
    @PostMapping("/updateFile")
    public JsonData updateFile(@RequestParam MultipartFile file) {
        //指定本地文件夹存储图片
        String savepath = filePath;
        //获取文件名
        String fileName = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //重新生成文件名
        fileName = UUID.randomUUID() + suffixName;
        System.out.println(savepath + fileName);
        try {
            String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
            //将图片保存到static文件夹里
            file.transferTo(new File(savepath + fileName));
            tRequestMapper.insert(login_user, fileName, new Date().toLocaleString());
            return new JsonData().build(2000, "凭证上传成功～").put("pic", fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonData().build(-2000, "凭证上传失败～");
        }
    }

    @GetMapping("/pic/{uuid}")
    public void getPic(@PathVariable String uuid, HttpServletResponse response) {
        if (uuid == null) {
            return;
        }
        //指定本地文件夹存储图片
        String Filepath = filePath + uuid;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(Filepath));
            ServletOutputStream outputStream = response.getOutputStream();
            // 10.流对拷
            IOUtils.copy(fileInputStream, outputStream, 2048);
            IOUtils.closeQuietly(fileInputStream, outputStream);
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }


    @GetMapping("/reqList.html")
    public String toReqList(Model model) {
        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        model.addAttribute("reqList", tRequestMapper.getOne(login_user));
        return "user/reqList";
    }

    @GetMapping("/my.html")
    public String toMy(Model md) {
        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        User one = userMapper.getOne(login_user);
        md.addAttribute("user", one);
        return "user/my";
    }


    @ResponseBody
    @PostMapping("/refund")
    public JsonData refund() {
        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        int insert = refundMapper.insert(login_user, new Date().toLocaleString());
        if (insert > 0) {
            return new JsonData().build(2000, "申请退保成功~等待管理员审核~");
        } else {
            return new JsonData().build(-2000,"退保失败！！！");
        }

    }


    @GetMapping("/logout")
    public void logOut(HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("LOGIN_USER");
        response.sendRedirect("/");
    }

    @GetMapping("/")
    public String index(Model md) {
        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        User one = userMapper.getOne(login_user);
        md.addAttribute("user", one);
        return "user/index";
    }


    @ResponseBody
    @PostMapping("/delMsg")
    public JsonData delete(@RequestParam Integer mid) {
        if (mid == null) {
            return new JsonData().build(-2000, "mid为空!");
        }
        msgMapper.delete(mid);
        return new JsonData().build(2000, "删除成功!");
    }

    @ResponseBody
    @PostMapping("/toMsg")
    public JsonData toMessgae(@RequestParam String message) {


        if (message == null) {
            return new JsonData().build(-2000, "留言发生错误！");
        }

        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        msgMapper.insert(login_user, message, new Date().toLocaleString());

        return new JsonData().build(2000, "留言成功~请等待管理员回复~");
    }


    @ResponseBody
    @PostMapping("/topup")
    public JsonData toTopUP(@RequestParam String passsword, @RequestParam String money) {


        if (money.indexOf("-") != -1) {
            return new JsonData().build(-2000, "充值发生错误!稍后重试～");
        }

        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        User one = userMapper.getOne(login_user);
        int new_money = one.getMoney() + Integer.valueOf(money);
        if (passsword.equals("") || passsword == null || money == null) {
            return new JsonData().build(-2000, "充值发生错误!");
        }
        if (passsword.equals("admin")) {
            billMapper.up(login_user, Integer.valueOf(money), new Date().toLocaleString());
            userMapper.topUp(login_user, new_money);
            return new JsonData().build(2000, String.format("充值成功～账号余额为: %d", new_money));
        } else {
            return new JsonData().build(-2000, "充值密码错误!");
        }


    }

    @ResponseBody
    @PostMapping("/reset")
    public JsonData toReset(@RequestParam String password1, @RequestParam String password2) {
        String login_user = (String) request.getSession().getAttribute("LOGIN_USER");
        if (password1 == null || password2 == null) {
            return new JsonData().build(-2000, "参数空！");
        }
        if (userMapper.login(login_user, password1) == null) {
            return new JsonData().build(-2000, "原始密码错误！");
        } else {
            userMapper.resetPwd(login_user, password2);
            return new JsonData().build(2000, "密码已经更新！");
        }
    }
}
