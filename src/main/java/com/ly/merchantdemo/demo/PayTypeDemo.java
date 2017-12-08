package com.ly.merchantdemo.demo;


import com.enci.signature.Base64;
import com.enci.signature.Parameters;
import com.enci.signature.Secrets;
import com.enci.signature.api.RSAUtils;
import com.enci.signature.client.ClientSignatureFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ly.merchantdemo.domain.HttpConfigs;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.client.apache4.ApacheHttpClient4Handler;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import java.util.HashMap;
import java.util.Map;

public class PayTypeDemo {

	public static void main(String[] args) {

		String APP_ID = "vqiuj8dpyqt1s622";
		String SIGNATURE_METHOD = "RSA-SHA1";
		String VERSION = "1.0";
		String PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBANPPcZyf0b6mc9sOYCKBFMDlt4gjBMiBJ/l5j6hPgv/7WWqYg" +
				"cHWOq9CfbPAAe/rLavJSevgeps8QNK2A0O2MvcS+aZiUqBZfB2537B30FLJVfcQpRqfBgbIUNLTp20Ggh07CQqsxc8aKYR5rl0kw2Hr4pA6PdIzyMw" +
				"P3x5i8hNhAgMBAAECgYAFJgCz9uqJQNCGHZZWoF6ZxbutUnSWTxViqgLE65HamQh2X9iflo58nhgiG2Owqj3E9DKYyi8QwOkudgAkUDRy46vN6mBpvgTuya" +
				"PbwfVHqLIB5sY7rBwAm9NLoFB6CgLVMMaO8SFlQyXaTITA1BGyPfqA+XX4qAXGEmitNpg6wQJBAPYDJcLalFhwqq6Tp2+AlsQlQpNEBhGp/GnuJS8uVBf3XIx" +
				"p3Ozrrg8reCDIZa2DmETgOC5OURsX7fYfu7BpZ9kCQQDcaNQo0TfOebkEw9sZ0nKVO9u8g49GOGjjyNAYe+7o8B+4dwxfiWSckiUJLmNTfSBS13GapgtNVRdIE" +
				"gwyD5rJAkEA6O0z/Jj+KfqK8H4m4wDKPu7UaSDX6jObJSTxGgnwXzy66Mt2/O9svzo2JDv5YV8edeAh8V3/lvqCOS30hGRYiQJBAJ2gZqIuRd/nE1eCDi5gX8u" +
				"0PEiMOam5a+IDWjc4Yrq6B6Vvliy44HhM2CnBwA0UPkqgg0FbIFgU04qaRZiDqmkCQQDpwKEM5WSQiMaa6aMC0Ml7iI/s1vZBRpIRqJP/hQK7xsnWJ8aDE3l" +
				"m5oOFqbrMrOsKj44BW2d9tYFtWdD8OVTo";

		Map<String, Object> data = new HashMap<>();
		data.put("openId", "8e72156fa71b6b3fc634ed60d66f3538");
		try {
			String valueString = new ObjectMapper().writeValueAsString(data);
			String getUrl = "http://10.110.5.101/api/pay/getpaylist";
			String queryBody = Base64.encode(RSAUtils.encryptByPrivateKey(valueString.getBytes(), PRIVATE_KEY));
			HttpConfigs postUrlConfig = new HttpConfigs("post", SIGNATURE_METHOD, PRIVATE_KEY, getUrl, APP_ID, VERSION, MediaType.APPLICATION_JSON_TYPE, null, queryBody);
			String responsStr = queryHttpByConfigs(postUrlConfig);
			System.out.println(responsStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String queryHttpByConfigs(HttpConfigs httpConfig) {
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
