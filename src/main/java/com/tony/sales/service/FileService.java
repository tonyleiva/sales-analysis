package com.tony.sales.service;

import com.tony.sales.exception.FileException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);
	private final PathService pathService;
	private final String fileExtension;

	public FileService(final PathService pathService, @Value("${app.file.extension}") final String fileExtension) {
		this.pathService = pathService;
		this.fileExtension = fileExtension;
	}

	public List<String> getFileContent(final String filename) {
		LOGGER.info("Loading file content - FILENAME={}", filename);
		final File file = getInputFile(filename);
		try (Stream<String> fileLines = Files.lines(file.toPath())) {
			return filterNotBlankLines(filename, fileLines);
		} catch (IOException e) {
			throw new FileException("Error reading the files of file", e.getCause());
		}
	}

	public File saveFileContent(final String filename, List<String> lines) {
		Path path = Paths.get(pathService.getOutputDirectory(), getOutputFilename(filename));
		LOGGER.info("Writing file content - FILENAME={}", path.getFileName());
		try {
			return Files.write(path, lines, StandardCharsets.UTF_8).toFile();
		} catch (IOException e) {
			throw new FileException("Error writing the output file", e.getCause());
		}
	}

	private File getInputFile(final String filename) {
		return Path.of(pathService.getInputDirectory(), filename).toFile();
	}

	private String getOutputFilename(final String filename) {
		return filename.replace(fileExtension, "-done") + fileExtension;
	}

	private List<String> filterNotBlankLines(final String file, final Stream<String> lines) {
		final List<String> filteredLines = lines.filter(StringUtils::isNotBlank)
				.map(String::strip)
				.collect(Collectors.toList());

		if (filteredLines.isEmpty())
			throw new FileException("The file " + file + " is empty");

		return filteredLines;
	}

}
