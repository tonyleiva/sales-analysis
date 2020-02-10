package br.com.ilegra.service;

import static br.com.ilegra.properties.Properties.getProperties;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PollingFilesServiceTest {

	private static final String IN_DIRECTORY = getProperties().getDirectory().getInputPath();
	private static final String OUT_DIRECTORY = getProperties().getDirectory().getOutputPath();
			
	private Path inputDirectory;
	private Path outputDirectory;
	
	@BeforeEach
	void init() {
		inputDirectory = Paths.get("src/test/resources", IN_DIRECTORY);
		outputDirectory = Paths.get("src/test/resources", OUT_DIRECTORY);
	}

	@Test
	void processFilesInDirectoryTest() {
		PollingFilesService pollingFilesService = new PollingFilesServiceImpl();
		pollingFilesService.processFilesInDirectory(inputDirectory, outputDirectory);

		File outputFile = new File(outputDirectory.toString() + "/example.done.dat");

		assertTrue(outputFile.exists());
	}

	@AfterEach
    void deleteOutputFile() {
		File outputFile = new File(outputDirectory.toString() + "/example.done.dat");
		outputFile.deleteOnExit();
	}

}
