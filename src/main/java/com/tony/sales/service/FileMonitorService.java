package com.tony.sales.service;

import com.tony.sales.exception.PathException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Service
public class FileMonitorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileMonitorService.class);
	private final SalesReportService salesReportService;
	private final PathService pathService;
	private final String fileExtension;

	public FileMonitorService(final SalesReportService salesReportService, final PathService pathService,
							  @Value("${app.file.extension}") final String fileExtension) {
		this.salesReportService = salesReportService;
		this.pathService = pathService;
		this.fileExtension = fileExtension;
	}

	public void processFilesInDirectory() {
		LOGGER.info("Process files existing in the directory");
		try (Stream<Path> pathStream = Files.walk(pathService.getInputPath().toAbsolutePath(), 1)) {
			LOGGER.info("ABSOLUTE_PATH={}", pathService.getInputPath().toAbsolutePath());
			pathStream.map(Path::toFile)
					.filter(file -> file.getName().endsWith(fileExtension))
					.forEach(file -> processFile(file.getName()));
		} catch (IOException | PathException e) {
			LOGGER.error("Error walking through the files in path - ERROR={}", e.getMessage());
		}
	}

	public void processFilesEnteringInDirectory() throws InterruptedException {
		LOGGER.info("Process files entering in the directory");
		try {
			final WatchService watchService = getWatchServiceRegistered();

			WatchKey key;
			while ((key = watchService.take()) != null) {
				key.pollEvents().stream()
						.filter(this::isValidEvent)
						.forEach(watchEvent -> processFile(watchEvent.context().toString()));
				key.reset();
			}
		} catch (IOException | PathException e) {
			LOGGER.error("Error watching for new files - ERROR={}", e.getMessage());
		}
	}

	private void processFile(final String filename) {
		try {
			salesReportService.createReport(filename);
		} catch (Exception e){
			LOGGER.error("An error occurs processing file - FILENAME={}, ERROR={}", filename, e.getMessage());
		}
	}

	private WatchService getWatchServiceRegistered() throws IOException {
		final WatchService watchService = FileSystems.getDefault().newWatchService();
		pathService.getInputPath().register(watchService, ENTRY_CREATE);
		return watchService;
	}

	private boolean isValidEvent(final WatchEvent<?> watchEvent) {
		return (watchEvent.kind() == ENTRY_CREATE && watchEvent.context() != null
				&& watchEvent.context().toString().endsWith(fileExtension));
	}

}
