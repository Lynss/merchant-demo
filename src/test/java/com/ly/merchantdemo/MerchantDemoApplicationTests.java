package com.ly.merchantdemo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MerchantDemoApplicationTests {

	@Test
	public void contextLoads() {
		try {
			URL site = new URL("http://vertx.io/");
			BufferedReader in = new BufferedReader(new InputStreamReader(site.openStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
			}
			in.close();

		} catch (Exception e) {

		}

	}

}
