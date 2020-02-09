package br.com.ilegra.service;

import static br.com.ilegra.constants.Constants.EXTENSION_FILE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.ilegra.processor.FileProcessor;

public class PollingFilesServiceImpl implements PollingFilesService {

	private static final Logger logger = LogManager.getLogger(PollingFilesServiceImpl.class);

	public void processFilesInDirectory(Path inputDirectory, Path outputDirectory) {
		logger.info("Process files in directory");
		try (Stream<Path> pathStream = Files.walk(inputDirectory.toAbsolutePath())) {
			List<File> listFiles = pathStream.filter(file -> file.toString().endsWith(EXTENSION_FILE)).map(Path::toFile)
					.collect(Collectors.toList());

			for (File file : listFiles) {
				processFile(file, outputDirectory);
			}
		} catch (IOException e) {
			logger.error("Error walking through the files in path - EX={}", e.getCause());
		}
	}

	public void processNewFilesAddedInDirectory(Path inputDirectory, Path outputDirectory) throws InterruptedException {
		logger.info("Waiting for new files in the directory to process");
		try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
			inputDirectory.register(watchService, ENTRY_CREATE);

			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> watchEvent : key.pollEvents()) {
					// This key is registered only for ENTRY_CREATE events, but an OVERFLOW event
					// can occur
					if (watchEvent.kind() == ENTRY_CREATE && watchEvent.context() != null
							&& watchEvent.context().toString().endsWith(EXTENSION_FILE)) {
						processFile(
								Paths.get(inputDirectory.toAbsolutePath().toString(), watchEvent.context().toString()).toFile(),
								outputDirectory);
					}
				}
				// If the key is no longer valid, the directory is inaccessible
				if (!key.reset()) {
					break;
				}
			}
		} catch (IOException e) {
			logger.error("Error creating the new watch service - EX={}", e.getCause());
		}
	}

	private void processFile(File file, Path outputDirectory) {
		new FileProcessor().process(file, outputDirectory);
	}
}
