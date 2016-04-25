package com.tompierce.roomba;

import com.tompierce.roomba.model.RoomCoordinates;

public interface RoombaServiceResponse {
	RoomCoordinates getCoords();

	int getPatches();

	long getSimId();
}
