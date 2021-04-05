package com.tony.sales.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SalesmanTest {

	private static final String CPF = "12345678901";
	private static final String NAME = "Salesman Name";
	private static final BigDecimal SALARY = BigDecimal.valueOf(10000.75);
	private Salesman salesman;

	@BeforeEach
	public void beforeEach() {
		salesman = new Salesman(CPF, NAME, SALARY);
	}

	@Test
	void salesmanToStringTest() {
		final String expected = "Salesman -> [{\"cpf\":\"" + CPF + "\",\"name\":\"" +
				NAME + "\",\"salary\":" + SALARY + "}]";

		assertEquals(expected, salesman.toString());
	}

	@Test
	void salesmanEqualsTest() {
		final Salesman salesmanNull = null;
		final Salesman customerSame = new Salesman(CPF, NAME, SALARY);

		assertEquals(salesman, salesman);
		assertEquals(salesman, customerSame);
		assertNotEquals(salesman, salesmanNull);
		assertNotEquals(salesman, new Customer("1234", NAME, "ADM"));
	}

}