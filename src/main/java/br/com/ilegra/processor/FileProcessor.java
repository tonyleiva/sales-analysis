package br.com.ilegra.processor;

import static br.com.ilegra.constants.Constants.EMPTY;
import static br.com.ilegra.properties.Properties.getProperties;
import static br.com.ilegra.util.Parser.isValidLine;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ilegra.constants.DataType;
import br.com.ilegra.model.Client;
import br.com.ilegra.model.Sale;
import br.com.ilegra.model.Salesman;
import br.com.ilegra.util.Parser;

public class FileProcessor {

	private static final Logger logger = LogManager.getLogger(FileProcessor.class);
	private static final String EXTENSION_FILE = getProperties().getFile().getExtension();

	/**
	 * Load the file content in {@code String} lines
	 * 
	 * @param file The input file to load
	 * @return string list containing the file lines
	 */
	public List<String> loadContent(File file) {
		logger.info("Loading file content - FILENAME={}", file.getName());
		List<String> fileLines = new ArrayList<>();
		try (Stream<String> fileContent = Files.lines(file.toPath())) {
			fileLines = fileContent.filter(StringUtils::isNotBlank).collect(Collectors.toList());
		} catch (IOException e) {
			logger.debug("Error loading the file content, EX={}", e.getCause());
		}
		return fileLines;
	}

	/**
	 * Save the output of the {@code fileLines} list process into a file with name
	 * based on {@code inputFilename}
	 * 
	 * @param fileLines       List containing the lines to process
	 * @param inputFilename   the base to the output file name
	 * @param outputDirectory directory where the output file will be save
	 */
	public void saveOutput(List<String> fileLines, String inputFilename, Path outputDirectory) {
		logger.info("Saving the output file - FILENAME={}", getOutputFilename(inputFilename));

		final List<Salesman> salesmanList = new ArrayList<>();
		final List<Sale> salesList = new ArrayList<>();
		final List<Client> clientList = new ArrayList<>();

		for (String line : fileLines) {
			if (isValidLine(line)) {
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

		File outputFile = new File(outputDirectory.toString());
		if (!outputFile.exists()) {
			outputFile.mkdir();
		}

		Path path = Paths.get(outputDirectory.toString(), getOutputFilename(inputFilename));
		try {
			Files.writeString(path, createFileContent(salesmanList, salesList, clientList), StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error("Error writing the output file, EX={}", e.getCause());
		}
	}

	private String createFileContent(List<Salesman> salesmanList, List<Sale> salesList, List<Client> clientList) {
		StringBuilder fileContent = new StringBuilder()
				.append(String.format("- Quantidade de clientes no arquivo de entrada = %d%n", clientList.size()))
				.append(String.format("- Quantidade de vendedor no arquivo de entrada = %d%n", salesmanList.size()))
				.append(String.format("- ID da venda mais cara = %s%n", getMostExpensiveSaleId(salesList)))
				.append(String.format("- O pior vendedor = %s%n", getTheWorstSalesman(salesList)));
		return fileContent.toString();
	}

	private String getMostExpensiveSaleId(List<Sale> salesList) {
		String mostExpensiveSaleId = EMPTY;
		Optional<Sale> mostExpensiveSale = salesList.stream().max(Comparator.comparing(Sale::getTotalSaleAmount));
		if (mostExpensiveSale.isPresent())
			mostExpensiveSaleId = mostExpensiveSale.get().getId();
		return mostExpensiveSaleId;
	}

	private String getTheWorstSalesman(List<Sale> salesList) {
		String worstSalesman = EMPTY;
		Optional<Sale> minSaleAmount = salesList.stream().min(Comparator.comparing(Sale::getTotalSaleAmount));
		if (minSaleAmount.isPresent())
			worstSalesman = minSaleAmount.get().getSalesmanName();
		return worstSalesman;
	}

	private String getOutputFilename(String inputFilename) {
		return inputFilename.replace(EXTENSION_FILE, ".done") + EXTENSION_FILE;
	}
}
