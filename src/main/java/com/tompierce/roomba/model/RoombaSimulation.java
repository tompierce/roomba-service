package com.tompierce.roomba.model;

import java.util.List;

public interface RoombaSimulation extends Runnable {
	RoomCoordinates getFinalCoordinates();

	List<RoomCoordinates> getCleanedPatches();

	String getInstructions();

	RoomCoordinates getStartingCoordinates();

	RoomDimensions getRoomDimensions();

	String getDirtyPatches();
}
