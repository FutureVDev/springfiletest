package com.myproj.test.controllers;


import com.myproj.test.entities.SystemUser;
import com.myproj.test.entities.User;
import com.myproj.test.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("systemUser", new SystemUser());
        return "registration-form";
    }


    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser theSystemUser, BindingResult theBindingResult, Model theModel) {
        String userName = theSystemUser.getUserName();
        logger.debug("Processing registration form for: " + userName);
        if (theBindingResult.hasErrors()) {
            return "registration-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing != null) {

            theModel.addAttribute("systemUser", theSystemUser);
            theModel.addAttribute("registrationError", "User with current username already exists");
            logger.debug("User name already exists.");
            return "registration-form";
        }
        userService.save(theSystemUser);
        logger.debug("Successfully created user: " + userName);
        return "registration-confirmation";
    }
}
