package com.svenkapudija.imagechooser;

import com.svenkapudija.imagechooser.exceptions.ImageChooserException;
import com.svenkapudija.imagechooser.settings.ImageChooserSaveLocation;

import android.content.Intent;

public interface ImageChooser {

	public ImageChooser saveImageTo(ImageChooserSaveLocation saveSettings);
	public ImageChooser saveImageTo(StorageOption saveLocation, String directory, String imageName);
	
	public void show();
	public void openGallery();
	public void openCamera() throws ImageChooserException;
	
	public void onActivityResult(Intent data, ImageChooserListener listener);
	
}
