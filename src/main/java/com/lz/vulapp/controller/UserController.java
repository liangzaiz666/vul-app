package com.lz.vulapp.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lz.vulapp.common.PlainResult;
import com.lz.vulapp.dto.UserDto;
import com.lz.vulapp.dto.UserPasswordEditDto;
import com.lz.vulapp.service.UserService;
import com.lz.vulapp.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private UserService userService;

    @GetMapping
    public PlainResult<UserDto> getUserInfo(@RequestParam String token) {
        String username = jwtUtil.extractUsername(token);
        UserDto userDto = userService.getUserByUsername(username);
        return PlainResult.success(userDto);
    }

    @GetMapping("/search")
    public PlainResult<IPage<UserDto>> searchUser(@RequestParam(required = false) Integer pageNum,
                                                  @RequestParam(required = false) Integer pageSize,
                                                  @RequestParam(required = false) String username) {
        IPage<UserDto> userInfoDtoIPage = userService.searchUser(pageNum, pageSize, username);
        return PlainResult.success(userInfoDtoIPage);

    }

    @PostMapping("/add")
    public PlainResult<String> register(@RequestBody UserDto userDto) {
        userService.add(userDto);
        return PlainResult.success("注册成功");
    }

    @PostMapping("/update/{id}")
    public PlainResult<String> updatePassword(@RequestBody  UserPasswordEditDto userPasswordEditDto) {
        userService.updatePassword(userPasswordEditDto);
        return PlainResult.success("success");
    }

    @DeleteMapping("/{id}")
    public PlainResult<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return PlainResult.success("success");
    }
}
