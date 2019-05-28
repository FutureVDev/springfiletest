package com.myproj.test.controllers;

import com.myproj.test.services.FileService;
import com.myproj.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller("/filecloud")
public class FileCloudController {
    private FileService fileService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFileService(FileService fileService){this.fileService = fileService;}



    @GetMapping("/")
    public String showMainPage(Model model, Principal principal){
//        model.addAttribute("files", fileService.getAllByUserName(principal.getName()));
        return "index";
    }

}
