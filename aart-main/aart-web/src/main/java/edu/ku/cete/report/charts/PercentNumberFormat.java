package edu.ku.cete.report.charts;

import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

public class PercentNumberFormat extends NumberFormat {
	private static final long serialVersionUID = -5385292846249413398L;

	@Override
	public StringBuffer format(double number, StringBuffer toAppendTo,
			FieldPosition pos) {
		return new StringBuffer(Math.abs((long) number) + "%");
	}

	@Override
	public StringBuffer format(long number, StringBuffer toAppendTo,
			FieldPosition pos) {
		return new StringBuffer(Math.abs(number) + "%");
	}

	@Override
	public Number parse(String source, ParsePosition parsePosition) {
		return null;
	}
}
