package com.ly.merchantdemo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class HttpConfigs {
    private String httpMethod;
    private String encryptType;
    private String privateKey;
    private String baseUrl;
    private String appId;
    private String version ;
    private String mediaType;
    private String getQueryParams;
    private String postQueryBody;
}
