package com.tony.sales.service;

import com.tony.sales.model.Customer;
import com.tony.sales.model.Sale;
import com.tony.sales.model.Salesman;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
public class SalesReportService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesReportService.class);
	private final FileService fileService;
	private final ParserService parserService;

	public SalesReportService(final FileService fileService, final ParserService parserService) {
		this.fileService = fileService;
		this.parserService = parserService;
	}

	public void createReport(final String filename) {
		LOGGER.info("Creating the sales report - FILENAME={}", filename);
		List<String> lines = fileService.getFileContent(filename);

		final List<Salesman> salesmanList = parserService.getSalesmanList(lines);
		final List<Customer> customerList = parserService.getCustomerList(lines);
		final List<Sale> salesList = parserService.getSaleList(lines);

		fileService.saveFileContent(filename, createFileContent(salesmanList, customerList, salesList));
	}

	private List<String> createFileContent(List<Salesman> salesmanList, List<Customer> customerList, List<Sale> salesList) {
		final List<String> lines = new ArrayList<>();

		lines.add(String.format("- Quantidade de clientes no arquivo de entrada = %d", customerList.size()));
		lines.add(String.format("- Quantidade de vendedor no arquivo de entrada = %d", salesmanList.size()));
		lines.add(String.format("- ID da venda mais cara = %s", getMostExpensiveSaleId(salesList)));
		lines.add(String.format("- O pior vendedor = %s", getTheWorstSalesman(salesList)));

		return lines;
	}

	private String getMostExpensiveSaleId(final List<Sale> salesList) {
		return salesList.stream()
				.max(Comparator.comparing(Sale::getTotalSaleAmount))
				.map(Sale::getSaleId)
				.orElse(EMPTY);
	}

	private String getTheWorstSalesman(final List<Sale> salesList) {
		return salesList.stream()
				.min(Comparator.comparing(Sale::getTotalSaleAmount))
				.map(Sale::getSalesmanName)
				.orElse(EMPTY);
	}

}
