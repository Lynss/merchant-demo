package com.ly.merchantdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TradeRequest {
    private String merId;
    private String userId;
    private String outTradeNo;
    private String payeeCusOpenid;
    private String payerCusOpenid;
    private String payeeCusName;
    private String payerCusName;
    private String orderName;
    private char orderType;
    private int originalAmount;
    private String discountAmount;
    private int tradeAmount;
    private String remark;
    private String notifyUrl;
    private String limitPay;
    private String pmtTag;
    private String sn;
}
