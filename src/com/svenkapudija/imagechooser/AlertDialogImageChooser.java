package com.svenkapudija.imagechooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.svenkapudija.imagechooser.exceptions.PermissionNotFoundException;
import com.svenkapudija.imagechooser.exceptions.SDCardNotFoundException;
import com.svenkapudija.imagechooser.settings.AlertDialogImageChooserSettings;

public class AlertDialogImageChooser extends GeneralImageChooser {

	public AlertDialogImageChooser(Activity activity, int requestCode, AlertDialogImageChooserSettings settings) {
		super(activity, requestCode, settings);
		
		if(settings == null) {
			this.settings = new AlertDialogImageChooserSettings(
					"Choose an image",
					"Gallery",
					"Camera"
			);
		}
	}
	
	public AlertDialogImageChooser(Activity activity, int requestCode) {
		this(activity, requestCode, null);
	}
	
	@Override
	public void show() {
		AlertDialogImageChooserSettings alertDialogSettings = (AlertDialogImageChooserSettings) settings;
		
		String[] items = new String[] {alertDialogSettings.getGalleryString(), alertDialogSettings.getCameraString()};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(alertDialogSettings.getTitle());
		builder.setItems(items, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int position) {
				dialog.cancel();
				
				if(position == 0) {
					openGallery();
				} else {
					try {
						openCamera();
					} catch (PermissionNotFoundException e) {
						e.printStackTrace();
					} catch (SDCardNotFoundException e) {
						e.printStackTrace();
						showMessage("Please insert SDCard to take pictures.");
					}
				}
			}
		});
		
		builder.create().show();
	}
	
	private void showMessage(String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		builder.create().show();
	}
	
}
