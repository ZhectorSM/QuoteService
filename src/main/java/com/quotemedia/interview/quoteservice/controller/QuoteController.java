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
import com.quotemedia.interview.quoteservice.dto.SymbolResponse;
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

	/**
	 * Returns the most recent quote for the specified symbol
	 * 
	 * @param symbol identifier for something that can be traded
	 * @return Response entity with status code and quote
	 * @throws ServletException
	 */
	@RequestMapping(value = "symbols/{symbol}/quotes/latest", method = RequestMethod.GET)
	public ResponseEntity<QuoteResponse> getLatestQuote(@PathVariable(value = "symbol") String symbol)
			throws ServletException {

		// 400 Bad Request
		if (symbol.length() < SYMBOL_MIN_LENGTH || symbol.length() > SYMBOL_MAX_LENGTH) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// 404 Not Found
		List<Quote> mostRecentQuote = quoteRepo.getMostRecentQuote(symbol.toUpperCase());
		if (mostRecentQuote.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// 200 OK
		QuoteResponse quoteResponse = new QuoteResponse(mostRecentQuote.get(0).getBid(),
				mostRecentQuote.get(0).getAsk());
		return new ResponseEntity<>(quoteResponse, HttpStatus.OK);
	}

	/**
	 * Returns the symbol with the highest ask for a given day
	 * 
	 * @param day given day to obtain the quote
	 * @return Response entity with status code and symbol
	 * @throws ServletException
	 */
	@RequestMapping(value = "symbols/{day}/highest", method = RequestMethod.GET)
	public ResponseEntity<SymbolResponse> getSymbolAtDay(@PathVariable(value = "day") String day)
			throws ServletException {

		// 400 Bad Request
		if (!DateUtils.isValidDate(day, DATE_FORMAT)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		// 404 Not Found
		List<Quote> highestSymbol = quoteRepo.getHighestSymbolAtDay(day);
		if (highestSymbol.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		// 200 OK
		SymbolResponse symbolResponse = new SymbolResponse(highestSymbol.get(0).getSymbol());
		return new ResponseEntity<>(symbolResponse, HttpStatus.OK);
	}

}
