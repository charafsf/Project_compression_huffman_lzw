package project.common;

import java.io.File;

public class InformationFile {

	private String path;
	private String name;
	private String extension;
	
	public InformationFile(File file) {
		String path = file.getAbsolutePath();
		String name = file.getName();
		this.path = path.replace(name, "");
        this.extension = name.substring(name.lastIndexOf("."));
        this.name = name.replace(extension, "");
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
}
