package com.hov.project_scope.controller;


import com.hov.project_scope.model.User;
import com.hov.project_scope.model.enums.UserType;
import com.hov.project_scope.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class MainController {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/loginPage")
    public String loginPage(){
        return "loginPage";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }




//    @PostMapping("/perform_login")
//    public String afterLogin() {
//        return "redirect:/successLogin";
//    }

    @GetMapping("/successLogin")
    public String successLogin(@AuthenticationPrincipal CurrentUser currentUser, ModelMap modelMap) {
        if (currentUser == null) {
            modelMap.addAttribute("msg","Wrong email or password");
            return "index";
        }
        User user = currentUser.getUser();
        if (user.getUserType() == UserType.TEAMLEADER) {
            return "leaderPage";
        } else {
            return "memberPage";
        }
    }

    @GetMapping(
            value = "/image",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) throws IOException {
        InputStream in = new FileInputStream(uploadDir + File.separator + imageName);
        return IOUtils.toByteArray(in);
    }
}
