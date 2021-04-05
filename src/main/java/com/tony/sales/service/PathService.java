package com.tony.sales.service;

import com.tony.sales.exception.PathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PathService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PathService.class);
	public static final String USER_HOME = "user.home";
	private final String inputDirectory;
	private final String outputDirectory;

	public PathService(@Value("${app.directory.input}") final String inputDirectory,
					   @Value("${app.directory.output}") final String outputDirectory) {
		this.inputDirectory = inputDirectory;
		this.outputDirectory = outputDirectory;
	}

	public Path getInputPath() {
		final Path inputPath = Path.of(System.getProperty(USER_HOME), this.inputDirectory);
		if (isNotValidDirectory(inputPath)) {
			throw new PathException("Input directory does not exists " + inputPath);
		}
		return inputPath;
	}
	public String getInputDirectory() {
		final Path inputPath = getInputPath();
		LOGGER.info("Input directory {}", inputPath);
		return inputPath.toString();
	}

	public String getOutputDirectory() {
		final Path outputPath = Path.of(System.getProperty(USER_HOME), this.outputDirectory);
		if (isNotValidDirectory(outputPath)) {
			createDirectory(outputPath);
		}
		LOGGER.info("Output directory {}", outputPath);
		return outputPath.toString();
	}

	private void createDirectory(final Path path) {
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			throw new PathException("Error creating directory " + path.toString(), e.getCause());
		}
	}

	private boolean isNotValidDirectory(final Path path) {
		return !(Files.exists(path) && Files.isDirectory(path));
	}

}
