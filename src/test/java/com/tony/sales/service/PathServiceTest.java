package com.tony.sales.service;

import com.tony.sales.exception.PathException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.tony.sales.service.PathService.USER_HOME;
import static org.junit.jupiter.api.Assertions.*;

class PathServiceTest {

	private static final String EMPTY = "";
	private static final String HOME_PATH = System.getProperty(USER_HOME);
	private static final String USER_DIRECTORY = System.getProperty("user.dir");
	private static final String DATA_IN = "/src/test/resources/data/in";
	private static final String DATA_OUT = "/src/test/resources/data/out";
	private static final String DATA_OUT_TEMP = "/src/test/resources/data/out/temp";

	@Test
	void getInputPathOkTest() {
		final String expectedPath = USER_DIRECTORY + DATA_IN;
		final PathService pathService = new PathService(buildInputDirectory(), buildOutputDirectory());

		final Path inputDirectory = pathService.getInputPath();

		assertNotNull(inputDirectory);
		assertEquals(expectedPath, inputDirectory.toString());
	}

	@Test
	void getInputDirectoryOkTest() {
		final String expectedPath = USER_DIRECTORY + DATA_IN;
		final PathService pathService = new PathService(buildInputDirectory(), buildOutputDirectory());

		final String inputDirectory = pathService.getInputDirectory();

		assertNotNull(inputDirectory);
		assertEquals(expectedPath, inputDirectory);
	}

	@Test
	void getInputDirectoryExceptionTest() {
		final String inputPath = buildInputDirectory() + "/invalid";
		final PathService pathService = new PathService(inputPath, buildOutputDirectory());

		assertThrows(
				PathException.class,
				() -> pathService.getInputDirectory()
		);
	}

	@Test
	void getInputDirectoryExceptionIsFileTest() {
		final String inputPath = buildInputDirectory() + File.separator + "example.txt";
		final PathService pathService = new PathService(inputPath, buildOutputDirectory());

		assertThrows(
				PathException.class,
				() -> pathService.getInputDirectory()
		);
	}

	@Test
	void getOutputDirectoryOkTest() {
		final String expectedPath = USER_DIRECTORY + DATA_OUT;
		final PathService pathService = new PathService(buildInputDirectory(), buildOutputDirectory());

		final String outputDirectory = pathService.getOutputDirectory();

		assertNotNull(outputDirectory);
		assertEquals(expectedPath, outputDirectory);
	}

	@Test
	void getOutputDirectoryCreatedTest() {
		final String expectedPath = USER_DIRECTORY + DATA_OUT_TEMP;
		final PathService pathService = new PathService(buildInputDirectory(), buildOutputTempDirectory());

		final String outputDirectory = pathService.getOutputDirectory();

		assertNotNull(outputDirectory);
		assertEquals(expectedPath, outputDirectory);
	}

	@BeforeEach
	private void beforeEach() throws IOException {
		final Path tempPath = Path.of(USER_DIRECTORY, DATA_OUT);
		Files.createDirectories(tempPath);
	}

	@AfterEach
	private void afterEach() throws IOException {
		final Path tempPath = Path.of(USER_DIRECTORY, DATA_OUT_TEMP);
		Files.deleteIfExists(tempPath);
	}

	private String buildInputDirectory() {
		return USER_DIRECTORY.replaceFirst(HOME_PATH, EMPTY) + DATA_IN;
	}

	private String buildOutputDirectory() {
		return USER_DIRECTORY.replaceFirst(HOME_PATH, EMPTY) + DATA_OUT;
	}

	private String buildOutputTempDirectory() {
		return USER_DIRECTORY.replaceFirst(HOME_PATH, EMPTY) + DATA_OUT_TEMP;
	}

}