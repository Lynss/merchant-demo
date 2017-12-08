package com.ly.merchantdemo.service.impl;

import com.enci.signature.Base64;
import com.enci.signature.Parameters;
import com.enci.signature.Secrets;
import com.enci.signature.api.RSAUtils;
import com.enci.signature.client.ClientSignatureFilter;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.merchantdemo.domain.*;
import com.ly.merchantdemo.service.MerchantService;
import com.sun.jersey.api.client.WebResource;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler;
import org.apache.http.impl.client.BasicCookieStore;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.LoggingFilter;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;

@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {
    private static final String APP_ID = "dpf43f3p2l4k3l03";
    private static final String SIGNATURE_METHOD = "RSA-SHA1";
    private static final String VERSION = "1.0";
    private static final String PRIVATE_KEY =
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJNgACdcxwVVL6DACVanU71J6DbJFse" +
                    "+0kUTwvxvCrwUfQr368Sx6rCBzH/6NAqiItFGDS6KtbjYDoMCRRMgiOrTshIjUpdmZQN6obeVaLwuJxCc7erzZV9ZZunEel4JT/y0sjI2oGCBLl3L3pBu5c3VtonZVJ70xyhmciaTodkrAgMBAAECgYBnekChYsNbOzT16eCkt1hU0E/8J7WXCqUZW4bNOCqRZNFnoiwpL8NZq8mrEkL4NF++ETGwPDTcVNfbpPxbMbzwFGV2mfIsVKtIoPfS67i5FHXnhNEb2oURMVR4k4Y70jSd5HlpzIImN/HAlriEmMKOyx5hwFKwBWUbs43zoVK0AQJBAMWkgnrehmu8N2zOpBeezJfPlOGH8A28zEPWykGAZCqTNNF6pPZkSoU1WAnbW1oPxEjyutpJXEjBFwfMPpHQU5kCQQC+49eVWxufYRFqZR/BX6DTrVBSwro1liw9OyZo/c6m7JR6DXVc1IyU8N/4K6UkGz88wiJ/SUzdCoMrJSd6IM1jAkAYqaimkHIRq5D3AOo1EFnTb9HSOtZXwIF0za67cbwOHARxR26iWG18JeXwhPDnUiRaPf/XEWR0p7OqA3CjXW2xAkA6eng31CJhMA5yxqn0xoPxdP3PbMI42lmRJIa+0Uo2jvFpdqgGmUK7+hLS5yP/LK2xwlNpJR579NV8KTSv0E67AkEAtdODq4h23DZT4y0XOwKimd/Rk2e0/7tkd/LxBiSxV+wszPk+qqF44kmrlwbqy+rHlgttzT6YZQ7typ9EMjgthg==";

    @Override
    public EasyPayResponse postForPlatformData(String testRequestData, HttpServletResponse
            response) throws Exception {
        String getUrl = "http://t-longyun.e-nci.com/api/pay/gateway";
        String queryBody = Base64.encode(RSAUtils.encryptByPrivateKey(testRequestData.getBytes(), PRIVATE_KEY));
        HttpConfigs postUrlConfig = new HttpConfigs("post", SIGNATURE_METHOD, PRIVATE_KEY, getUrl, APP_ID, VERSION, MediaType.APPLICATION_JSON_TYPE, null, queryBody);
        //获取数据后对数据进行解析，并且组装成需要的值
        EasyPayResponse merchantResult = new ObjectMapper().readValue(queryHttpByConfigs(postUrlConfig), new TypeReference<EasyPayResponse>() {});
        if (Integer.parseInt(merchantResult.getCode()) != MerchantResultEnum.EASY_PAY_SUCCESS.code()) {
            throw new RuntimeException(merchantResult.getMessage());
        }
        merchantResult.setMessage(MerchantResultEnum.MERCHANT_SUCCESS.message());
        merchantResult.setCode(String.valueOf(MerchantResultEnum.MERCHANT_SUCCESS.code()));
        return merchantResult;
    }

    public String queryHttpByConfigs(HttpConfigs httpConfig) {
        CloseableHttpClient apacheClient = HttpClientBuilder.create().build();
        Client client = new Client(new ApacheHttpClient4Handler(apacheClient, new BasicCookieStore(), true));
        client.addFilter(new LoggingFilter());
        Providers providers = client.getProviders();
        Parameters params = new Parameters().appId(httpConfig.appId()).version(httpConfig.version()).signatureMethod
                (httpConfig.encryptType());
        Secrets secrets = new Secrets().appSecret(httpConfig.privateKey());
        ClientSignatureFilter filter = new ClientSignatureFilter(providers, params, secrets);
        WebResource resource = client.resource(httpConfig.baseUrl());
        resource.addFilter(filter);
        if (httpConfig.getQueryParams() != null) {
            resource.queryParams(httpConfig.getQueryParams()).type(MediaType.APPLICATION_JSON_TYPE);
        } else {
            resource.type(MediaType.APPLICATION_JSON_TYPE);
        }
        return "post".equals(httpConfig.httpMethod()) ? resource.post(String.class, httpConfig.postQueryBody()) :
                resource.get(String.class);
    }
}
