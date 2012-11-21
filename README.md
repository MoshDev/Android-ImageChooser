#In development...
--------

What is Android ImageChooser?
--------
I'm pretty sure that name is self-descriptive - the idea is to have simple library which will manage choosing images
from different sources (`Gallery`, `Camera`), maybe `save it` to some location (internal memory
or SD-Card) and return the `Bitmap` image back.

Usage
--------
Initialize it

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