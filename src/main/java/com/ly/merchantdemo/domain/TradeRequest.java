package com.ly.merchantdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
//@Accessors(fluent = true)
public class TradeRequest implements Serializable{
    private static final long serialVersionUID = 3563532561509730709L;
    private String openId;
    private String userId;
    private String outTradeNo;
    private String payeeCusOpenid;
    private String payerCusOpenid;
    private String payeeCusName;
    private String payerCusName;
    private String orderName;
    private char orderType;
    private Integer originalAmount;
    private Integer discountAmount;
    private Integer tradeAmount;
    private String remark;
    private String notifyUrl;
    private String limitPay;
    private String pmtTag;
    private String pmtType;
    private String sn;
    private String appId;
    private String createIp;
}
