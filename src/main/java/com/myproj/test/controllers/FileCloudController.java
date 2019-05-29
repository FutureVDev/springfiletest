package com.myproj.test.controllers;

import com.myproj.test.services.FileService;
import com.myproj.test.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
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
        if (principal == null) {
            return "redirect:/login";
        }
        System.out.println(principal);
        model.addAttribute("files", principal.getName());
        model.addAttribute("files", fileService.getAllByUserName(principal.getName()));
        return "index";
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model, Principal principal) throws IOException {

        model.addAttribute("files", fileService.getAllByUserName(principal.getName()));

        return "index";
    }

//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = fileService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        File file = new File();

        file.set

        fileService.saveOrUpdate(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

}
