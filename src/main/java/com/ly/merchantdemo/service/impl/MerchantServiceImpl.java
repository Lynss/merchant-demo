package com.ly.merchantdemo.service.impl;

import com.ly.merchantdemo.domain.MerchantResult;
import com.ly.merchantdemo.domain.RequestPayMessageDTO;
import com.ly.merchantdemo.service.MerchantService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {
    private static final String APP_ID = "dpf43f3p2l4k3l03";
    private static final String SIGNATURE_METHOD = "RSA-SHA1";
    private static final String VERSION = "1.0";
    private static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ3ZfSRieTqOKtxNxVydZgRf9yXqlovZwu2diD8d8CT57n4OiraNxAaJ2ENaFbRHfid9oiBhiwZyEja1Sat1FZeaH1I8QlZL5sU9vbNuQC03bjrXlb8UI4enVhiiXB1ieY6rFLXXMpDP90htLKyIvH+RkNgzJvQXVhzzoFm12gOBAgMBAAECgYBD14DgjW47C3VCYC6OApwhDznC0xNHIh2UUJuJPQ3EZqLpDMjzcvSoNsB7GhGv/PYsdOOkdSfyaj6HwtzZ0yWm5QZIQv66nQpxL1Ss3fQeqt5px/kFjPOnYOKYg4A9Cn9ffSMKo7kMwh1KXOVrZ+NLHdPp/8QAhcGyE5eHZmKFzQJBAPh+WHSOYiLfxd0WrQpNvMA8Hmio8BzLnVRJKG7TZwdD47FdY6tjCGg7zXVviISBaJdzVSf6wYAg9tkPwtGykVsCQQCinix+AyM7g11WjBD0be4kwHqWlKJ8kCApIkitrbwMVaalBfLsmhimUPv6uyEdTNAFVJTjOAvuAuYy6GUauRlTAkEA4/7w5Aib7EmK/v7GSBTpYSwH7plKrfD4apQxP/ZBqr3UlTENuPvFg/WS3vQ1uvYNZCBS+rqtfgVA2AoJA2QmzwJANj+8KgGT9FubfK7XTSOLKXmIq8lD93gBMpe8VSw7KoY8RJsacjHp/TnRBdD9eA/S6aRQ0wg0ep8++kaqy+Jp/wJAdKBASp/9fgp0iJiVxG/IEx/2fJDDATsEqqAeA/F82HutSJQglFnqh5/rGZs9Eq7ovzlSa7qOLzRv6KTtt0U95g==";

    @Override
    public MerchantResult<RequestPayMessageDTO> postForPlatformData(String testRequestData, HttpServletResponse response) {
        val getUrl = "http://localhost:80/api/pay/gateway"
        val queryBody = Base64.encode(RSAUtils.encryptByPrivateKey(testRequestData.toByteArray(), PRIVATE_KEY))
        val getUrlConfig = HttpConfig("post", SIGNATURE_METHOD, PRIVATE_KEY, getUrl, APP_ID, VERSION,
                MediaType.APPLICATION_JSON_TYPE, null, queryBody)
        //获取数据后对数据进行解析，并且组装成需要的值
        val merchantResult = jacksonObjectMapper().readValue<MerchantResult<RequestPayMessageDTO>>(queryHttpByConfigs(getUrlConfig))
        if (merchantResult.code!=MerchantResultEnum.EASY_PAY_SUCCESS.code){
            throw BusinessException(MerchantResultEnum.EASY_PAY_FAIL_GETURL)
        }
        merchantResult.code = MerchantResultEnum.MERCHANT_SUCCESS.code
        merchantResult.message = MerchantResultEnum.MERCHANT_SUCCESS.message
        return merchantResult
    }

    fun queryHttpByConfigs(httpConfig: HttpConfig): String {
        val apacheClient = HttpClientBuilder.create().build()
        val client = Client(ApacheHttpClient4Handler(apacheClient, BasicCookieStore(), true))
        client.addFilter(LoggingFilter())
        val providers = client.providers
        val params = Parameters().appId(httpConfig.appId).version(httpConfig.version).signatureMethod(httpConfig.encryptType)
        val secrets = Secrets().appSecret(httpConfig.privateKey)
        val filter = ClientSignatureFilter(providers, params, secrets)
        val resource = client.resource(httpConfig.baseUrl)
        resource.addFilter(filter)
        val beforeSent =if(httpConfig.getQueryParams!=null){
            resource.queryParams(httpConfig.getQueryParams).type(MediaType.APPLICATION_JSON_TYPE)
        }else{
            resource.type(MediaType.APPLICATION_JSON_TYPE)
        }
        return when (httpConfig.httpMethod) {
            "get" -> beforeSent.get(String::class.java)
            "post" -> beforeSent.post(String::class.java, httpConfig.postQueryBody)
            else -> throw BusinessException(MerchantResultEnum.FAIL_UNSUPPORT_HTTP_METHOD)
        }
    }
}
