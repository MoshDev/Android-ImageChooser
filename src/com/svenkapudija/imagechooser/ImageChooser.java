package com.svenkapudija.imagechooser;

import android.content.Intent;

public interface ImageChooser {
	
	public void onActivityResult(int requestCode, int resultCode, Intent data, ImageChooserListener listener);
	public void show();
	
}
