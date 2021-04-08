package com.tony.sales.parser;

import com.tony.sales.model.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemParserTest {

	@Test
	void isValidTrueTest() {
		final ItemParser parser = new ItemParser();
		assertTrue(parser.isItemsValid("[1-10-100,2-30-2.50,3-40-3.10]"));
		assertTrue(parser.isItemsValid("[1-10-100,3-40-3.10]"));
		assertTrue(parser.isItemsValid("[1-10-100,3-40-3.10]"));
	}

	@Test
	void isValidFalseTest() {
		final ItemParser parser = new ItemParser();
		assertFalse(parser.isItemsValid("[1-10-1002-30-2.50,3-40-3.10]"));
		assertFalse(parser.isItemsValid("[1-10-100,340-3.10]"));
		assertFalse(parser.isItemsValid("[[1-10-100,340-3.10]"));
		assertFalse(parser.isItemsValid("[]"));
	}

	@Test
	void parseOkTest() {
		final ItemParser parser = new ItemParser();

		List<Item> items = parser.parseItems("[1-10-100,2-30-2.50,3-40-3.10]");
		Item first = items.get(0);

		assertEquals(3, items.size());
		assertEquals("1", first.getId());
		assertEquals(10, first.getQuantity());
		assertEquals(BigDecimal.valueOf(100.0), first.getPrice());
	}

}