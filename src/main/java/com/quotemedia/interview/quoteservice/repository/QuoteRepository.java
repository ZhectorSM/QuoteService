package com.quotemedia.interview.quoteservice.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.quotemedia.interview.quoteservice.dto.Quote;
import com.quotemedia.interview.quoteservice.mapper.QuoteMapper;

@Repository
public class QuoteRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	/**
	 * Method to get the most recent quote of a specified symbol
	 * 
	 * @param symbol identifier of the quote to get
	 * @return list of quotes
	 */
	public List<Quote> getMostRecentQuote(String symbol) {

		return jdbcTemplate.query("SELECT SYMBOL, DAY, BID ,ASK FROM QUOTE WHERE SYMBOL = ? ORDER BY DAY DESC LIMIT 1",
				new Object[] { symbol }, new QuoteMapper());
	}

	/**
	 * Method to get the symbol with the highest ask at given day
	 * 
	 * @param day Specified date to get the symbol
	 * @return list of quotes
	 */
	public List<Quote> getHighestSymbolAtDay(String day) {

		return jdbcTemplate.query("SELECT SYMBOL, DAY, BID ,ASK FROM QUOTE WHERE DAY = ? ORDER BY ASK DESC LIMIT 1",
				new Object[] { day }, new QuoteMapper());
	}

}
