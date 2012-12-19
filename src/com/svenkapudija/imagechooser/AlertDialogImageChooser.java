package com.svenkapudija.imagechooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.svenkapudija.imagechooser.exceptions.PermissionNotFoundException;
import com.svenkapudija.imagechooser.exceptions.SDCardNotFoundException;

public class AlertDialogImageChooser extends GeneralImageChooser {

	public AlertDialogImageChooser(Activity activity, int requestCode) {
		super(activity, requestCode);
	}
	
	@Override
	public void show() {
		String[] items = new String[] {activity.getString(R.string.gallery), activity.getString(R.string.camera)};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(activity.getString(R.string.choose_an_image));
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
