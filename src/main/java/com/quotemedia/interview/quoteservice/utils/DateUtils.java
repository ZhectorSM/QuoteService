package com.quotemedia.interview.quoteservice.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

	/**
	 * Method to parse a String into a LocalDate Object
	 * 
	 * @param date to be parsed
	 * @return LocalDate
	 */
	public static LocalDate toLocalDate(String date) {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}

	/**
	 * Method to know if the given day is in the right format
	 * 
	 * @param day    to test
	 * @param format pattern to be validated
	 * @return boolean
	 */
	public static boolean isValidDate(String day, String format) {

		DateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		try {
			sdf.parse(day);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}
