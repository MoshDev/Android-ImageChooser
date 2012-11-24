package com.svenkapudija.imagechooser.exceptions;

public class PermissionNotFoundException extends ImageChooserException {

	private static final long serialVersionUID = -6149220636285846998L;

	public PermissionNotFoundException(String message) {
        super(message);
    }
	
}
