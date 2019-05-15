package com.ryan.sms.medical.view;

import com.ryan.sms.medical.mapper.UserMapper;
import com.ryan.sms.medical.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminView {


    @Autowired
    UserMapper userMapper;


    @GetMapping("/")
    public String index(){
        return "admin/index";
    }

    @GetMapping("/userAudit.html")
    public String toAudit(Model md){
        List<User> allAuditUser = userMapper.getAllAuditUser();
        md.addAttribute("users",allAuditUser);
        return "admin/userAudit";
    }

}
