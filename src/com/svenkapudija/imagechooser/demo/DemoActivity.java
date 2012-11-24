package com.svenkapudija.imagechooser.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.svenkapudija.imagechooser.AlertDialogImageChooser;
import com.svenkapudija.imagechooser.ImageChooser;
import com.svenkapudija.imagechooser.ImageChooserListener;
import com.svenkapudija.imagechooser.R;
import com.svenkapudija.imagechooser.StorageOption;

public class DemoActivity extends Activity {

	private final static String TAG = DemoActivity.class.getName();
	
	private final static int CHOOSER_IMAGE_REQUEST_CODE = 100;
	private ImageChooser chooser;
	
	private Button chooseImage;
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_demo);
		chooseImage = (Button) findViewById(R.id.button_choose_image);
		imageView = (ImageView) findViewById(R.id.imageview_image);
		
		// Initialize it
		chooser = new AlertDialogImageChooser(this, CHOOSER_IMAGE_REQUEST_CODE);
		chooser.saveImageTo(StorageOption.SDCARD, "myDirectory", "image0");
		
		chooseImage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				chooser.show();
			}
		});
		
	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHOOSER_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			chooser.onActivityResult(data, new ImageChooserListener() {
				
				@Override
				public void onResult(Bitmap image) {
					imageView.setImageBitmap(image);
				}
				
				@Override
				public void onError(String message) {
					// Something bad happened :(
					Toast.makeText(DemoActivity.this, message, Toast.LENGTH_LONG).show();
				}
			});
		}
	}
	
}
