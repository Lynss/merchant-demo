package com.ly.merchantdemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.merchantdemo.domain.MerchantResult;
import com.ly.merchantdemo.domain.MerchantResultEnum;
import com.ly.merchantdemo.domain.RequestPayMessageDTO;
import com.ly.merchantdemo.domain.TradeRequest;
import com.ly.merchantdemo.service.MerchantService;
import com.ly.merchantdemo.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public MerchantResult<TradeRequest> orderMessage(HttpServletResponse response) {
        String testDemoString = "{\n" +
                "            \"merId\": \"MER001\",\n" +
                "                \"outTradeNo\": \"258228420608\",\n" +
                "                \"remark\": \"该订单为测试订单\",\n" +
                "                \"orderType\": 1,\n" +
                "                \"userId\":\"321\",\n" +
                "                \"discountAmount\": 0,\n" +
                "                \"originalAmount\": 1,\n" +
                "                \"tradeAmount\": 1,\n" +
                "                \"notifyUrl\": \"http://localhost:8080/callback\",\n" +
                "                \"orderName\": \"车险订单\",\n" +
                "                \"payeeCusOpenid\":\"73b24f53ffc64486eb40d606456fb04d\",\n" +
                "                \"payerCusOpenid\":\"231\",\n" +
                "                \"limitPay\": \"no_credit\"\n" +
                "        }";
        try {
            TradeRequest tradeRequest = new ObjectMapper().readValue(testDemoString, TradeRequest.class);
            tradeRequest.setOutTradeNo(IDUtil.getId());
            return new MerchantResult<>(MerchantResultEnum.MERCHANT_SUCCESS, tradeRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassCastException("jackson 转换失败");
        }
    }

    @PostMapping("/url")
    public MerchantResult<RequestPayMessageDTO> platformUrl(@RequestBody TradeRequest testRequest, HttpServletResponse response) {
        //测试请求参数
        try {
            String testRequestData =new ObjectMapper().writeValueAsString(testRequest);
            return merchantService.postForPlatformData(testRequestData, response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClassCastException("转换实体类对象为字符串失败");
        }
    }

    @PostMapping("/callback")
    public void callback(HttpServletRequest request, HttpServletResponse response) {
        String requestData = request.getParameter("code");
        try (PrintWriter writer = response.getWriter()) {
            writer.print("notify_success");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("输出异常");
        }
        if (MerchantResultEnum.EASY_PAY_SUCCESS.getCode() != Integer.valueOf(requestData)) {
            System.out.println(request.getParameter("message"));
        } else {
            System.out.println("paySuccess");
        }
        System.out.println("message get success");
    }

}
