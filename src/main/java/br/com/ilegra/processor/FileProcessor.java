package br.com.ilegra.processor;

import static br.com.ilegra.constants.Constants.EMPTY;
import static br.com.ilegra.constants.Constants.EXTENSION_FILE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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

	public void process(File file, Path outputDirectory) {
		if (file.exists()) {
			loadContent(file);
			saveOutput(file, outputDirectory);
		} else {
			logger.error(
					"File to process does not exists, PATH=" + file.getAbsolutePath() + " FILENAME=" + file.getName());
		}
	}

	private void loadContent(File file) {
		logger.info("Load file content - FILENAME=" + file.getName());

		String line;
		try (Scanner sc = new Scanner(file)) {
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (isNotBlank(line)) {
					DataType dataType = getLineDataType(line);
					if (dataType != null) {
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
							break;
						}
					} else {
						logger.debug("DataType not found, line ingnored, LINE=" + line);
					}
				}
			}
		} catch (FileNotFoundException e) {
			logger.error("File not found - FILENAME=" + file.getName());
		}
	}

	private DataType getLineDataType(String line) {
		return DataType.parse(line.substring(0, 3));
	}

	private void saveOutput(File file, Path outputDirectory) {
		logger.info("Save the output of file - FILENAME=" + file.getName());

		StringBuilder fileContent = new StringBuilder()
				.append(String.format("- Quantidade de clientes no arquivo de entrada = %d%n", clientList.size()))
				.append(String.format("- Quantidade de vendedor no arquivo de entrada = %d%n", salesList.size()))
				.append(String.format("- ID da venda mais cara = %s%n", getMostExpensiveSaleId()))
				.append(String.format("- O pior vendedor = %s%n", getTheWorstSalesman()));

		File outputFile = new File(outputDirectory.toString());
		if (!outputFile.exists()) {
			outputFile.mkdir();
		}

		String outputFilename = file.getName().replace(EXTENSION_FILE, ".done") + EXTENSION_FILE;
		Path path = Paths.get(outputDirectory.toString(), outputFilename);
		try {
			Files.writeString(path, fileContent.toString(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			logger.error("Error writing the output file, EX={}", e.getCause());
		}
	}

	private String getMostExpensiveSaleId() {
		String mostExpensiveSaleId = EMPTY;
		Optional<Sale> mostExpensiveSale = salesList.stream()
				.max((Sale o1, Sale o2) -> (o1.getTotalSaleAmount().compareTo(o2.getTotalSaleAmount())));
		if (mostExpensiveSale.isPresent())
			mostExpensiveSaleId = mostExpensiveSale.get().getId();
		return mostExpensiveSaleId;
	}

	private String getTheWorstSalesman() {
		String worstSalesman = EMPTY;
		Optional<Sale> minQuantitySale = salesList.stream()
				.min((Sale o1, Sale o2) -> (o1.getTotalSaleAmount().compareTo(o2.getTotalSaleAmount())));
		if (minQuantitySale.isPresent())
			worstSalesman = minQuantitySale.get().getSalesmanName();
		return worstSalesman;
	}
}
