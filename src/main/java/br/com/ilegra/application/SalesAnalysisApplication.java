package br.com.ilegra.application;

import static br.com.ilegra.constants.Constants.OUT_DIRECTORY;
import static br.com.ilegra.constants.Constants.IN_DIRECTORY;
import static br.com.ilegra.constants.Constants.USER_HOME;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import br.com.ilegra.service.PollingFilesService;
import br.com.ilegra.service.PollingFilesServiceImpl;

public class SalesAnalysisApplication {

	private static final Logger logger = LogManager.getLogger(SalesAnalysisApplication.class);

	public static void main(String[] args) {
		logger.info("Application started");

		Path inputDirectory = Paths.get(System.getProperty(USER_HOME) + IN_DIRECTORY);
		Path outputDirectory = Paths.get(System.getProperty(USER_HOME) + OUT_DIRECTORY);
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
			logger.error("There is something wrong with the directory - PATH=" + inputDirectory.toAbsolutePath());
		}

		logger.info("Application ended");
	}

	/**
	 * Retrieve an implementation of PollingFilesService 
	 * @return PollingFilesService implementation
	 */
	private static PollingFilesService getPollingFilesService() {
		return new PollingFilesServiceImpl();
	}
}
