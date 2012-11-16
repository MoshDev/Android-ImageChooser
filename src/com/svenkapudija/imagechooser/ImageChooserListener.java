package com.svenkapudija.imagechooser;

import android.graphics.Bitmap;

public interface ImageChooserListener {

	public void onResult(Bitmap image);
	public void onError(String message);
	
}
