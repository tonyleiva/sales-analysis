package br.com.ilegra.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.ilegra.model.Client;
import br.com.ilegra.model.Sale;
import br.com.ilegra.model.Salesman;

class ParserTest {

	@Test
	void isNullValidTest() {
		assertFalse(Parser.isValidLine(null));
	}

	@Test
	void isBlankValidTest() {
		assertFalse(Parser.isValidLine(" "));
	}

	@Test
	void isSalesmanValidTest() {
		assertTrue(Parser.isValidLine("001ç1234567891234çPedro Paçocaç50000"));
		assertFalse(Parser.isValidLine("001ç1234567891234çPedro Paçocaç50000."));
		assertTrue(Parser.isValidLine("001ç3245678865434çPauloç40000.99"));
		assertFalse(Parser.isValidLine("001ç3245678865z34çPauloç40000.99"));
	}

	@Test
	void isClientValidTest() {
		assertTrue(Parser.isValidLine("002ç2345675434544345çJose da SilvaçRural"));
		assertFalse(Parser.isValidLine("002ç2345675434544345çJose da Silva Rural"));
		assertTrue(Parser.isValidLine("002ç2345675433444345çEduardo PereiraçIndustrial e Rural"));
		assertFalse(Parser.isValidLine("012ç2345675433444345çEduardo PereiraçRural"));
	}

	@Test
	void isSaleValidTest() {
		assertTrue(Parser.isValidLine("003ç10ç[1-10-100.0]çPedro"));
		assertTrue(Parser.isValidLine("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro"));
		assertFalse(Parser.isValidLine("003ç10ç[1-10-100 2-30-2.50,3-40-3.10]çPedro"));
		assertTrue(Parser.isValidLine("003ç08ç[1-34-10]çPaulo"));
		assertTrue(Parser.isValidLine("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo"));
		assertTrue(Parser.isValidLine("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo Paçoca"));
	}

	@Test
	void parseSalesmanTest() {
		String salesmanLine = "001ç3245678865434çPauloPaçocaç40000.99";
		Salesman salesman = Parser.parseSalesman(salesmanLine);
		assertTrue("3245678865434".equals(salesman.getCpf()));
		assertTrue("PauloPaçoca".equals(salesman.getName()));
		assertTrue(Double.valueOf("40000.99").compareTo(salesman.getSalary()) == 0);
	}

	@Test
	void parseClientTest() {
		String clientLine = "002ç2345675434544345çJose da SilvaçRural";
		Client client = Parser.parseClient(clientLine);
		assertTrue("2345675434544345".equals(client.getCnpj()));
		assertTrue("Jose da Silva".equals(client.getName()));
		assertTrue("Rural".equals(client.getBusinessArea()));
	}

	@Test
	void parseSaleTest() {
		String saleLine = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
		Sale sale = Parser.parseSales(saleLine);
		assertTrue("10".equals(sale.getId()));
		assertTrue("Pedro".equals(sale.getSalesmanName()));
		assertTrue(3 == sale.getItemList().size());
	}

}
