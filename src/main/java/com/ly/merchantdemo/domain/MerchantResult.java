package com.ly.merchantdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantResult<T>implements Serializable{
    private static final long serialVersionUID = 9134347043515718028L;
    private T data;
    private int code;
    private String message;

    public MerchantResult(MerchantResultEnum merchantResultEnum,T data){
        this.code = merchantResultEnum.code();
        this.message = merchantResultEnum.message();
        this.data = data;
    }

}
