package com.avs.adpass.controllers;

import com.avs.adpass.services.UserService;
import com.avs.adpass.services.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class IndexController {


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping({"/index"})
    public String index(Model model, Authentication authentication) {

        return "index";
    }

    @RequestMapping("/access_denied")
    public String notAuth() {
        return "access_denied";
    }


    @RequestMapping("login")
    public String loginForm() {

        return "login";
    }
}
