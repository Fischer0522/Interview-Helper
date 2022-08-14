package com.fischer.exception;

import lombok.Data;
import org.springframework.validation.ObjectError;

@Data
public class BizException extends RuntimeException {

    private Integer code;
    private String msg;
    private Object data;

    public BizException(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
        data = null;
    }

}