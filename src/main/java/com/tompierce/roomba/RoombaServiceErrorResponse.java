package com.tompierce.roomba;

public class RoombaServiceErrorResponse {
	
	private final String error;
	
	public RoombaServiceErrorResponse(final String message) {
		this.error = message;
	}

	public String getMessage() {
		return error;
	}
}