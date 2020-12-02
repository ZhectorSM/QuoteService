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

	public List<Quote> getMostRecentQuote(String symbol) {

		return jdbcTemplate.query("SELECT SYMBOL, DAY, BID ,ASK FROM QUOTE WHERE SYMBOL = ? ORDER BY DAY DESC LIMIT 1",
				new Object[] { symbol }, new QuoteMapper());
	}

	public List<Quote> getQuoteAtDay(String symbol, String day) {

		return jdbcTemplate.query("SELECT SYMBOL, DAY, BID ,ASK FROM QUOTE WHERE SYMBOL = ? AND DAY = ?",
				new Object[] { symbol, day }, new QuoteMapper());
	}

}
