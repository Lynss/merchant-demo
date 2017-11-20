package com.ly.merchantdemo.service;

import com.ly.merchantdemo.domain.MerchantResult;
import com.ly.merchantdemo.domain.RequestPayMessageDTO;

import javax.servlet.http.HttpServletResponse;

public interface MerchantService {
    public MerchantResult<RequestPayMessageDTO> postForPlatformData(String testRequestData, HttpServletResponse response);
}
