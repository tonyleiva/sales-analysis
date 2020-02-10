package br.com.ilegra.properties;

import static br.com.ilegra.properties.DefaultValues.EXTENSION_FILE;

public class File {

	private String extension;

	public File() {
		extension = EXTENSION_FILE;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		return "File [extension='" + extension + "']";
	}

}
