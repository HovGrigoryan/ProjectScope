package com.hov.project_scope.controller;


import com.hov.project_scope.model.User;
import com.hov.project_scope.repository.UserRepository;
import com.hov.project_scope.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    @Value("${file.upload.dir}")
    private String uploadDir;

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user, ModelMap modelMap, @RequestParam("image") MultipartFile file) throws IOException {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (byEmail.isPresent()) {
            String msg = "User already exist";
            modelMap.addAttribute("msg", msg);
            return "register";
        }
        String profilePic = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File image = new File(uploadDir, profilePic);
        file.transferTo(image);
        String msg = "User was added";
        User user1 = User.builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .profilePic(profilePic)
                .userType(user.getUserType())
                .build();
        userRepository.save(user1);
        modelMap.addAttribute("msg",msg);

        return "index";
    }
}
