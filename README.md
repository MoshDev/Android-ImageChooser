#In development...
--------

What is Android ImageChooser?
--------
I'm pretty sure that name is self-descriptive - the idea is to have simple library which will manage choosing images
from different sources (`Gallery`, `Camera`), maybe `save it` and `resize it` (via
[Android ImageResizer](https://github.com/svenkapudija/Android-ImageResizer) library) to some location (internal memory
or SD-Card) and return the `Bitmap` image back.

Usage
--------
Define private class variable of type `ImageChooser` in your `Activity`

    private ImageChooser chooser;

initialize it (`CHOOSER_REQUEST_CODE` is Android request code, it can be defined as any number `!= 0`)

    chooser = new AlertDialogImageChooser(this, CHOOSER_REQUEST_CODE, new ImageChooserSaveSettings("imageChooser", "myImage2"));

and show it

    chooser.show();

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