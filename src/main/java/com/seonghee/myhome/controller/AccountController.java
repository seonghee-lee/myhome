package com.seonghee.myhome.controller;

import com.seonghee.myhome.model.User;
import com.seonghee.myhome.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(){
        return "/account/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/account/register";
    }

    @PostMapping("/register")
    public String register(User user){
        //비밀번호 암호화, 사용자 권한 추가, 회원가입 정보 저장
        userService.save(user);
        return "redirect:/";    //회원가입이 다 끝난 후에는 홈으로 이동한다.
    }
}
