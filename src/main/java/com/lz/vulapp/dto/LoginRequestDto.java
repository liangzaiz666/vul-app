package com.lz.vulapp.dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
    private String validCode;

}
