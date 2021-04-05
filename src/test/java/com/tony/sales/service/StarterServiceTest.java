package com.tony.sales.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StarterServiceTest {

	@InjectMocks
	private StarterService starterService;

	@Mock
	private FileMonitorService fileMonitorService;

	@Test
	void startProcessTest() throws InterruptedException {
		starterService.startProcess();

		verify(fileMonitorService, times(1)).processFilesInDirectory();
		verify(fileMonitorService, times(1)).processFilesEnteringInDirectory();
	}

}