package com.tony.sales.service;

import com.tony.sales.exception.LineException;
import com.tony.sales.model.LineLayout;
import com.tony.sales.model.LineLayoutType;
import com.tony.sales.parser.CustomerParser;
import com.tony.sales.parser.ParserLine;
import com.tony.sales.parser.SaleParser;
import com.tony.sales.parser.SalesmanParser;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

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

	public Map<LineLayoutType, List<LineLayout>> getLineLayoutMap(final List<String> lines) {
		return lines.stream()
				.map(line -> getParseLine(line).parse(line))
				.collect(groupingBy(LineLayout::getLineLayoutType));
	}

	private ParserLine getParseLine(final String line) {
		return Stream.of(salesmanParser, customerParser, saleParser)
				.filter(parser -> parser.isValid(line))
				.findFirst().orElseThrow(() -> new LineException("Error parsing the line: " + line));
	}

}
