package com.tony.sales.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SaleTest {

	@Test
	void customerToStringTest() {
		final Sale sale = getSale();
		final String expected = "Sale -> [{\"saleId\":\"00\",\"itemList\":[],\"salesmanName\":\"salesman\"}]";

		assertEquals(expected, sale.toString());
	}

	@Test
	void customerEqualsTest() {
		final Sale saleNull = null;
		final Sale sale = getSale();

		assertEquals(sale, sale);
		assertEquals(sale, getSale());
		assertNotEquals(sale, new Customer("1", "name", "Rural"));
		assertNotEquals(sale, saleNull);
	}

	@Test
	void getTotalSaleAmountOkTest() {
		final List<Item> itemList = new ArrayList<>();
		itemList.add(new Item("01",10, BigDecimal.TEN));
		itemList.add(new Item("02", 2, BigDecimal.valueOf(0.50)));
		final Sale sale = new Sale("00", itemList, "salesman");

		assertEquals(BigDecimal.valueOf(101.0), sale.getTotalSaleAmount());
	}

	@Test
	void getTotalSaleAmountEmptyTest() {
		final Sale sale = getSale();

		assertEquals(BigDecimal.ZERO, sale.getTotalSaleAmount());
	}

	@Test
	void getTotalSaleAmountNullTest() {
		Sale sale = new Sale(null, null, null);
		assertEquals(BigDecimal.ZERO, sale.getTotalSaleAmount());
	}

	private Sale getSale() {
		final List<Item> itemList = new ArrayList<>();
		final Sale sale = new Sale("00", itemList, "salesman");
		return sale;
	}

}
