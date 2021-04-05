package com.tony.sales.parser;

import com.tony.sales.model.Item;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.replace;

@Component
public class ItemParser extends Parser {

	private static final String ITEM_REGEX = "(\\d+)-(\\d+)-(\\d*\\.?\\d+)";
	private static final Pattern ITEM_PATTERN = getPattern(ITEM_REGEX);

	@Override
	public boolean isValid(String itemsContent) {
		final Matcher matcher = getMatcher(ITEM_PATTERN, itemsContent);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return validQuantityItems(count, itemsContent);
	}

	@Override
	public List<Item> parse(String line) {
		final Matcher matcher = getMatcher(ITEM_PATTERN, line);
		final List<Item> items = new ArrayList<>();
		while (matcher.find()) {
			final String id = matcher.group(1);
			final Integer quantity = Integer.valueOf(matcher.group(2));
			final BigDecimal price = BigDecimal.valueOf(Double.valueOf(matcher.group(3)));
			items.add(new Item(id, quantity, price));
		}
		return items;
	}

	private boolean validQuantityItems(final int expectedItems, final String itemsContent) {
		final String comma = ",";
		final String hyphen = "-";
		final int expectedProperties = expectedItems * 3;

		final int totalItems = itemsContent.split(comma).length;
		final int totalProperties = replace(itemsContent, comma, hyphen).split(hyphen).length;
		return expectedItems == totalItems && expectedProperties == totalProperties;
	}

}
