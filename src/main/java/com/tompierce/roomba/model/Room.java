package com.tompierce.roomba.model;

import java.util.List;

public interface Room {
	RoomDimensions getDimensions();

	List<Coordinates<Integer>> getDirtPatches();

	void addDirtPatch(final Coordinates<Integer> coords);

	void removeDirtPatch(final Coordinates<Integer> currentPatch);
}
