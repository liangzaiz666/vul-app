package com.lz.vulapp.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserException extends RuntimeException {
    private Integer code;

    public UserException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
