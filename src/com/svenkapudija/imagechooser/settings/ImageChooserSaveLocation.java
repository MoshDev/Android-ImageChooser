package com.svenkapudija.imagechooser.settings;

import java.io.File;

import android.content.Context;
import android.os.Environment;

import com.svenkapudija.imagechooser.ImageExtension;
import com.svenkapudija.imagechooser.StorageOption;

public class ImageChooserSaveLocation {

	private StorageOption storageOption;
	private String path;
	
	public ImageChooserSaveLocation(StorageOption storageOption, String directory, String imageName, ImageExtension extension) {
		this.storageOption = storageOption;
		this.path =  directory + "/" + imageName + "." + extension.toString().toLowerCase();
	}
	
	public ImageChooserSaveLocation(StorageOption saveLocation, String directory, String imageName) {
		this(saveLocation, directory, imageName, ImageExtension.JPG);
	}
	
	public File getFile(Context context) {
		if(storageOption == StorageOption.SD_CARD_ROOT) {
			return new File(
					Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/" + path
			);
		} else if(storageOption == StorageOption.SD_CARD_APP_ROOT) {
			return new File(
					context.getExternalFilesDir(null).getAbsolutePath()
					+ "/" + path
			);
		} else {
			return new File(
					context.getFilesDir().getAbsolutePath()
					+ "/" + path
			);
		}
	}
	
}
