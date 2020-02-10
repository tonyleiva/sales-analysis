package br.com.ilegra.constants;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class DataTypeTest {

	@Test
	void testSalesmanParse() {
		assertTrue(DataType.SALESMAN.equals(DataType.parse("001")));
	}

	@Test
	void testClientParse() {
		assertTrue(DataType.CLIENT.equals(DataType.parse("002")));
	}

	@Test
	void testSaleParse() {
		assertTrue(DataType.SALE.equals(DataType.parse("003")));
	}
	
	@Test
	void testInvalidParse() {
		assertTrue(DataType.INVALID.equals(DataType.parse(null)));
		assertTrue(DataType.INVALID.equals(DataType.parse("004")));
		assertTrue(DataType.INVALID.equals(DataType.parse("0")));
		assertTrue(DataType.INVALID.equals(DataType.parse("ABCD")));
	}
}
