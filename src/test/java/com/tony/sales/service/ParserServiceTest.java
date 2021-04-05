package com.tony.sales.service;

import com.tony.sales.model.Customer;
import com.tony.sales.model.Sale;
import com.tony.sales.model.Salesman;
import com.tony.sales.parser.CustomerParser;
import com.tony.sales.parser.ItemParser;
import com.tony.sales.parser.SaleParser;
import com.tony.sales.parser.SalesmanParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserServiceTest {

	private ParserService parserService;
	private List<String> lines;

	@BeforeEach
	public void beforeEach() {
		lines = List.of("001ç12345678901çPedroç50000",
				"001ç32456788654çPauloç40000.99",
				"002ç23456754345443çJose da SilvaçRural",
				"002ç23456754334440çEduardo PereiraçRural",
				"003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çPedro",
				"003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

		parserService = new ParserService(new SalesmanParser(),
				new CustomerParser(), new SaleParser(new ItemParser()));
	}
	@Test
	void getSalesmanListOkTest() {
		List<Salesman> salesmenList = parserService.getSalesmanList(lines);

		assertEquals(2, salesmenList.size());
		assertEquals("12345678901", salesmenList.get(0).getCpf());
		assertEquals("Pedro", salesmenList.get(0).getName());
		assertEquals(BigDecimal.valueOf(40000.99), salesmenList.get(1).getSalary());
	}

	@Test
	void getCustomerListOkTest() {
		List<Customer> customerList = parserService.getCustomerList(lines);

		assertEquals(2, customerList.size());
		assertEquals("23456754345443", customerList.get(0).getCnpj());
		assertEquals("Jose da Silva", customerList.get(0).getName());
		assertEquals("Rural", customerList.get(1).getBusinessArea());
	}

	@Test
	void getSaleListOkTest() {
		List<Sale> saleList = parserService.getSaleList(lines);

		assertEquals("10", saleList.get(0).getSaleId());
		assertEquals(3, saleList.get(0).getItemList().size());
		assertEquals("Paulo", saleList.get(1).getSalesmanName());
		assertEquals("Paulo", saleList.get(1).getSalesmanName());
		assertEquals(BigDecimal.valueOf(393.5), saleList.get(1).getTotalSaleAmount());
	}

}