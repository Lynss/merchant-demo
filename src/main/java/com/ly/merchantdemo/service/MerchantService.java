package com.ly.merchantdemo.service;

import com.ly.merchantdemo.domain.EasyPayResponse;
import com.ly.merchantdemo.domain.MerchantResult;
import com.ly.merchantdemo.domain.RequestPayMessageDTO;

import javax.servlet.http.HttpServletResponse;

public interface MerchantService {
    EasyPayResponse postForPlatformData(String testRequestData, HttpServletResponse response) throws Exception;
}
