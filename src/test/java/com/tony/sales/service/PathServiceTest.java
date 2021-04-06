package com.tony.sales.service;

import com.tony.sales.exception.PathException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class PathServiceTest {

	private static final String USER_DIR = "user.dir";
	private static final String USER_DIRECTORY = System.getProperty("user.dir");
	private static final String DATA_IN = "/src/test/resources/data/in";
	private static final String DATA_OUT = "/src/test/resources/data/out";
	private static final String DATA_OUT_TEMP = "/src/test/resources/data/out/temp";

	@Test
	void getInputPathOkTest() {
		final String expectedPath = USER_DIRECTORY + DATA_IN;
		final PathService pathService = new PathService(DATA_IN, DATA_OUT, USER_DIR);

		final Path inputDirectory = pathService.getInputPath();

		assertNotNull(inputDirectory);
		assertEquals(Path.of(expectedPath).toString(), inputDirectory.toString());
	}

	@Test
	void getInputDirectoryOkTest() {
		final String expectedPath = USER_DIRECTORY + DATA_IN;
		final PathService pathService = new PathService(DATA_IN, DATA_OUT, USER_DIR);

		final String inputDirectory = pathService.getInputDirectory();

		assertNotNull(inputDirectory);
		assertEquals(expectedPath, inputDirectory);
	}

	@Test
	void getInputDirectoryExceptionTest() {
		final String inputPath = DATA_IN + "/invalid";
		final PathService pathService = new PathService(inputPath, DATA_OUT, USER_DIR);

		assertThrows(
				PathException.class,
				() -> pathService.getInputDirectory()
		);
	}

	@Test
	void getInputDirectoryExceptionIsFileTest() {
		final String inputPath = DATA_IN + File.separator + "example.txt";
		final PathService pathService = new PathService(inputPath, DATA_OUT, USER_DIR);

		assertThrows(
				PathException.class,
				() -> pathService.getInputDirectory()
		);
	}

	@Test
	void getOutputDirectoryOkTest() {
		final String expectedPath = USER_DIRECTORY + DATA_OUT;
		final PathService pathService = new PathService(DATA_IN, DATA_OUT, USER_DIR);

		final String outputDirectory = pathService.getOutputDirectory();

		assertNotNull(outputDirectory);
		assertEquals(expectedPath, outputDirectory);
	}

	@Test
	void getOutputDirectoryCreatedTest() {
		final String expectedPath = USER_DIRECTORY + DATA_OUT_TEMP;
		final PathService pathService = new PathService(DATA_IN, DATA_OUT_TEMP, USER_DIR);

		final String outputDirectory = pathService.getOutputDirectory();

		assertNotNull(outputDirectory);
		assertEquals(expectedPath, outputDirectory);
	}

}