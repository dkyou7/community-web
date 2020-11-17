package com.example.communityweb.controller;

import com.example.communityweb.annotation.SocialUser;
import com.example.communityweb.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping({"", "/"})
    public String getAuthorizationMessage() {
        return "home";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginComplete(@SocialUser User user){
        return "redirect:/board/list";
    }

    @GetMapping("/loginFailure")
    public String loginFailure(){
        return "redirect:/";
    }
}
