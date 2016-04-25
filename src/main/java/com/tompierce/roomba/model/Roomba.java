package com.tompierce.roomba.model;

public interface Roomba {
	RoomCoordinates getStartingPosition();

	String getInstructions();
}
