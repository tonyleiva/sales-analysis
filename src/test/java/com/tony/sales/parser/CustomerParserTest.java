package com.tony.sales.parser;

import com.tony.sales.exception.ParseLineException;
import com.tony.sales.model.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerParserTest {

	@Test
	void isValidTrueTest() {
		final CustomerParser parser = new CustomerParser();
		assertTrue(parser.isValid("002ç12345678901234çJose da SilvaçRural"));
		assertTrue(parser.isValid("002ç45675433444345çEduardo PereiraçIndustrial e Rural"));
	}

	@Test
	void isValidFalseTest() {
		final CustomerParser parser = new CustomerParser();
		assertFalse(parser.isValid("002ç23456754345443çJose da Silva Rural"));
		assertFalse(parser.isValid("012ç25675433444345çEduardo PereiraçRural"));
		assertFalse(parser.isValid("002ç023456754345443çJose da SilvaçRural"));
	}

	@Test
	void parseOkTest() {
		final CustomerParser parser = new CustomerParser();
		final Customer customer = parser.parse("002ç23456754345443çJose da SilvaçRural");

		assertEquals("23456754345443", customer.getCnpj());
		assertEquals("Jose da Silva", customer.getName());
		assertEquals("Rural", customer.getBusinessArea());
	}

	@Test
	void parseExceptionTest() {
		final CustomerParser parser = new CustomerParser();

		assertThrows(ParseLineException.class, () -> parser.parse("002ç234516754345443çJose da SilvaçRural"));
	}

}