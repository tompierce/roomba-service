package com.tompierce.roomba;

import com.tompierce.roomba.model.RoomCoordinates;

public class RoombaServiceResponseImpl implements RoombaServiceResponse {

	private final RoomCoordinates coords;
	private final int patches;
	private final long simId;

	public RoombaServiceResponseImpl(final long simId, final RoomCoordinates coords, final int patches) {
		this.simId = simId;
		this.coords = coords;
		this.patches = patches;
	}

	public RoomCoordinates getCoords() {
		return coords;
	}

	public int getPatches() {
		return patches;
	}

	public long getSimId() {
		return simId;
	}

}
