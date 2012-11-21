package com.svenkapudija.imagechooser.settings;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.svenkapudija.imagechooser.ImageExtension;
import com.svenkapudija.imagechooser.SaveLocation;

public class ImageChooserSaveLocation {

	private SaveLocation saveLocation;
	private String path;
	
	public ImageChooserSaveLocation(SaveLocation saveLocation, String directory, String imageName, ImageExtension extension) {
		this.saveLocation = saveLocation;
		this.path =  directory + "/" + imageName + "." + extension.toString().toLowerCase();
	}
	
	public ImageChooserSaveLocation(SaveLocation saveLocation, String directory, String imageName) {
		this(saveLocation, directory, imageName, ImageExtension.JPG);
	}
	
	public SaveLocation getSaveLocation() {
		return saveLocation;
	}
	
	public File getFile(Context context) {
		if(saveLocation == SaveLocation.SDCARD) {
			return new File(
					Environment.getExternalStorageDirectory().getAbsolutePath() +
					"/" + path
			);
		} else {
			return new File(
					context.getFilesDir().getAbsolutePath() +
					"/" + path
			);
		}
	}
	
}
