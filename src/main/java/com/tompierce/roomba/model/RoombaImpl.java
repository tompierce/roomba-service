package com.tompierce.roomba.model;

import org.apache.commons.lang3.Validate;

public class RoombaImpl implements Roomba {

	private final RoomCoordinates startingPosition;
	private final String instructions;

	public RoombaImpl(final RoomCoordinates startingPosition, final String instructions) {
		Validate.notNull(instructions);
		Validate.isTrue(startingPosition.getX() >= 0 && startingPosition.getY() >= 0,
				"Cannot have negative coordinates");

		this.startingPosition = startingPosition;
		this.instructions = instructions;
	}

	public RoomCoordinates getStartingPosition() {
		return startingPosition;
	}

	public String getInstructions() {
		return instructions;
	}

}
