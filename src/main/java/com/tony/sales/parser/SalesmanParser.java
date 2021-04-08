package com.tony.sales.parser;

import com.tony.sales.exception.LineException;
import com.tony.sales.model.Salesman;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SalesmanParser extends Parser implements ParserLine {

	private static final String SALESMAN_REGEX = "^(001)ç(\\d{11})ç([\\D]+)ç(\\d*\\.?\\d+)";
	private static final Pattern SALESMAN_PATTERN = getPattern(SALESMAN_REGEX);

	@Override
	public boolean isValid(final String line) {
		return getMatcher(SALESMAN_PATTERN, line).matches();
	}

	@Override
	public Salesman parse(String line) {
		final Matcher matcher = getMatcher(SALESMAN_PATTERN, line);
		if (matcher.matches()) {
			final String cpf = matcher.group(2);
			final String name = matcher.group(3);
			final BigDecimal salary = BigDecimal.valueOf(Double.parseDouble(matcher.group(4)));
			return new Salesman(cpf, name, salary);
		} else {
			throw new LineException("Error parsing the line: " + line);
		}
	}

}
