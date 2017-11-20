package com.ly.merchantdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(CorsConfig.class)
class MerchantDemoConfig {

}