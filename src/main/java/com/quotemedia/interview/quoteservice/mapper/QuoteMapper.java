package com.quotemedia.interview.quoteservice.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.quotemedia.interview.quoteservice.dto.Quote;
import com.quotemedia.interview.quoteservice.utils.DateUtils;

public class QuoteMapper implements RowMapper<Quote> {

	@Override
	public Quote mapRow(ResultSet rs, int rowNum) throws SQLException {

		Quote quote = new Quote();
		quote.setSymbol(rs.getString("SYMBOL"));
		quote.setDay(DateUtils.toLocalDate(rs.getString("DAY")));
		quote.setBid(rs.getFloat("BID"));
		quote.setAsk(rs.getFloat("ASK"));

		return quote;

	}

}
