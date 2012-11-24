package com.svenkapudija.imagechooser.settings;

public class AlertDialogImageChooserSettings implements ImageChooserSettings {

	private boolean showProgressDialog;
	
	public AlertDialogImageChooserSettings(boolean showProgressDialog) {
		this.showProgressDialog = showProgressDialog;
	}
	
	@Override
	public boolean showProgressDialog() {
		return showProgressDialog;
	}
	
}
