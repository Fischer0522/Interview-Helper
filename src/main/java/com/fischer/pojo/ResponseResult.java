package com.fischer.pojo;

import lombok.Data;

@Data
public class ResponseResult {


    private Integer code;
    private Object data;
    private String msg;

    public ResponseResult(Integer code,String msg,Object data) {
        this.code = code;
        this.msg =msg;
        this.data = data;
    }

    public ResponseResult(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
        this.data =null;
    }

}
