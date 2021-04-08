package com.tony.sales.service;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class StarterService {

	private final FileMonitorService fileMonitorService;

	public StarterService(final FileMonitorService fileMonitorService) {
		this.fileMonitorService = fileMonitorService;
	}

	@PostConstruct
	public void startProcess() {
		try {
			fileMonitorService.processFilesInDirectory();
			fileMonitorService.processFilesEnteringInDirectory();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

}
