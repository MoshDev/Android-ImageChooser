package com.svenkapudija.imagechooser.settings;

public class ImageChooserSaveSettings {

	private String imageName;
	private String path;
	
	public ImageChooserSaveSettings(String path, String imageName) {
		this.imageName = imageName;
		this.path = path;
	}

	public String getImageName() {
		return imageName;
	}
	
	public String getPath() {
		return path;
	}
	
}
