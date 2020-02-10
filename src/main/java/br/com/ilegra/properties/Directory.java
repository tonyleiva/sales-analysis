package br.com.ilegra.properties;

import static br.com.ilegra.properties.DefaultValues.IN_DIRECTORY;
import static br.com.ilegra.properties.DefaultValues.OUT_DIRECTORY;

public class Directory {

	private String inputPath;
	private String outputPath;

	public Directory() {
		inputPath = IN_DIRECTORY;
		outputPath = OUT_DIRECTORY;
	}

	public String getInputPath() {
		return inputPath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public String getOutputPath() {
		return outputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	@Override
	public String toString() {
		return "Directory [input='" + inputPath + "', output='" + outputPath + "']";
	}

}
