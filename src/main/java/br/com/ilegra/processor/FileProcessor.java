package br.com.ilegra.processor;

import static br.com.ilegra.constants.Constants.EMPTY;
import static br.com.ilegra.constants.Constants.EXTENSION_FILE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ilegra.constants.DataType;
import br.com.ilegra.model.Client;
import br.com.ilegra.model.Sale;
import br.com.ilegra.model.Salesman;
import br.com.ilegra.util.Parser;

public class FileProcessor {
	private static final Logger logger = LogManager.getLogger(FileProcessor.class);

	private List<Salesman> salesmanList = new ArrayList<>();
	private List<Sale> salesList = new ArrayList<>();
	private List<Client> clientList = new ArrayList<>();

	public List<String> loadContent(File file) {
		logger.info("Loading file content - FILENAME={}", file.getName());
		List<String> fileLines = new ArrayList<>();
		try(Stream<String> fileContent = Files.lines(file.toPath())) {
			fileLines = fileContent.collect(Collectors.toList());
		} catch (IOException e) {
			logger.debug("Error loading the file content, EX={}", e.getCause());
		}
		return fileLines;
	}

	public void saveOutput(List<String> fileLines, File file, Path outputDirectory) {
		logger.info("Saving the output file - FILENAME={}", getOutputFilename(file));
		
		for (String line : fileLines) {
			if (isNotBlank(line)) {
				DataType dataType = DataType.parse(line);
				switch (dataType) {
				case SALESMAN:
					salesmanList.add(Parser.parseSalesman(line));
					break;
				case CLIENT:
					clientList.add(Parser.parseClient(line));
					break;
				case SALE:
					salesList.add(Parser.parseSales(line));
					break;
				default:
					logger.debug("DataType not found, line ingnored, LINE={}", line);
					break;
				}
			}
		}

		StringBuilder fileContent = new StringBuilder()
				.append(String.format("- Quantidade de clientes no arquivo de entrada = %d%n", clientList.size()))
				.append(String.format("- Quantidade de vendedor no arquivo de entrada = %d%n", salesList.size()))
				.append(String.format("- ID da venda mais cara = %s%n", getMostExpensiveSaleId()))
				.append(String.format("- O pior vendedor = %s%n", getTheWorstSalesman()));

		File outputFile = new File(outputDirectory.toString());
		if (!outputFile.exists()) {
			outputFile.mkdir();
		}

		String outputFilename = getOutputFilename(file);
		Path path = Paths.get(outputDirectory.toString(), outputFilename);
		try {
			Files.writeString(path, fileContent.toString(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error("Error writing the output file, EX={}", e.getCause());
		}
	}

	private String getMostExpensiveSaleId() {
		String mostExpensiveSaleId = EMPTY;
		Optional<Sale> mostExpensiveSale = salesList.stream().max(Comparator.comparing(Sale::getTotalSaleAmount));
		if (mostExpensiveSale.isPresent())
			mostExpensiveSaleId = mostExpensiveSale.get().getId();
		return mostExpensiveSaleId;
	}

	private String getTheWorstSalesman() {
		String worstSalesman = EMPTY;
		Optional<Sale> minSaleAmount = salesList.stream().min(Comparator.comparing(Sale::getTotalSaleAmount));
		if (minSaleAmount.isPresent())
			worstSalesman = minSaleAmount.get().getSalesmanName();
		return worstSalesman;
	}

	private String getOutputFilename(File file) {
		return file.getName().replace(EXTENSION_FILE, ".done") + EXTENSION_FILE;
	}
}
