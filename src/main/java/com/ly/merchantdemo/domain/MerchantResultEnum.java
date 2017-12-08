package com.ly.merchantdemo.domain;

import java.io.Serializable;

public enum MerchantResultEnum implements Serializable{
    EASY_PAY_SUCCESS(1,"成功"),
    EASY_PAY_FAIL_GETURL(301,"获取支付平台地址失败"),
    EASY_PAY_FAIL_UNHANDLE_EXCEPTION(302,"未处理的意料外异常"),
    MERCHANT_SUCCESS(200,"成功"),
    FAIL_ENCRYPT_TYPE_NOT_FOUNT(401,"没有合适的加密方式"),
    FAIL_UNSUPPORT_HTTP_METHOD(402,"无法进行对应的http请求");

    MerchantResultEnum(int code,String message) {
        this.code = code;
        this.message = message;
    }
     private int code;
     private String message;

    public int code() {
        return this.code;
    }
    public String message() {
        return this.message;
    }
}
