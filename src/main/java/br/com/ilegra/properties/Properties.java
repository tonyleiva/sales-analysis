package br.com.ilegra.properties;

public class Properties {

	private static Properties self = new Properties();
	private Directory directory;
	private File file;
	private Delimiter delimiter;

	public Properties() {
		directory = new Directory();
		file = new File();
		delimiter = new Delimiter();
	}

	public static Properties getProperties() {
		return self;
	}

	public static void setProperties(Properties properties) {
		self = properties;
	}

	public Directory getDirectory() {
		return directory;
	}

	public void setDirectory(Directory directory) {
		if (directory == null)
			directory = new Directory();
		this.directory = directory;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		if (file == null)
			file = new File();
		this.file = file;
	}

	public Delimiter getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(Delimiter delimiter) {
		if (delimiter == null)
			delimiter = new Delimiter();
		this.delimiter = delimiter;
	}

	@Override
	public String toString() {
		return "Properties [directory=" + directory + ", file=" + file + ", delimiter=" + delimiter + "]";
	}

}
