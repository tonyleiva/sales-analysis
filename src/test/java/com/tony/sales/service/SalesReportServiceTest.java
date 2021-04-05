package com.tony.sales.service;

import com.tony.sales.model.Customer;
import com.tony.sales.model.Item;
import com.tony.sales.model.Sale;
import com.tony.sales.model.Salesman;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SalesReportServiceTest {

	private static final String FILENAME = "fileInput";

	@InjectMocks
	private SalesReportService salesReportService;

	@Mock
	private FileService fileService;

	@Mock
	private ParserService parserService;

	@BeforeEach
	public void beforeEach() {
		when(fileService.getFileContent(anyString())).thenReturn(new ArrayList<>());
	}

	@Test
	void createReportTest() {
		when(parserService.getSalesmanList(anyList())).thenReturn(getSalesmanList());
		when(parserService.getCustomerList(anyList())).thenReturn(getCustomerList());
		when(parserService.getSaleList(anyList())).thenReturn(getSalesList());
		final List<String> expectedFile = createExpectedFileContent(getCustomerList().size(), getSalesmanList().size(),
				"001", "Salesman Name 2");

		salesReportService.createReport(FILENAME);

		verify(fileService, times(1)).saveFileContent(FILENAME, expectedFile);
		assertTrue(true);
	}

	private List<Salesman> getSalesmanList() {
		final List<Salesman> salesmanList = new ArrayList<>();
		salesmanList.add(new Salesman("12345678901", "Salesman Name 1", BigDecimal.valueOf(4700.00)));
		salesmanList.add(new Salesman("23456789012", "Salesman Name 2", BigDecimal.valueOf(1700.00)));
		salesmanList.add(new Salesman("34567890123", "Salesman Name 3", BigDecimal.valueOf(8000.25)));
		return salesmanList;
	}

	private List<Customer> getCustomerList() {
		final List<Customer> customerList = new ArrayList<>();
		customerList.add(new Customer("12345678901234", "Customer name 1", "I.T."));
		customerList.add(new Customer("23456789012345", "Customer name 2", "H.R."));
		return customerList;
	}

	private List<Sale> getSalesList() {
		final List<Sale> salesList = new ArrayList<>();

		final List<Item> items1 = List.of(
				new Item("1", 10, BigDecimal.valueOf(100.15)),
				new Item("2", 100, BigDecimal.valueOf(1.0)),
				new Item("3", 50, BigDecimal.valueOf(1000.55)));
		salesList.add(new Sale("001", items1, "Salesman Name 1"));

		final List<Item> items2 = List.of(
				new Item("1", 10, BigDecimal.valueOf(10.05)),
				new Item("2", 99, BigDecimal.valueOf(1.0)));
		salesList.add(new Sale("002", items2, "Salesman Name 2"));

		return salesList;
	}

	private List<String> createExpectedFileContent(
			final int customerQuantity, final int salesmanQuantity, final String saleId, final String salesmanName) {
		final List<String> lines = new ArrayList<>();

		lines.add(String.format("- Quantidade de clientes no arquivo de entrada = %d", customerQuantity));
		lines.add(String.format("- Quantidade de vendedor no arquivo de entrada = %d", salesmanQuantity));
		lines.add(String.format("- ID da venda mais cara = %s", saleId));
		lines.add(String.format("- O pior vendedor = %s", salesmanName));

		return lines;
	}

}