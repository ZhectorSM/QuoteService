package com.quotemedia.interview.quoteservice.dto;

public class QuoteResponse {

	private float bid;
	private float ask;

	public QuoteResponse(float bid, float ask) {
		this.bid = bid;
		this.ask = ask;
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
		return "QuoteResponse [bid=" + bid + ", ask=" + ask + "]";
	}

}
