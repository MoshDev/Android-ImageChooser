What is Android ImageChooser?
--------
I'm pretty sure that name is self-descriptive - the idea is to have simple library which will manage choosing images
from different sources (`Gallery`, `Camera`), maybe `save it` to some location (internal memory
or SD-Card) and return the `Bitmap` image back.

Usage
--------

Put these two permissions inside your AndroidManifest

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

Initialize ImageChooser inside your `Activity`

    chooser = new AlertDialogImageChooser(this, CHOOSER_IMAGE_REQUEST_CODE); // CHOOSER_IMAGE_REQUEST_CODE is some number
    chooser.saveImageTo(StorageOption.SDCARD, "myDirectory", "myFabulousImage"); // Optional

and show it on for example button click

	chooseImage.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			chooser.show();
		}
	});

Last step is to override `onActivityResult` and call chooser method which has the same name

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CHOOSER_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			chooser.onActivityResult(data, new ImageChooserListener() {
				
				@Override
				public void onResult(Bitmap image) {
					// Do something with image...
				}
				
				@Override
				public void onError(String message) {
					// Something bad happened :(
				}
			});
		}
	}

### SD-Card
SD-Card is necessary to capture image from camera, otherwise exception will be thrown.