package com.tony.sales.parser;

import com.tony.sales.exception.ParseLineException;
import com.tony.sales.model.Customer;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomerParser extends Parser {

	private static final String CUSTOMER_REGEX = "^(002)ç(\\d{14})ç([\\D]+)ç([\\D]+)";
	private static final Pattern CUSTOMER_PATTERN = Pattern.compile(CUSTOMER_REGEX, Pattern.CANON_EQ);

	@Override
	public boolean isValid(final String line) {
		return getMatcher(CUSTOMER_PATTERN, line).matches();
	}

	@Override
	public Customer parse(String line) {
		final Matcher matcher = getMatcher(CUSTOMER_PATTERN, line);
		if (matcher.matches()) {
			final String cnpj = matcher.group(2);
			final String name = matcher.group(3);
			final String businessArea = matcher.group(4);
			return new Customer(cnpj, name, businessArea);
		} else {
			throw new ParseLineException("Error parsing the line " + line);
		}
	}

}
