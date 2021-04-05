package com.tony.sales.parser;

import com.tony.sales.exception.ParseLineException;
import com.tony.sales.model.Item;
import com.tony.sales.model.Sale;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SaleParser extends Parser {

	private static final String SALE_REGEX = "^(003)ç(\\d+)ç(\\[[0-9\\-\\,\\.]+\\])ç([\\D]+)";
	private static final Pattern SALE_PATTERN = Pattern.compile(SALE_REGEX, Pattern.CANON_EQ);
	private final ItemParser itemParser;

	public SaleParser(final ItemParser itemParser) {
		this.itemParser = itemParser;
	}

	@Override
	public boolean isValid(final String line) {
		return getMatcher(SALE_PATTERN, line).matches();

	}

	@Override
	public Sale parse(String line) {
		final Matcher matcher = getMatcher(SALE_PATTERN, line);
		if (matcher.matches()) {
			final String id = matcher.group(2);
			final List<Item> itemList = itemParser.parse(matcher.group(3));
			final String salesmanName = matcher.group(4);
			return new Sale(id, itemList, salesmanName);
		} else {
			throw new ParseLineException("Error parsing the line " + line);
		}
	}

}
