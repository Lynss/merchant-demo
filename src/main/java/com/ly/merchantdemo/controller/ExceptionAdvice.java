package com.ly.merchantdemo.controller;

import com.ly.merchantdemo.domain.MerchantResult;
import com.ly.merchantdemo.domain.MerchantResultEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ly
 */
@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public MerchantResult exceptionHandler(Exception e) {
        e.printStackTrace();
        return new MerchantResult<>(MerchantResultEnum.EASY_PAY_FAIL_UNHANDLE_EXCEPTION, null);
    }
}
