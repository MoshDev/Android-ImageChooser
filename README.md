What is Android ImageChooser?
--------
I'm pretty sure that name is self-descriptive - the idea is to have simple library which will manage choosing images
from different sources (`Gallery`, `Camera`), maybe `save it` to some location (internal memory
or SD-Card) and return the `Bitmap` image back.

Usage
--------

Import a project into Eclipse and reference to it from your project as an Android Library project for more info visit
[Referencing Library Project](http://developer.android.com/tools/projects/projects-eclipse.html#ReferencingLibraryProject).

### Permissions

Put these two permissions inside your AndroidManifest

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

### Inside your Activity

    // CHOOSER_IMAGE_REQUEST_CODE is some number
    ImageChooser chooser = new AlertDialogImageChooser(this, CHOOSER_IMAGE_REQUEST_CODE);

    // Optional
    chooser.saveImageTo(StorageOption.SDCARD, "myDirectory", "myFabulousImage");
    chooser.saveImageTo(StorageOption.SDCARD, "anotherDirectory", "myFabulousImageCopy");

    // Show it (open AlertDialog) on button click
	button.setOnClickListener(new OnClickListener() {
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
				public void onResult(Bitmap image, File ... savedImages) {
					// Do something with original image...

					// savedImages is empty if you didn't invoke any
					// saveImageTo method, otherwise those files will be in the
					// same order as you were calling the saveImageTo methods
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

Developed by
------------
* Sven Kapuđija

License
-------

    Copyright 2012 Sven Kapuđija
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.