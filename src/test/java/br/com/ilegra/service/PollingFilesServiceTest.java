package br.com.ilegra.service;

import static br.com.ilegra.constants.Constants.IN_DIRECTORY;
import static br.com.ilegra.constants.Constants.OUT_DIRECTORY;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PollingFilesServiceTest {

	private Path inputDirectory;
	private Path outputDirectory;
	
	@BeforeEach
	public void init() {
		inputDirectory = Paths.get("src/test/resources", IN_DIRECTORY);
		outputDirectory = Paths.get("src/test/resources", OUT_DIRECTORY);
	}

	@Test
	public void processFilesInDirectoryTest() {
		PollingFilesService pollingFilesService = new PollingFilesServiceImpl();
		pollingFilesService.processFilesInDirectory(inputDirectory, outputDirectory);

		File outputFile = new File(outputDirectory.toString() + "/example.done.dat");

		assertTrue(outputFile.exists());
	}

	@AfterEach
    public void deleteOutputFile() {
		File outputFile = new File(outputDirectory.toString() + "/example.done.dat");
		outputFile.deleteOnExit();
	}

}
