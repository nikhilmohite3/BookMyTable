package com.stackroute;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stackroute.controller.PaymentController;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.core.env.Environment;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentServiceApplicationTests {


	@Autowired
	private PaymentController controller;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate resttemplate;

	@Autowired
	private Environment env;



	@Test

	void loadContext() {

		assertThat(controller).isNotNull();
	}


	@Test
	public void updatePaymenntTest() {
		try {

			RazorpayClient razorpay = new RazorpayClient(env.getProperty("rzp_key_id"), env.getProperty("rzp_key_secret"));
			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", 1*100); // amount in the smallest currency
			orderRequest.put("currency", env.getProperty("rzp_currency"));
			orderRequest.put("receipt", "test");
			Order order = razorpay.orders.create(orderRequest);
			assertThat(orderRequest.get("amount")).isEqualTo(order.get("amount"));
			assertThat(orderRequest.get("currency")).isEqualTo(order.get("currency"));
			assertThat(orderRequest.get("receipt")).isEqualTo(order.get("receipt"));
			assertThat(this.resttemplate.getForObject("http://localhost:"+port+"/", String.class).contains("Hellow"));
		} catch (RazorpayException e) {
			e.getMessage();
		} catch (JSONException e) {
			e.printStackTrace();
		}


	}
}
