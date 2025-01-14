package com.app.custom_exceptions;

import com.app.entities.PassengerTypeEnum;

public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String mesg, String id, Long userid) {
		super(mesg);
	}

	public ResourceNotFoundException(String mesg, String email, String emailid) {
		super(mesg);
	}

	public ResourceNotFoundException(String mesg, String email, PassengerTypeEnum passengerTypeEnum) {
		super(mesg);
	}
}
