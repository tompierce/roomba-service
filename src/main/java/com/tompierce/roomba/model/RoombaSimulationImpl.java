package com.tompierce.roomba.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.google.gson.Gson;

public class RoombaSimulationImpl implements RoombaSimulation {

	private final Room room;
	private final Roomba roomba;
	private List<RoomCoordinates> cleanedPatches;
	private RoomCoordinates finalCoords = null;

	public RoombaSimulationImpl(final Room room, final Roomba roomba) {
		Validate.isTrue(roomba.getStartingPosition().getX() < room.getDimensions().getX(),
				"Must start within the room");
		Validate.isTrue(roomba.getStartingPosition().getY() < room.getDimensions().getY(),
				"Must start within the room");
		this.room = room;
		this.roomba = roomba;
		this.cleanedPatches = new ArrayList<RoomCoordinates>();
	}

	public void run() {
		// keep track of roomba position
		int roombaX = roomba.getStartingPosition().getX();
		int roombaY = roomba.getStartingPosition().getY();

		// read instructions 1 by 1
		for (char ch : roomba.getInstructions().toCharArray()) {

			cleanPatchIfDirty(new RoomCoordinates(roombaX, roombaY));

			// apply movement
			switch (ch) {
			case 'N':
				if (roombaY < room.getDimensions().getY() - 1) {
					roombaY += 1;
				}
				break;
			case 'S':
				if (roombaY > 0) {
					roombaY -= 1;
				}
				break;
			case 'E':
				if (roombaX < room.getDimensions().getX() - 1) {
					roombaX += 1;
				}
				break;
			case 'W':
				if (roombaX > 0) {
					roombaX -= 1;
				}
				break;
			default:
				throw new UnsupportedOperationException("Invalid instruction in instruction list: " + ch);
			}
		}

		finalCoords = new RoomCoordinates(roombaX, roombaY);
		cleanPatchIfDirty(finalCoords);
	}

	public Roomba getRoomba() {
		return roomba;
	}

	public Room getRoom() {
		return room;
	}

	public RoomCoordinates getFinalCoordinates() {
		return finalCoords;
	}

	public List<RoomCoordinates> getCleanedPatches() {
		return cleanedPatches;
	}

	private void cleanPatchIfDirty(RoomCoordinates currentPatch) {
		if (room.getDirtPatches().contains(currentPatch)) {
			room.removeDirtPatch(currentPatch);
			cleanedPatches.add(currentPatch);
		}
	}

	@Override
	public String getInstructions() {
		return roomba.getInstructions();
	}

	@Override
	public RoomCoordinates getStartingCoordinates() {
		return roomba.getStartingPosition();
	}

	@Override
	public RoomDimensions getRoomDimensions() {
		return room.getDimensions();
	}

	@Override
	public String getDirtyPatches() {
		Gson gson = new Gson();
		return gson.toJson(room.getDirtPatches());
	}

}
