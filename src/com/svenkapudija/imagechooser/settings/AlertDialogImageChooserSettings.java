package com.svenkapudija.imagechooser.settings;

public class AlertDialogImageChooserSettings implements ImageChooserSettings {

	private String title;
	private String galleryString;
	private String cameraString;
	
	private boolean showProgressDialog = true;
	private String progressDialogMessage = "Getting image...";
	
	public AlertDialogImageChooserSettings(String title, String galleryString, String cameraString) {
		this.title = title;
		this.galleryString = galleryString;
		this.cameraString = cameraString;
	}
	
	public AlertDialogImageChooserSettings(String title, String galleryString, String cameraString, boolean showProgressDialog, String progressDialogMessage) {
		this(title, galleryString, cameraString);
		
		this.showProgressDialog = showProgressDialog;
		this.progressDialogMessage = progressDialogMessage;
	}
	
	public String getTitle() {
		return title;
	}

	public String getGalleryString() {
		return galleryString;
	}

	public String getCameraString() {
		return cameraString;
	}
	
	@Override
	public String getProgressDialogMessage() {
		return progressDialogMessage;
	}
	
	@Override
	public boolean showProgressDialog() {
		return showProgressDialog;
	}
	
}
