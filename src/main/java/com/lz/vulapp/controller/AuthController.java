package com.lz.vulapp.controller;

import com.lz.vulapp.common.PlainResult;
import com.lz.vulapp.dto.LoginRequestDto;
import com.lz.vulapp.dto.LoginResponseDto;
import com.lz.vulapp.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public PlainResult<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto loginResponseDto = userService.login(loginRequestDto);
        return PlainResult.success(loginResponseDto);
    }

}
