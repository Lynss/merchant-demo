package com.ly.merchantdemo.domain;

import com.sun.jersey.core.util.MultivaluedMapImpl;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.ws.rs.core.MediaType;

@Data
@AllArgsConstructor
@Accessors(fluent = true)
public class HttpConfigs {
    private String httpMethod;
    private String encryptType;
    private String privateKey;
    private String baseUrl;
    private String appId;
    private String version ;
    private MediaType mediaType;
    private MultivaluedMapImpl getQueryParams;
    private String postQueryBody;
}
