package br.com.ilegra.application;

import static br.com.ilegra.constants.Constants.HOMEPATH;
import static br.com.ilegra.constants.Constants.PROPERTIES_FILE;
import static br.com.ilegra.properties.Properties.getProperties;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;


import br.com.ilegra.properties.Properties;
import br.com.ilegra.service.PollingFilesService;
import br.com.ilegra.service.PollingFilesServiceImpl;

public class SalesAnalysisApplication {

	private static final Logger logger = LogManager.getLogger(SalesAnalysisApplication.class);

	static {
		try {
			Yaml yaml = new Yaml();
			InputStream inputStream = SalesAnalysisApplication.class
					  .getClassLoader()
					  .getResourceAsStream(PROPERTIES_FILE);

			Properties propertiesLoaded = yaml.loadAs(inputStream, Properties.class);
			Properties.setProperties(propertiesLoaded);
			logger.info("Properties loaded from '{}' file", PROPERTIES_FILE);
		} catch (Exception e) {
			logger.error("Error loading the '{}' properties file, using default values, EX={}", PROPERTIES_FILE, e.getCause());
		} finally {
			logger.info("PROPERTIES={}", getProperties());
		}
	}

	public static void main(String[] args) {
		logger.info("Application started");
		Path inputDirectory = Paths.get(System.getenv(HOMEPATH) + getProperties().getDirectory().getInputPath());
		Path outputDirectory = Paths.get(System.getenv(HOMEPATH) + getProperties().getDirectory().getOutputPath());
		if (Files.exists(inputDirectory) && Files.isDirectory(inputDirectory)) {
			PollingFilesService pollingService = getPollingFilesService();
			pollingService.processFilesInDirectory(inputDirectory, outputDirectory);

			try {
				pollingService.processNewFilesAddedInDirectory(inputDirectory, outputDirectory);
			} catch (InterruptedException e) {
				logger.error("Error in watch service while trying to get next watch key - EX={}", e.getCause());
				Thread.currentThread().interrupt();
			}
		} else {
			logger.error("There is something wrong with the directory - PATH={}", inputDirectory.toAbsolutePath());
		}

		logger.info("Application ended");
	}

	/**
	 * Retrieve an implementation of PollingFilesService
	 * 
	 * @return PollingFilesService implementation
	 */
	private static PollingFilesService getPollingFilesService() {
		return new PollingFilesServiceImpl();
	}
}
