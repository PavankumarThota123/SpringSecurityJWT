package com.example.demo.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.JwtService;
import com.example.demo.service.UserInfoService;



@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/addUser")
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/token")
    public String authenticateUser(@RequestBody UserInfo userInfo) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userInfo.getUname(), userInfo.getPwd()));
            if (authentication.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                return jwtService.generateToken(userDetails.getUsername());
            } else {
                throw new RuntimeException("Authentication failed");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }

    @GetMapping("/profile")
    public String userProfile() {
        return "Welconme to jwt";
    }
    @GetMapping("/hlo")
    public ResponseEntity<Object> hlo(Authentication auth) {
        var response = new HashMap<String, Object>();
        response.put("uname", auth.getName());
        response.put("Authorities", auth.getAuthorities());

        // Correct the following line, assuming 'user' is meant to be a string key
        var appUser = userInfoService.findByUsername(auth.getName());
        response.put("user", appUser);

        return ResponseEntity.ok(response);
    }


}
