package br.com.ilegra.util;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.ilegra.model.Client;
import br.com.ilegra.model.Sale;
import br.com.ilegra.model.Salesman;

public class ParserTest {

	@Test
	public void parseSalesmanTest() {
		String salesmanLine = "001ç3245678865434çPauloPaçocaç40000.99";
		Salesman salesman = Parser.parseSalesman(salesmanLine);
		assertTrue("3245678865434".equals(salesman.getCpf()));
		assertTrue("PauloPaçoca".equals(salesman.getName()));
		assertTrue(Double.valueOf("40000.99").compareTo(salesman.getSalary()) == 0);
	}

	@Test
	public void parseClientTest() {
		String clientLine = "002ç2345675434544345çJose da SilvaçRural";
		Client client = Parser.parseClient(clientLine);
		assertTrue("2345675434544345".equals(client.getCnpj()));
		assertTrue("Jose da Silva".equals(client.getName()));
		assertTrue("Rural".equals(client.getBusinessArea()));
	}

	@Test
	public void parseSaleTest() {
		String saleLine = "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro";
		Sale sale = Parser.parseSales(saleLine);
		assertTrue("10".equals(sale.getId()));
		assertTrue("Pedro".equals(sale.getSalesmanName()));
		assertTrue(3 == sale.getItemList().size());
	}
}
