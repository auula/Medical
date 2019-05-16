package com.ryan.sms.medical.view;

import com.ryan.sms.medical.mapper.*;
import com.ryan.sms.medical.pojo.Bill;
import com.ryan.sms.medical.pojo.Msg;
import com.ryan.sms.medical.pojo.TRequest;
import com.ryan.sms.medical.pojo.User;
import com.ryan.sms.medical.utils.JsonData;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminView {


    @Autowired
    UserMapper userMapper;

    @Autowired
    BillMapper billMapper;


    @Autowired
    MsgMapper msgMapper;


    @Autowired
    TRequestMapper tRequestMapper;

    @Autowired
    NewsMapper newsMapper;

    @Autowired
    HttpServletRequest request;

    //获取文件存储路径
    @Value("${filePath}")
    private String filePath;


    @GetMapping("/my.html")
    public String toMy(Model md){
        User user = new User();
        user.setUsername("系统超级管理员");
        md.addAttribute("user",user);
        return "admin/my";
    }

    @GetMapping("/logout")
    public void logOut(HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("USER_TYPE");
        request.getSession().removeAttribute("LOGIN_USER");
        response.sendRedirect("/");
    }
    @GetMapping("/")
    public String index(Model md){
        User user = new User();
        user.setUsername("系统管理员");
        md.addAttribute("user",user);
        return "admin/index";
    }

    @GetMapping("/userAudit.html")
    public String toAudit(Model md){
        List<User> allAuditUser = userMapper.getAllAuditUser();
        md.addAttribute("users",allAuditUser);
        return "admin/userAudit";
    }

    @GetMapping("/billList.html")
    public String toBill(Model md){
        List<Bill> all = billMapper.getAll();
        md.addAttribute("bill",all);
        return "admin/billList";
    }

    @GetMapping("/userList.html")
    public String toList(Model md){
        List<User> allUser = userMapper.getAll();
        md.addAttribute("users",allUser);
        return "admin/userList";
    }
    @GetMapping("/pullNews.html")
    public String pullNews(){
        return "admin/pullNews";
    }

    @GetMapping("/msgList.html")
    public String tomsgList(Model md){
        List<Msg> allMsg = msgMapper.getAllMsg();
        md.addAttribute("msg",allMsg);
        return "admin/msgList";
    }
    @GetMapping("/reqList.html")
    public String toreqList(Model md){
        List<TRequest> all = tRequestMapper.getAll();
        md.addAttribute("reqs",all);
        return "admin/reqList";
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
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    @ResponseBody
    @PostMapping("/delMsg")
    public JsonData delMsg(@RequestParam Integer mid){
        if (mid == null) {
            return new JsonData().build(-2000,"操作失败!！！！");
        }
        msgMapper.delete(mid);
        return new JsonData().build(2000,"操作成功～留言已被删除!");
    }
    @ResponseBody
    @PostMapping("/pull")
    public JsonData pull(@RequestParam String title,@RequestParam String content){
        if (title == null || content == null) {
            return new JsonData().build(-2000,"操作失败!！！！");
        }
        newsMapper.insert(title,content,new Date().toLocaleString());
        return new JsonData().build(2000,"发布成功～请去新闻列表查看～");
    }
    @ResponseBody
    @PostMapping("/reply")
    public JsonData reply(@RequestParam Integer mid,@RequestParam String content){
        if (mid == null || content == null) {
            return new JsonData().build(-2000,"操作失败!！！！");
        }
        msgMapper.reply(content,mid);
        return new JsonData().build(2000,"操作成功～留言已被回复!");
    }
    @ResponseBody
    @PostMapping("/audit")
    public JsonData Audit(@RequestParam Integer uid){
        if (uid == null) {
            return new JsonData().build(-2000,"操作失败!！！！");
        }
        userMapper.audit(uid);
        return new JsonData().build(2000,"操作成功～用户已被审核!");
    }

    @ResponseBody
    @PostMapping("/action")
    public JsonData action(@RequestParam Integer rid){
        if (rid == null) {
            return new JsonData().build(-2000,"操作失败!！！！");
        }
        tRequestMapper.action(rid);
        return new JsonData().build(2000,"操作成功～请求已被审核!");
    }
    @ResponseBody
    @PostMapping("/del")
    public JsonData del(@RequestParam Integer uid){
        if (uid == null) {
            return new JsonData().build(-2000,"操作失败!！！！");
        }
        userMapper.del(uid);
        return new JsonData().build(2000,"操作成功～用户已被删除!");
    }

    @ResponseBody
    @PostMapping("/delBill")
    public JsonData delBill(@RequestParam Integer bid){
        if (bid == null) {
            return new JsonData().build(-2000,"操作失败!！！！");
        }
        billMapper.del(bid);
        return new JsonData().build(2000,"操作成功～记录已被删除!");
    }



}
