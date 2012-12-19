package com.svenkapudija.imagechooser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;

import com.svenkapudija.imagechooser.exceptions.PermissionNotFoundException;
import com.svenkapudija.imagechooser.exceptions.SDCardNotFoundException;
import com.svenkapudija.imagechooser.settings.ImageChooserSaveLocation;

public abstract class GeneralImageChooser implements ImageChooser {

	protected Activity activity;
	protected int requestCode;
	
	private ImageChooserSaveLocation tmpCameraSaveLocation;
	private ImageChooserSaveLocation saveLocation;
	
	private ImageSource source;
	
	public GeneralImageChooser(Activity activity, int requestCode) {
		this.activity = activity;
		this.requestCode = requestCode;
	}
	
	public File saveImageTo(ImageChooserSaveLocation saveLocation) {
		this.saveLocation = saveLocation;
		return saveLocation.getFile(activity);
	}
	
	@Override
	public File saveImageTo(StorageOption storageOption, String directory, String imageName) {
		ImageChooserSaveLocation location = new ImageChooserSaveLocation(storageOption, directory, imageName);
		this.saveLocation = location;
		return location.getFile(activity);
	}
	
	@Override
	public void onActivityResult(final Intent data, final ImageChooserListener listener) {
		new AsyncTask<Integer, Integer, Bitmap>() {

			private String errorMessage;
			
			@Override
			protected Bitmap doInBackground(Integer... params) {
				Bitmap photo = null;

				if (source == ImageSource.GALLERY) {
					try {
						photo = Media.getBitmap(activity.getContentResolver(), data.getData());
						writePhotoToSaveLocations(photo);
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
						errorMessage = "ImageSource: " + source + " - could not find a file at " + data.toString() + ".";
						return null;
					} catch (IOException e1) {
						e1.printStackTrace();
						errorMessage = "ImageSource: " + source + " - IOException at " + data.toString() + ".";
						return null;
					}
				} else if (source == ImageSource.CAMERA) {
					File tmpFile = tmpCameraSaveLocation.getFile(activity);
					photo = BitmapFactory.decodeFile(tmpFile.getAbsolutePath());
					
					if(saveLocation != null) {
						try {
							writePhotoToSaveLocations(photo);
						} catch (IOException e) {
							e.printStackTrace();
							errorMessage = "ImageSource: " + source + " - IOException.";
							return null;
						}
					}
					
					tmpFile.delete();
				} else {
					// Unknown source
					errorMessage = "Unknown image source.";
					return null;
				}

				return photo;
			}

			private void writePhotoToSaveLocations(Bitmap photo) throws IOException {
				File file = saveLocation.getFile(activity);
				new File(file.getParent()).mkdirs();
				
				writeToFile(photo, file);
			}
			
			private void writeToFile(Bitmap photo, File file) throws IOException {
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		    	photo.compress(CompressFormat.JPEG, 100, bytes);
		    	
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bytes.toByteArray());
				fos.close();
			}
			
			@Override
			protected void onPostExecute(Bitmap result) {
				super.onPostExecute(result);
				
				if(result == null) {
					listener.onError(errorMessage);
				} else {
					File file = null;
					if(saveLocation != null) {
						file = saveLocation.getFile(activity);
					}
					
					listener.onResult(result, file);
				}
			}
			
		}.execute();
	}
	
	public void openCamera() throws PermissionNotFoundException, SDCardNotFoundException {
		source = ImageSource.CAMERA;
		
		if(isWriteExternalEnabled() == false) {
			throw new PermissionNotFoundException("You don't have WRITE_EXTERNAL_STORAGE permission enabled in Android Manifest.");
		}
		
		if(isSDCardAvailable() == false) {
			throw new SDCardNotFoundException("SDCard is not mounted.");
		}
		
		tmpCameraSaveLocation = new ImageChooserSaveLocation(StorageOption.SD_CARD_APP_ROOT, "tmp", Long.toString(new Date().getTime()));
		File saveTo = tmpCameraSaveLocation.getFile(activity);
		
		// Create directories
		new File(saveTo.getParent()).mkdirs();
		
		Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
		cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(saveTo));
		activity.startActivityForResult(cameraIntent, requestCode);
	}
	
	public void openGallery() {
		source = ImageSource.GALLERY;
		
		Intent cameraIntent = null;
		cameraIntent = new Intent(Intent.ACTION_GET_CONTENT);
		cameraIntent.setType("image/*");
		activity.startActivityForResult(cameraIntent, requestCode);
	}
	
	private boolean isWriteExternalEnabled() {
	    String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
	    int res = activity.checkCallingOrSelfPermission(permission);
	    return res == PackageManager.PERMISSION_GRANTED;            
	}
	
	private boolean isSDCardAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

}
