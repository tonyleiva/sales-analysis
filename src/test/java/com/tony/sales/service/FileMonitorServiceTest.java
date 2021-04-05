package com.tony.sales.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.file.Path;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileMonitorServiceTest {

	private static final String RESOURCES_DATA_IN = "src/test/resources/data/in/";

	@InjectMocks
	private FileMonitorService fileMonitorService;

	@Mock
	private SalesReportService salesReportService;

	@Mock
	private PathService pathService;

	@Test
	void processFilesInDirectoryOkTest() {
		ReflectionTestUtils.setField(fileMonitorService, "fileExtension", ".dat");
		when(pathService.getInputPath()).thenReturn(Path.of(RESOURCES_DATA_IN));

		fileMonitorService.processFilesInDirectory();

		verify(salesReportService, times(1)).createReport("example.dat");
	}

	@Test
	void processFilesInDirectoryNoFilesTest() {
		ReflectionTestUtils.setField(fileMonitorService, "fileExtension", ".java");
		when(pathService.getInputPath()).thenReturn(Path.of(RESOURCES_DATA_IN));

		fileMonitorService.processFilesInDirectory();

		verifyNoInteractions(salesReportService);
	}

}