package com.ly.merchantdemo.domain;


import java.io.Serializable;

/**
 * Created by lanxiaolong on 2017/8/29.
 */
public class EasyPayResponse  implements Serializable {
    private static final long serialVersionUID = -5824717466424543879L;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPmtType() {
        return pmtType;
    }

    public void setPmtType(String pmtType) {
        this.pmtType = pmtType;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getMweb_url() {
        return mweb_url;
    }

    public void setMweb_url(String mweb_url) {
        this.mweb_url = mweb_url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    private String code;

    private String message;

    private String transactionId;
    private String pmtType;
    //private InputStream inputStream;
    private String qrCode;
    /** ==============微信公众号号微信APP支付相关参数================ */
    //mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
    private String mweb_url;
    //公众号id（APP）
    private String appid;
    //子商户号（APP）
    private String partnerid;
    //预支付交易会话标识（APP）
    private String prepayid;
    // 随机字符串（APP）
    private String noncestr;
    // 时间戳（APP）
    private String timestamp;
    // 签名（APP）
    private String sign;
}
