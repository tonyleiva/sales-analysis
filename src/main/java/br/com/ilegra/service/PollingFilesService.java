package br.com.ilegra.service;

import java.nio.file.Path;

public interface PollingFilesService {

	/**
	 * Analyze the files into a {@code inputDirectory} and for each file will be
	 * created an output file in {@code outputDirectory} containing the result of
	 * the analysis
	 * 
	 * @param inputDirectory  location of files to process
	 * @param outputDirectory location where the result files will be saved
	 */
	public void processFilesInDirectory(Path inputDirectory, Path outputDirectory);

	/**
	 * Analyze the new added files into a {@code inputDirectory} and for each new
	 * file will be created an output file in {@code outputDirectory} containing the
	 * result of the analysis
	 * 
	 * @param inputDirectory  location of files to process
	 * @param outputDirectory location where the result files will be saved
	 * @throws InterruptedException if interrupted while waiting for new files
	 */
	public void processNewFilesAddedInDirectory(Path inputDirectory, Path outputDirectory) throws InterruptedException;

}
