package com.ryan.sms.medical.view;

import com.ryan.sms.medical.mapper.BillMapper;
import com.ryan.sms.medical.mapper.MsgMapper;
import com.ryan.sms.medical.mapper.UserMapper;
import com.ryan.sms.medical.pojo.Bill;
import com.ryan.sms.medical.pojo.Msg;
import com.ryan.sms.medical.pojo.User;
import com.ryan.sms.medical.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/")
    public String index(Model md){
        md.addAttribute("user","admin");
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


    @GetMapping("/msgList.html")
    public String tomsgList(Model md){
        List<Msg> allMsg = msgMapper.getAllMsg();
        md.addAttribute("msg",allMsg);
        return "admin/msgList";
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
