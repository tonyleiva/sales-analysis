package com.tony.sales.parser;

import com.tony.sales.exception.ParseLineException;
import com.tony.sales.model.Salesman;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SalesmanParserTest {

	@Test
	void isValidTrueTest() {
		final SalesmanParser parser = new SalesmanParser();

		assertTrue(parser.isValid("001ç34567891234çPedro Paçocaç50000"));
		assertTrue(parser.isValid("001ç32456788434çPauloç40000.99"));
	}

	@Test
	void isValidFalseTest() {
		final SalesmanParser parser = new SalesmanParser();

		assertFalse(parser.isValid("001ç3456789234çPedro Paçocaç50000."));
		assertFalse(parser.isValid("001ç345678865z34çPauloç40000.99"));
	}

	@Test
	void parseOkTest() {
		final SalesmanParser parser = new SalesmanParser();
		final Salesman salesman = parser.parse("001ç32456788654çPauloPaçocaç40000.99");

		assertEquals("32456788654", salesman.getCpf());
		assertEquals("PauloPaçoca", salesman.getName());
		assertEquals(BigDecimal.valueOf(40000.99), salesman.getSalary());
	}

	@Test
	void parseExceptionTest() {
		final SalesmanParser parser = new SalesmanParser();

		assertThrows(ParseLineException.class, () -> parser.parse("001ç32456788650PauloPaçocaç40000.99"));
	}

}