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

	public boolean isItemsValid(final String line) {
		final Matcher matcher = getMatcher(ITEM_PATTERN, line);
		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return validQuantityItems(count, line);
	}

	public List<Item> parseItems(final String line) {
		final String itemsContent = getItemsContent(line);
		final Matcher matcher = getMatcher(ITEM_PATTERN, itemsContent);
		final List<Item> items = new ArrayList<>();
		while (matcher.find()) {
			final String id = matcher.group(1);
			final Integer quantity = Integer.valueOf(matcher.group(2));
			final BigDecimal price = BigDecimal.valueOf(Double.valueOf(matcher.group(3)));
			items.add(new Item(id, quantity, price));
		}
		return items;
	}

	private boolean validQuantityItems(final int expectedItems, final String line) {
		final String itemsContent = getItemsContent(line);
		final String comma = ",";
		final String hyphen = "-";
		final int expectedProperties = expectedItems * 3;

		final int totalItems = itemsContent.split(comma).length;
		final int totalProperties = replace(itemsContent, comma, hyphen).split(hyphen).length;
		return expectedItems == totalItems && expectedProperties == totalProperties;
	}

	private String getItemsContent(final String line) {
		final int beginIndex = line.indexOf("[");
		final int endIndex = line.indexOf("]");
		return line.substring(beginIndex, endIndex);
	}

}
