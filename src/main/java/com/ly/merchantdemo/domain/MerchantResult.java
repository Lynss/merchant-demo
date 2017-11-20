package com.ly.merchantdemo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class MerchantResult<T> extends BaseReturn {
    private T data;

    public MerchantResult(MerchantResultEnum merchantResultEnum,T data){
        super(merchantResultEnum.getCode(),merchantResultEnum.getMessage());
        this.data = data;
    }
}
