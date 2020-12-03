package com.quotemedia.interview.quoteservice.test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.quotemedia.interview.quoteservice.dto.QuoteResponse;
import com.quotemedia.interview.quoteservice.dto.SymbolResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class QuoteMediaApiTest {

	@Value("${api.auth.user}")
	private String AUTH_USER;

	@Value("${api.auth.pwd}")
	private String AUTH_PWD;

	@LocalServerPort
	int randomServerPort;

	TestRestTemplate restTemplate;
	URL base;

	@Before
	public void setUp() throws MalformedURLException {
		restTemplate = new TestRestTemplate(AUTH_USER, AUTH_PWD);
		base = new URL("http://localhost:" + randomServerPort);
	}

	@Test
	public void testGetQuoteSuccess() throws Exception {

		String symbol = "MSFT";
		QuoteResponse qResponse = new QuoteResponse(2.51f, 2.96f);

		String baseUrl = base.toString() + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals(qResponse, result.getBody());
	}

	@Test
	public void testGetQuoteLoweCaseSuccess() throws Exception {

		String symbol = "msft";
		QuoteResponse qResponse = new QuoteResponse(2.51f, 2.96f);

		String baseUrl = base.toString() + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals(qResponse, result.getBody());
	}

	@Test
	public void testGetQuoteBadRequest() throws Exception {

		String symbol = "MSFTSSSS";

		String baseUrl = base.toString() + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	public void testGetQuoteNotFound() throws Exception {

		String symbol = "ABCD";

		String baseUrl = base.toString() + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	public void testGetSymbolDaySuccess() throws Exception {

		String day = "20200103";
		SymbolResponse sResponse = new SymbolResponse("GOOG");

		String baseUrl = base.toString() + "/symbols/" + day + "/highest";
		URI uri = new URI(baseUrl);

		ResponseEntity<SymbolResponse> result = restTemplate.getForEntity(uri, SymbolResponse.class);

		Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
		Assert.assertEquals(sResponse, result.getBody());
	}

	@Test
	public void testGetSymbolDayBadRequest() throws Exception {

		String day = "2020-01-03";

		String baseUrl = base.toString() + "/symbols/" + day + "/highest";
		URI uri = new URI(baseUrl);

		ResponseEntity<SymbolResponse> result = restTemplate.getForEntity(uri, SymbolResponse.class);

		Assert.assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
	}

	@Test
	public void testGetSymbolDayNotFound() throws Exception {

		String day = "20200120";

		String baseUrl = base.toString() + "/symbols/" + day + "/highest";
		URI uri = new URI(baseUrl);

		ResponseEntity<SymbolResponse> result = restTemplate.getForEntity(uri, SymbolResponse.class);

		Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	public void testGetQuoteNoAuth() throws Exception {
		// Create new template with no auth
		restTemplate = new TestRestTemplate();

		String symbol = "MSFT";

		String baseUrl = base.toString() + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

	@Test
	public void testGetQuoteWrongCredentials() throws Exception {
		// Create new template with wrong auth data
		restTemplate = new TestRestTemplate("user", "wrongPwd");

		String symbol = "MSFT";

		String baseUrl = base.toString() + "/symbols/" + symbol + "/quotes/latest";
		URI uri = new URI(baseUrl);

		ResponseEntity<QuoteResponse> result = restTemplate.getForEntity(uri, QuoteResponse.class);

		Assert.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
	}

}
