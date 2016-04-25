package com.tompierce.roomba.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RoombaImplTest {

	@Test
	public void testShouldConstructCorrectly() {
		Roomba roomba = new RoombaImpl(new RoomCoordinates(0, 0), "NSEW");
		assertEquals(new RoomCoordinates(0, 0), roomba.getStartingPosition());
		assertEquals("NSEW", roomba.getInstructions());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowWithNegativeCoordinates() {
		new RoombaImpl(new RoomCoordinates(0, -1), "NSEW");
	}

	@Test(expected = NullPointerException.class)
	public void testShouldThrowWithNullInstructions() {
		new RoombaImpl(new RoomCoordinates(0, 1), null);
	}

}
