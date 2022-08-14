package com.fischer.exception;

import com.fischer.pojo.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalHandler {
    @ExceptionHandler(BizException.class)
    public ResponseEntity<ResponseResult> bizExceptionHandler(BizException e){
        log.error("发生业务异常,,具体原因为："+e.getMsg());
        return ResponseEntity.ok(new ResponseResult(e.getCode(), e.getMsg()));

    }
}
