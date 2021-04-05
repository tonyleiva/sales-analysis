package com.tony.sales.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Service
public class StarterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StarterService.class);
	private final FileMonitorService fileMonitorService;

	public StarterService(final FileMonitorService fileMonitorService) {
		this.fileMonitorService = fileMonitorService;
	}

	@PostConstruct
	public void startProcess() {
		LOGGER.info("Start processing");
		final long startTime = Instant.now().toEpochMilli();

		try {
			fileMonitorService.processFilesInDirectory();
			fileMonitorService.processFilesEnteringInDirectory();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		LOGGER.info("Process Ended - ELAPSED_TIME={}", (Instant.now().toEpochMilli() - startTime));
	}

}
