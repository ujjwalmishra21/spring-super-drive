package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getSignUp(){
        return "signup";
    }

    @PostMapping
    public String postSignUp(@ModelAttribute User user, Model model){
        String signUpError = null;

        if(!userService.isUsernameAvailable(user.getUsername())){
            signUpError = "The username already exists";
        }

        if(signUpError == null){
            Integer rowsAdded = userService.createUser(user);
            if(rowsAdded < 0){
                signUpError = "There was an error signing you in. Please try again.";
            }
        }

        if(signUpError == null){
            model.addAttribute("signupSuccess", true);
        }else{
            model.addAttribute("signupError", signUpError);
        }
        return "signup";
    }
}
