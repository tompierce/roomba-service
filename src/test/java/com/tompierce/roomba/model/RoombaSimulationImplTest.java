package com.tompierce.roomba.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RoombaSimulationImplTest {

	@Test
	public void testSimulation() {

		Room room = new RoomImpl(new RoomDimensions(5, 5));
		room.addDirtPatch(new RoomCoordinates(1, 0));
		room.addDirtPatch(new RoomCoordinates(2, 2));
		room.addDirtPatch(new RoomCoordinates(2, 3));

		String instructions = "NNESEESWNWW";
		RoomCoordinates startingPosition = new RoomCoordinates(1, 2);
		Roomba roomba = new RoombaImpl(startingPosition, instructions);

		RoombaSimulation simulation = new RoombaSimulationImpl(room, roomba);

		simulation.run();
		assertEquals(new RoomCoordinates(1, 3), simulation.getFinalCoordinates());
		assertEquals(1, simulation.getCleanedPatches().size());
	}

	@Test
	public void testShouldRecordACleanPatchIfStartingCoordsAreSameAsDirtyPatch() {

		Room room = new RoomImpl(new RoomDimensions(5, 5));
		room.addDirtPatch(new RoomCoordinates(1, 0));

		String instructions = "NNN";
		RoomCoordinates startingPosition = new RoomCoordinates(1, 0);
		Roomba roomba = new RoombaImpl(startingPosition, instructions);

		RoombaSimulation simulation = new RoombaSimulationImpl(room, roomba);

		simulation.run();
		assertEquals(new RoomCoordinates(1, 3), simulation.getFinalCoordinates());
		assertEquals(1, simulation.getCleanedPatches().size());
	}

	@Test
	public void testShouldRecordACleanPatchIfFinishingCoordsAreSameAsDirtyPatch() {

		Room room = new RoomImpl(new RoomDimensions(5, 5));
		room.addDirtPatch(new RoomCoordinates(1, 3));

		String instructions = "NNN";
		RoomCoordinates startingPosition = new RoomCoordinates(1, 0);
		Roomba roomba = new RoombaImpl(startingPosition, instructions);

		RoombaSimulation simulation = new RoombaSimulationImpl(room, roomba);

		simulation.run();
		assertEquals(new RoomCoordinates(1, 3), simulation.getFinalCoordinates());
		assertEquals(1, simulation.getCleanedPatches().size());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testShouldThrowOnInvalidInstructions() {

		Room room = new RoomImpl(new RoomDimensions(5, 5));
		room.addDirtPatch(new RoomCoordinates(1, 3));

		String instructions = "WRONG";
		RoomCoordinates startingPosition = new RoomCoordinates(1, 0);
		Roomba roomba = new RoombaImpl(startingPosition, instructions);

		RoombaSimulation simulation = new RoombaSimulationImpl(room, roomba);

		simulation.run();
	}

	@Test
	public void testShouldGoNowhereInA1by1Room() {

		Room room = new RoomImpl(new RoomDimensions(1, 1));
		room.addDirtPatch(new RoomCoordinates(0, 0));

		String instructions = "NNNWWWSSSEEE";
		RoomCoordinates startingPosition = new RoomCoordinates(0, 0);
		Roomba roomba = new RoombaImpl(startingPosition, instructions);

		RoombaSimulation simulation = new RoombaSimulationImpl(room, roomba);

		simulation.run();
		assertEquals(new RoomCoordinates(0, 0), simulation.getFinalCoordinates());
		assertEquals(1, simulation.getCleanedPatches().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowIfTryingToStartOutsideTheRoom() {

		Room room = new RoomImpl(new RoomDimensions(1, 1));

		String instructions = "NNNWWWSSSEEE";
		RoomCoordinates startingPosition = new RoomCoordinates(1, 1);
		Roomba roomba = new RoombaImpl(startingPosition, instructions);

		RoombaSimulation simulation = new RoombaSimulationImpl(room, roomba);

		simulation.run();
	}

	
}
