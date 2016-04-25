package com.tompierce.roomba;

import java.util.List;

import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoomDimensions;

public class RoombaServiceRequestImpl implements RoombaServiceRequest {

	private final String instructions;
	private final RoomDimensions roomSize;
	private final RoomCoordinates startingPosition;
	private final List<RoomCoordinates> dirtPatches;

	public RoombaServiceRequestImpl(String instructions, RoomDimensions roomSize,
			RoomCoordinates startingPosition, List<RoomCoordinates> dirtPatches) {
		this.instructions = instructions;
		this.roomSize = roomSize;
		this.startingPosition = startingPosition;
		this.dirtPatches = dirtPatches;
	}

	@Override
	public String getInstructions() {
		return instructions;
	}

	@Override
	public RoomDimensions getRoomSize() {
		return roomSize;
	}

	@Override
	public RoomCoordinates getStartingPosition() {
		return startingPosition;
	}

	@Override
	public List<RoomCoordinates> getDirtPatches() {
		return dirtPatches;
	}

}
