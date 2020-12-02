package com.quotemedia.interview.quoteservice.controller;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.quotemedia.interview.quoteservice.dto.Quote;
import com.quotemedia.interview.quoteservice.dto.QuoteResponse;
import com.quotemedia.interview.quoteservice.repository.QuoteRepository;
import com.quotemedia.interview.quoteservice.utils.DateUtils;

@RestController
public class QuoteController {

	@Autowired
	QuoteRepository quoteRepo;

	@Value("${symbol.min.length}")
	private int SYMBOL_MIN_LENGTH;

	@Value("${symbol.max.length}")
	private int SYMBOL_MAX_LENGTH;

	@Value("${date.query.format}")
	private String DATE_FORMAT;

	@RequestMapping(value = "symbols/{symbol}/quotes/latest", method = RequestMethod.GET)
	public ResponseEntity<QuoteResponse> getLatestQuote(@PathVariable String symbol) throws ServletException {

		// 400 Bad Request
		if (symbol.length() < SYMBOL_MIN_LENGTH || symbol.length() > SYMBOL_MAX_LENGTH) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// 404 Not Found
		List<Quote> mostRecentQuote = quoteRepo.getMostRecentQuote(symbol);
		if (mostRecentQuote.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// 200 OK
		QuoteResponse quoteResponse = new QuoteResponse(mostRecentQuote.get(0).getBid(),
				mostRecentQuote.get(0).getAsk());
		return new ResponseEntity<>(quoteResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "symbols/{symbol}/quotes/{day}", method = RequestMethod.GET)
	public ResponseEntity<QuoteResponse> getQuoteAtDay(@PathVariable String symbol, @PathVariable String day)
			throws ServletException {

		// 400 Bad Request
		if (symbol.length() < SYMBOL_MIN_LENGTH || symbol.length() > SYMBOL_MAX_LENGTH) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		// Date validation
		if (!DateUtils.isValidDate(day, DATE_FORMAT)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// 404 Not Found
		List<Quote> mostRecentQuote = quoteRepo.getQuoteAtDay(symbol, day);
		if (mostRecentQuote.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// 200 OK
		QuoteResponse quoteResponse = new QuoteResponse(mostRecentQuote.get(0).getBid(),
				mostRecentQuote.get(0).getAsk());
		return new ResponseEntity<>(quoteResponse, HttpStatus.OK);
	}

}
