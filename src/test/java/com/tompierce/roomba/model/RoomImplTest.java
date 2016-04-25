package com.tompierce.roomba.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RoomImplTest {

	@Test
	public void testShouldBeAbleToCreateARoomOfSizeXbyY() {
		Integer x = 5;
		Integer y = 6;
		Room room = new RoomImpl(new RoomDimensions(x, y));

		assertEquals(x, room.getDimensions().getX());
		assertEquals(y, room.getDimensions().getY());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowIfTryingToCreateARoomWithZeroDim() {
		new RoomImpl(new RoomDimensions(0, 6));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testShouldThrowIfTryingToCreateARoomWithNegativeDim() {
		new RoomImpl(new RoomDimensions(5, -6));
	}

	@Test(expected = NullPointerException.class)
	public void testShouldThrowIfDimensionsAreNull() {
		new RoomImpl(null);
	}

}
