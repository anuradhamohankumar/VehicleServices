package com.udacity.vmicroservice.pricingservice;


import java.util.List;

import com.udacity.vmicroservice.pricingservice.entity.Price;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PricingServiceApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
	}

	@Test
	public void getAllPrices() throws Exception {
		ResponseEntity<String> response =
				this.restTemplate.getForEntity("http://localhost:" + 8082 + "/prices",String.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}

	@Test
	public void getPrice() {
		ResponseEntity<Price> response =
				this.restTemplate.getForEntity("http://localhost:" + 8082 + "/prices/1", Price.class);

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}


}
