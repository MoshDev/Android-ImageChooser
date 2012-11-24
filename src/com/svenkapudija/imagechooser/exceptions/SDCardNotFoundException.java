package com.svenkapudija.imagechooser.exceptions;

public class SDCardNotFoundException extends ImageChooserException {

	private static final long serialVersionUID = 6241352744004862941L;

	public SDCardNotFoundException(String message) {
        super(message);
    }
	
}
