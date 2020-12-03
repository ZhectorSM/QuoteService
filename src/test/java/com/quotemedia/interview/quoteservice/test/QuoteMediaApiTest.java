package com.quotemedia.interview.quoteservice.test;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.quotemedia.interview.quoteservice.dto.QuoteResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuoteMediaApiTest {

	@LocalServerPort
	int randomServerPort;

	@Test
	public void testGetQuoteSuccess() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		String symbol = "MSFT";
		QuoteResponse qResponse = new QuoteResponse(2.51f, 2.96f);

		final String baseUrl = "http://localhost:" + randomServerPort + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals(qResponse, result.getBody());
	}

	@Test
	public void testGetQuoteLoweCaseSuccess() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		String symbol = "msft";
		QuoteResponse qResponse = new QuoteResponse(2.51f, 2.96f);

		final String baseUrl = "http://localhost:" + randomServerPort + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals(qResponse, result.getBody());
	}

	@Test
	public void testGetQuoteBadRequest() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		String symbol = "MSFTSSSS";

		final String baseUrl = "http://localhost:" + randomServerPort + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		try {
			restTemplate.getForEntity(uri, QuoteResponse.class);
			Assert.fail("No exception was thrown");
		} catch (HttpClientErrorException ex) {
			// Verify bad request
			Assert.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
		}
	}

	@Test
	public void testGetQuoteNotFound() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		String symbol = "ABCD";

		final String baseUrl = "http://localhost:" + randomServerPort + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		try {
			restTemplate.getForEntity(uri, QuoteResponse.class);
			Assert.fail("No exception was thrown");
		} catch (HttpClientErrorException ex) {
			// Verify not found
			Assert.assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}
	}

	@Test
	public void testGetQuoteDaySuccess() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		String symbol = "MSFT";
		String day = "20200103";
		QuoteResponse qResponse = new QuoteResponse(3.74f, 4.33f);

		final String baseUrl = "http://localhost:" + randomServerPort + "/symbols/" + symbol + "/quotes/" + day;
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals(qResponse, result.getBody());
	}

	@Test
	public void testGetQuoteDayBadRequest() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		String symbol = "MSFT";
		String day = "2020-01-03";

		final String baseUrl = "http://localhost:" + randomServerPort + "/symbols/" + symbol + "/quotes/" + day;
		URI uri = new URI(baseUrl);

		try {
			restTemplate.getForEntity(uri, QuoteResponse.class);
			Assert.fail("No exception was thrown");
		} catch (HttpClientErrorException ex) {
			// Verify bad request
			Assert.assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
		}
	}

	@Test
	public void testGetQuoteDayNotFound() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		String symbol = "MSFT";
		String day = "20200120";

		final String baseUrl = "http://localhost:" + randomServerPort + "/symbols/" + symbol + "/quotes/" + day;
		URI uri = new URI(baseUrl);

		try {
			restTemplate.getForEntity(uri, QuoteResponse.class);
			Assert.fail("No exception was thrown");
		} catch (HttpClientErrorException ex) {
			// Verify bad request
			Assert.assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
		}

	}

}
