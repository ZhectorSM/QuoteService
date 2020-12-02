package com.quotemedia.interview.quoteservice.dto;

import java.time.LocalDate;

public class Quote {

	private String symbol;
	private LocalDate day;
	private float bid;
	private float ask;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public LocalDate getDay() {
		return day;
	}

	public void setDay(LocalDate day) {
		this.day = day;
	}

	public float getBid() {
		return bid;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}

	public float getAsk() {
		return ask;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}

	@Override
	public String toString() {
		return "Quote [symbol=" + symbol + ", day=" + day + ", bid=" + bid + ", ask=" + ask + ", getSymbol()="
				+ getSymbol() + ", getDay()=" + getDay() + ", getBid()=" + getBid() + ", getAsk()=" + getAsk()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

}
