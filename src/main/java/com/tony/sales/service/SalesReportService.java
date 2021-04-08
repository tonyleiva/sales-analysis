package com.tony.sales.service;

import com.tony.sales.model.LineLayout;
import com.tony.sales.model.LineLayoutType;
import com.tony.sales.model.Sale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
		final List<String> lines = fileService.getFileContent(filename);
		final Map<LineLayoutType, List<LineLayout>> layoutLines = parserService.getLineLayoutMap(lines);

		final List<LineLayout> salesmanList = layoutLines.get(LineLayoutType.SALESMAN);
		final List<LineLayout> customerList = layoutLines.get(LineLayoutType.CUSTOMER);
		final List<LineLayout> salesList = layoutLines.get(LineLayoutType.SALE);

		fileService.saveFileContent(filename, createFileContent(salesmanList, customerList, salesList));
	}

	private List<String> createFileContent(final List<LineLayout> salesmanList, final List<LineLayout> customerList,
										   final List<LineLayout> salesList) {
		final List<String> lines = new ArrayList<>();

		lines.add(String.format("- Quantidade de clientes no arquivo de entrada = %d", customerList.size()));
		lines.add(String.format("- Quantidade de vendedor no arquivo de entrada = %d", salesmanList.size()));
		lines.add(String.format("- ID da venda mais cara = %s", getMostExpensiveSaleId(salesList)));
		lines.add(String.format("- O pior vendedor = %s", getTheWorstSalesman(salesList)));

		return lines;
	}

	private String getMostExpensiveSaleId(final List<LineLayout> salesList) {
		return salesList.stream()
				.map(Sale.class::cast)
				.max(Comparator.comparing(Sale::getTotalSaleAmount))
				.map(Sale::getSaleId)
				.orElse(EMPTY);
	}

	private String getTheWorstSalesman(final List<LineLayout> salesList) {
		return salesList.stream()
				.map(Sale.class::cast)
				.min(Comparator.comparing(Sale::getTotalSaleAmount))
				.map(Sale::getSalesmanName)
				.orElse(EMPTY);
	}

}
