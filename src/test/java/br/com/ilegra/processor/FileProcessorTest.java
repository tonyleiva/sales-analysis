package br.com.ilegra.processor;

import static br.com.ilegra.properties.Properties.getProperties;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class FileProcessorTest {

	private String inputFilename = "example.dat";

	@Test
	void loadContentTest() {
		FileProcessor fileProcessor = new FileProcessor();
		Path inputDirectory = Paths.get("src/test/resources", getProperties().getDirectory().getInputPath());
		File file = new File(inputDirectory.toString() + "/" + inputFilename);

		List<String> linesFile = fileProcessor.loadContent(file);
		assertTrue(6 == linesFile.size());
	}

	@Test
	void loadSaveOutput() {
		FileProcessor fileProcessor = new FileProcessor();
		Path outputDirectory = Paths.get("src/test/resources", getProperties().getDirectory().getOutputPath());
		File outputFile = new File(outputDirectory.toString() + "/" + getOutputFilename(inputFilename));
		List<String> fileLines = Arrays.asList(
				"001ç3245678865434çPauloç40000.99",
				"002ç2345675434544345çJose da SilvaçRural", 
				"003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çPaulo");

		fileProcessor.saveOutput(fileLines, inputFilename, outputDirectory);
		assertTrue(outputFile.exists());
		assertTrue(outputFile.delete());
	}

	private String getOutputFilename(String inputFilename) {
		return inputFilename.replace(getProperties().getFile().getExtension(), ".done")
				+ getProperties().getFile().getExtension();
	}

}
