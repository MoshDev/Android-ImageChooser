package com.svenkapudija.imagechooser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.svenkapudija.imagechooser.exceptions.PermissionNotFoundException;
import com.svenkapudija.imagechooser.exceptions.SDCardNotFoundException;

public class AlertDialogImageChooser extends GeneralImageChooser {

	private String galleryLabel = "Gallery";
	private String cameraLabel = "Camera";
	private String chooseAnImageLabel = "Choose an image";
	
	public AlertDialogImageChooser(Activity activity, int requestCode) {
		super(activity, requestCode);
	}
	
	@Override
	public void show() {
		String[] items = new String[] {galleryLabel, cameraLabel};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(chooseAnImageLabel);
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
	
	public void setGalleryLabel(String galleryLabel) {
		this.galleryLabel = galleryLabel;
	}
	
	public void setCameraLabel(String cameraLabel) {
		this.cameraLabel = cameraLabel;
	}
	
	public void setChooseAnImageLabel(String chooseAnImageLabel) {
		this.chooseAnImageLabel = chooseAnImageLabel;
	}
	
}
