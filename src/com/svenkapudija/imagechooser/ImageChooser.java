package com.svenkapudija.imagechooser;

import java.io.File;

import android.content.Context;
import android.content.Intent;

import com.svenkapudija.imagechooser.exceptions.ImageChooserException;
import com.svenkapudija.imagechooser.settings.ImageChooserSaveLocation;

public interface ImageChooser {

	public File saveImageTo(ImageChooserSaveLocation saveSettings);
	public File saveImageTo(StorageOption saveLocation, String directory, String imageName);
	
	public void show();
	public void openGallery();
	public void openCamera() throws ImageChooserException;
	
	public void onActivityResult(Context activity, Intent data, ImageChooserListener listener);
	
}
