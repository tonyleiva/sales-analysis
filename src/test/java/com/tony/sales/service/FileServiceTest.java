package com.tony.sales.service;

import com.tony.sales.exception.FileException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

	private static final String RESOURCES_DATA_IN = "src/test/resources/data/in/";
	private static final String RESOURCES_DATA_OUT = "src/test/resources/data/out/";

	@InjectMocks
	private FileService fileService;

	@Mock
	private PathService pathService;

	@Test
	void getFileContentOkTest() {
		when(pathService.getInputDirectory()).thenReturn(RESOURCES_DATA_IN);

		final List<String> lines = fileService.getFileContent("example.dat");

		assertNotNull(lines);
		assertFalse(lines.isEmpty());
		assertEquals(6, lines.size());
	}

	@Test
	void getFileContentEmptyTest() {
		when(pathService.getInputDirectory()).thenReturn(RESOURCES_DATA_IN);

		assertThrows(FileException.class, () -> fileService.getFileContent("empty.dat"));
	}

	@Test
	void saveFileContentOkTest() {
		ReflectionTestUtils.setField(fileService, "fileExtension", ".dat");
		when(pathService.getOutputDirectory()).thenReturn(RESOURCES_DATA_OUT);
		final String filename = "file.dat";
		final String expectedFilename = "file-done.dat";

		final File file = fileService.saveFileContent(filename, Arrays.asList("line1","line2"));

		assertNotNull(file);
		assertTrue(file.isFile());
		assertEquals(expectedFilename, file.getName());
		assertTrue(file.delete());
	}

}