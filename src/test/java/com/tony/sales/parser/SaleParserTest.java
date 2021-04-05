package com.tony.sales.parser;

import com.tony.sales.exception.ParseLineException;
import com.tony.sales.model.Sale;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaleParserTest {

	@Test
	void isValidTrueTest() {
		final SaleParser saleParser = new SaleParser(new ItemParser());

		assertTrue(saleParser.isValid("003ç10ç[1-10-100.0]çPedro"));
		assertTrue(saleParser.isValid("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro"));
		assertTrue(saleParser.isValid("003ç08ç[1-34-10]çPaulo"));
		assertTrue(saleParser.isValid("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo"));
		assertTrue(saleParser.isValid("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo Paçoca"));
	}

	@Test
	void isValidFalseTest() {
		final SaleParser saleParser = new SaleParser(new ItemParser());

		assertFalse(saleParser.isValid("003ç10ç[1-10-100 2-30-2.50,3-40-3.10]çPedro"));
		assertFalse(saleParser.isValid("004ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo Paçoca"));
	}

	@Test
	void parseOkTest() {
		final SaleParser saleParser = new SaleParser(new ItemParser());
		final Sale sale = saleParser.parse("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro");

		assertEquals("10", sale.getSaleId());
		assertEquals("Pedro", sale.getSalesmanName());
		assertEquals(3, sale.getItemList().size());
	}

	@Test
	void parseExceptionTest() {
		final SaleParser parser = new SaleParser(new ItemParser());

		assertThrows(ParseLineException.class, () -> parser.parse("003ç10[1-10-100,2-30-2.50,3-40-3.10]çPedro"));
	}

}