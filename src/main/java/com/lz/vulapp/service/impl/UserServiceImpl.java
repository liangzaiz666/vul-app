package com.lz.vulapp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lz.vulapp.common.UserException;
import com.lz.vulapp.entity.User;
import com.lz.vulapp.dto.*;
import com.lz.vulapp.mapper.UserMapper;
import com.lz.vulapp.service.UserService;
import com.lz.vulapp.util.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtUtil jwtUtil;

    @Override
    public void add(UserDto userDto) {
        User originUser = userMapper.findOneByUsername(userDto.getUsername());
        if (originUser != null) {
            throw new UserException(400, "用户名已存在");
        }
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        userMapper.insert(user);
    }
    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(),loginRequestDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        LoginResponseDto loginResponseDto = new LoginResponseDto();
        loginResponseDto.setToken(jwtUtil.generateToken(loginRequestDto.getUsername()));
        return loginResponseDto;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userMapper.findOneByUsername(username);
        if (user == null){
            return null;
        }
        return toUserInfoDto(user);
    }

    @Override
    public IPage<UserDto> searchUser(Integer pageNum, Integer pageSize, String username) {
        if (pageNum == null || pageNum < 1) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 10;
        }
        LambdaQueryWrapper<User> wrappers = Wrappers.lambdaQuery();
        wrappers.orderByDesc(User::getUpdatedAt);
        if (StringUtils.hasText(username)) {
            wrappers.like(User::getUsername, username);
        }
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), wrappers);
        if (userPage == null) {
            return null;
        }
        return userPage.convert(this::toUserInfoDto);
    }

    @Override
    public void updatePassword(UserPasswordEditDto userPasswordEditDto) {
        User user = userMapper.selectById(userPasswordEditDto.getId());
        user.setPassword(passwordEncoder.encode(userPasswordEditDto.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        userMapper.deleteById(id);

    }

    private UserDto toUserInfoDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        userDto.setCreatedAt(user.getCreatedAt());
        return userDto;
    }
}
