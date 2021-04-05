package com.tony.sales.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ItemTest {

	private static final String ID = "128901";
	private static final Integer QUANTITY = 11;
	private static final BigDecimal PRICE = BigDecimal.valueOf(10.09);
	private Item item;

	@BeforeEach
	public void beforeEach() {
		item = new Item(ID, QUANTITY, PRICE);
	}

	@Test
	void itemToStringTest() {
		final String expected = "Item -> [{\"id\":\"" + ID +
				"\",\"quantity\":" + QUANTITY + ",\"price\":" + PRICE + "}]";

		assertEquals(expected, item.toString());
	}

	@Test
	void itemEqualsTest() {
		final Item itemNull = null;
		final Item itemSame = new Item(ID, QUANTITY, PRICE);

		assertEquals(item, item);
		assertEquals(item, itemSame);
		assertNotEquals(item, new Item("006", QUANTITY, PRICE));
		assertNotEquals(item, itemNull);
		assertNotEquals(item, new Salesman("", "", BigDecimal.ZERO));
	}

	@Test
	void getTotalTest() {
		assertEquals(BigDecimal.valueOf(110.99), item.getTotal());
	}

}