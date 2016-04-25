package com.tompierce.roomba.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;

public class RoomImpl implements Room {

	private final RoomDimensions dimensions;
	private List<Coordinates<Integer>> dirtPatches;

	public RoomImpl(final RoomDimensions dimensions) {
		Validate.isTrue(!(dimensions.getX() <= 0) && !(dimensions.getY() <= 0), "Dimension cannot be less than 1");
		this.dimensions = dimensions;
		this.dirtPatches = new ArrayList<Coordinates<Integer>>();
	}

	public RoomDimensions getDimensions() {
		return dimensions;
	}

	public List<Coordinates<Integer>> getDirtPatches() {
		return dirtPatches;
	}

	public void addDirtPatch(Coordinates<Integer> coords) {
		dirtPatches.add(coords);
	}

	public void removeDirtPatch(Coordinates<Integer> currentPatch) {
		dirtPatches.remove(currentPatch);
	}

}
