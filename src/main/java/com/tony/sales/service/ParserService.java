package com.tony.sales.service;

import com.tony.sales.model.Customer;
import com.tony.sales.model.Sale;
import com.tony.sales.model.Salesman;
import com.tony.sales.parser.CustomerParser;
import com.tony.sales.parser.Parser;
import com.tony.sales.parser.SaleParser;
import com.tony.sales.parser.SalesmanParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ParserService {

	private final SalesmanParser salesmanParser;
	private final CustomerParser customerParser;
	private final SaleParser saleParser;

	public ParserService(final SalesmanParser salesmanParser,
						 final CustomerParser customerParser,
						 final SaleParser saleParser) {
		this.salesmanParser = salesmanParser;
		this.customerParser = customerParser;
		this.saleParser = saleParser;
	}

	public List<Salesman> getSalesmanList(final List<String> lines) {
		return getParsedStream(lines, salesmanParser)
				.map(Salesman.class::cast)
				.collect(Collectors.toList());
	}

	public List<Customer> getCustomerList(final List<String> lines) {
		return getParsedStream(lines, customerParser)
				.map(Customer.class::cast)
				.collect(Collectors.toList());
	}

	public List<Sale> getSaleList(final List<String> lines) {
		return getParsedStream(lines, saleParser)
				.map(Sale.class::cast)
				.collect(Collectors.toList());
	}

	private Stream<Object> getParsedStream(final List<String> lines, final Parser parser) {
		return lines.stream()
				.filter(line -> parser.isValid(line))
				.map(line -> parser.parse(line));
	}

}
