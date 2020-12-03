package com.quotemedia.interview.quoteservice.dto;

public class QuoteResponse {

	private float bid;
	private float ask;

	public QuoteResponse() {
	}

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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(ask);
		result = prime * result + Float.floatToIntBits(bid);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuoteResponse other = (QuoteResponse) obj;
		if (Float.floatToIntBits(ask) != Float.floatToIntBits(other.ask))
			return false;
		if (Float.floatToIntBits(bid) != Float.floatToIntBits(other.bid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QuoteResponse [bid=" + bid + ", ask=" + ask + "]";
	}

}
