package com.lz.vulapp.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lz.vulapp.dto.*;

public interface UserService {
    void add(UserDto userDto);

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    UserDto getUserByUsername(String username);

    IPage<UserDto> searchUser(Integer pageNum, Integer pageSize, String username);

    void updatePassword(UserPasswordEditDto userPasswordEditDto);

    void deleteUser(Long id);
}
