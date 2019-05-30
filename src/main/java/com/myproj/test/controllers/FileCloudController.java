package com.myproj.test.controllers;

import com.myproj.test.entities.File;
import com.myproj.test.services.FileService;
import com.myproj.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

//import static com.sun.tools.doclint.Entity.copy;

@Controller("/")
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
    public String listUploadedFiles(Model model, Principal principal) throws IOException {
        if (principal == null) {
            return "redirect:/login";
        }
        model.addAttribute("files", fileService.getAllByUserName(principal.getName()));

        return "index";
    }

    @GetMapping("/files/{signature}")
    @ResponseBody
    public void serveFile(@PathVariable String signature, HttpServletResponse response) {
        try{
            File file = fileService.getOneBySignature(signature);
            InputStream is = new ByteArrayInputStream(file.getContent());
            FileCopyUtils.copy(is, response.getOutputStream());
            response.setContentType(file.getContentType());
            response.flushBuffer();

        }catch (Exception e){
            System.out.println(e);
        }



    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, Principal principal) {

        File myFile = new File();
        myFile.setName(file.getOriginalFilename());
        try {

            myFile.setContent(file.getBytes());
            myFile.setContentType(file.getContentType());
            myFile.setUser(userService.findByUserName(principal.getName()));
            myFile.setSignature(randomAlphaNumeric(16));
            fileService.saveOrUpdate(myFile);
        }catch (Exception e){
            System.out.println(e);
        }
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomAlphaNumeric(int count) {

        StringBuilder builder = new StringBuilder();

        while (count-- != 0) {

            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());

            builder.append(ALPHA_NUMERIC_STRING.charAt(character));

        }

        return builder.toString();

    }

}
