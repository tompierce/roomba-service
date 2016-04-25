package com.tompierce.roomba;

import java.util.List;

import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoomDimensions;

public interface RoombaServiceRequest {
	String getInstructions();
	RoomDimensions getRoomSize();
	RoomCoordinates getStartingPosition();
	List<RoomCoordinates> getDirtPatches();
}
