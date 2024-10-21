package org.example.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootdeveloper.dto.request.UserLoginRequestDto;
import org.example.springbootdeveloper.dto.request.UserRequestDto;
import org.example.springbootdeveloper.dto.response.ResponseDto;
import org.example.springbootdeveloper.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseDto<String> signup(@RequestBody UserRequestDto userRequestDto) {
        try {
            String result = userService.signup(userRequestDto);
            return ResponseDto.setSuccess("Signup successful", result);
        } catch(Exception e) {
            return ResponseDto.setFailed("Signup Failed" + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        try {
            String result = userService.login(userLoginRequestDto);
            return ResponseDto.setSuccess("Login successful", result);
        } catch(Exception e) {
            return ResponseDto.setFailed("Login Failed" + e.getMessage());
        }
    }
}
