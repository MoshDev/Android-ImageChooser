package com.svenkapudija.imagechooser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import com.svenkapudija.imagechooser.exceptions.ImageChooserException;
import com.svenkapudija.imagechooser.settings.ImageChooserSaveSettings;
import com.svenkapudija.imagechooser.settings.ImageChooserSettings;

public abstract class GeneralImageChooser implements ImageChooser {

	protected Activity activity;
	protected int requestCode;
	protected ImageChooserSettings settings;
	protected ImageChooserSaveSettings saveSettings;
	
	public GeneralImageChooser(Activity activity, int requestCode, ImageChooserSettings settings, ImageChooserSaveSettings saveSettings) {
		this.activity = activity;
		this.requestCode = requestCode;
		this.settings = settings;
		this.saveSettings = saveSettings;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, final Intent data, final ImageChooserListener listener) {
		if (requestCode == this.requestCode && resultCode == Activity.RESULT_OK) {
			new AsyncTask<Integer, Integer, Bitmap>() {

				private String errorMessage;
				private ProgressDialog dialog;
				
				@Override
				protected void onPreExecute() {
					if(settings.showProgressDialog()) {
						dialog = ProgressDialog.show(activity, null, settings.getProgressDialogMessage(), true, false);
					}
				};
				
				@Override
				protected Bitmap doInBackground(Integer... params) {
					Bitmap photo = null;

					if (getSource(data) == ImageSource.GALLERY) {
						try {
							photo = Media.getBitmap(activity.getContentResolver(), data.getData());
						} catch (FileNotFoundException e1) {
							e1.printStackTrace();
							errorMessage = "ImageSource: GALLERY - could not find a file at " + data.toString() + ".";
							return null;
						} catch (IOException e1) {
							e1.printStackTrace();
							errorMessage = "ImageSource: GALLERY - IOException at " + data.toString() + ".";
							return null;
						}
					} else if (getSource(data) == ImageSource.CAMERA) {
						File file = new File(
								Environment.getExternalStorageDirectory().getAbsolutePath() +
								"/" + saveSettings.getPath() + "/" +
								saveSettings.getImageName() + ".jpg"
						);
						
						photo = BitmapFactory.decodeFile(file.getAbsolutePath());
						writeToFile(photo, file);
					} else {
						// Unknown source
						errorMessage = "Unknown image source.";
						return null;
					}

					return photo;
				}
				
				private void writeToFile(Bitmap photo, File file) {
					ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			    	photo.compress(CompressFormat.JPEG, 100, bytes);
			    	
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(bytes.toByteArray());
						fos.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						errorMessage = "Writing image to disk - could not find a file at " + file.getAbsolutePath() + ".";
					} catch (IOException e1) {
						e1.printStackTrace();
						errorMessage = "Writing image to disk - IOException at " + file.getAbsolutePath() + ".";
					}
				}
				
				private ImageSource getSource(Intent data) {
					if(data == null) {
						return ImageSource.CAMERA;
					} else {
						return ImageSource.GALLERY;
					}
				}
				
				@Override
				protected void onPostExecute(Bitmap result) {
					super.onPostExecute(result);
					
					if(dialog != null)
						dialog.cancel();
					
					if(result == null) {
						listener.onError(errorMessage);
					} else {
						listener.onResult(result);
					}
				}
			}.execute();
		}
	}
	
	public void openCamera() throws ImageChooserException {
		if(isSDCardAvailable() == false) {
			throw new ImageChooserException("SDCard is not mounted.");
		}
		
		Intent cameraIntent = null;
		File file = new File(
				Environment.getExternalStorageDirectory().getAbsolutePath() +
				"/" + saveSettings.getPath()
		);
		file.mkdirs();
		
		cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(file, saveSettings.getImageName() + ".jpg")));
		activity.startActivityForResult(cameraIntent, requestCode);
	}
	
	public void openGallery() {
		Intent cameraIntent = null;
		cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
		cameraIntent.setType("image/*");
		activity.startActivityForResult(cameraIntent, requestCode);
	}
	
	private boolean isSDCardAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

}
