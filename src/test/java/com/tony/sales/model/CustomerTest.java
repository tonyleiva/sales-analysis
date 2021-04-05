package com.tony.sales.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CustomerTest {

	private static final String CNPJ = "12345678901";
	private static final String NAME = "Customer Name";
	private static final String BUSINESS_AREA = "I.T.";

	@Test
	void customerToStringTest() {
		final Customer customer = new Customer(CNPJ, NAME, "I.T.");
		final String expected = "Customer -> [{\"cnpj\":\"" + CNPJ +
				"\",\"name\":\"" + NAME + "\",\"businessArea\":\"" + BUSINESS_AREA + "\"}]";

		assertEquals(expected, customer.toString());
	}

	@Test
	void customerEqualsTest() {
		final Customer customerNull = null;
		final Customer customer = new Customer(CNPJ, NAME, BUSINESS_AREA);
		final Customer customerSame = new Customer(CNPJ, NAME + "2", BUSINESS_AREA);

		assertEquals(customer, customer);
		assertEquals(customer, customerSame);
		assertNotEquals(customer, new Customer("", NAME, BUSINESS_AREA));
		assertNotEquals(customer, customerNull);
		assertNotEquals(customer, new Salesman("", "", BigDecimal.ZERO));
	}

}