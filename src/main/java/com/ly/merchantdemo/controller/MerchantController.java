package com.ly.merchantdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.merchantdemo.domain.*;
import com.ly.merchantdemo.service.MerchantService;
import com.ly.merchantdemo.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@RestController
public class MerchantController {
    private final MerchantService merchantService;

    @Autowired
    public MerchantController(MerchantService merchantService) {
        this.merchantService = merchantService;
    }

    @GetMapping("/orderMessage")
    public MerchantResult<TradeRequest> orderMessage(HttpServletResponse response) throws Exception {
        String testDemoString = "{\n" +
                "                \"remark\": \"该订单为测试订单\",\n" +
                "                \"orderType\": 1,\n" +
                "                \"discountAmount\": 0,\n" +
                "                \"originalAmount\": 1,\n" +
                "                \"tradeAmount\": 1,\n" +
                "                \"notifyUrl\": \"http://localhost:8080/notify\",\n" +
                "                \"orderName\": \"车险订单\",\n" +
                "                \"appId\": \"MER001\",\n" +
                "                \"sn\": \"357926679458349057\",\n" +
                "                \"payeeCusOpenid\":\"8e72156fa71b6b3fc634ed60d66f3538\",\n" +
                "                \"payerCusOpenid\":\"oF8GtwTvVRkOhOIBo0sgWk734UB4\",\n" +
                "                \"limitPay\": \"no_credit\"\n" +
                "        }";
        TradeRequest tradeRequest = new ObjectMapper().readValue(testDemoString, TradeRequest.class);
        tradeRequest.setOutTradeNo(IDUtil.getId());
        //微信分享支付
//        tradeRequest.setPmtTag("Weixin");
//        tradeRequest.setPmtType("4");
        //微信app支付
        tradeRequest.setPmtTag("Weixin");
        tradeRequest.setPmtType("5");
        tradeRequest.setOpenId("8e72156fa71b6b3fc634ed60d66f3538");
        return new MerchantResult<>(MerchantResultEnum.MERCHANT_SUCCESS, tradeRequest);
    }

    @PostMapping("/url")
    public EasyPayResponse platformUrl(@RequestBody TradeRequest testRequest,
                                       HttpServletResponse response) throws Exception {
        //测试请求参数
        String testRequestData = new ObjectMapper().writeValueAsString(testRequest);
        return merchantService.postForPlatformData(testRequestData, response);
    }

    @PostMapping("/notify")
    public void callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String requestData = request.getParameter("code");
        try (PrintWriter writer = response.getWriter()) {
            writer.print("notify_success");
        }
        if (MerchantResultEnum.EASY_PAY_SUCCESS.code() != Integer.valueOf(requestData)) {
            System.out.println(request.getParameter("message"));
        } else {
            System.out.println("paySuccess");
        }
        System.out.println("message get success");
    }

}
