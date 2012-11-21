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

    chooser = new AlertDialogImageChooser(this, 100);
    chooser.saveImageTo(StorageOption.SDCARD, "myDirectory", "myFabulousImage"); // Optional

and show it on for example button click

    chooseImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				chooser.show();
			}
		});

Last step is to override `onActivityResult` and call chooser method which has the same name

      @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		chooser.onActivityResult(requestCode, resultCode, data, new ImageChooserListener() {

			@Override
			public void onResult(Bitmap image) {
				// Use the image somewhere
			}
  
			@Override
			public void onError(String message) {
				// Something bad happened :(
			}
		});
	  }