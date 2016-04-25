package com.tompierce.roomba;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoomDimensions;

public class RoombaServiceRequestDeserializerTest {

	private Gson gson;

	@Before
	public void setup() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(RoombaServiceRequestImpl.class, new RoombaServiceRequestDeserializer());
		gson = gsonBuilder.create();
	}
	
	@Test
	public void testShouldDeserializeValidJSON() {
		
		final String json = "{"
				+ "\"roomSize\" : [5, 5],"
				+ "\"coords\" : [1, 2],"
				+ "\"patches\" : [[1, 0], [2, 2], [2, 3]],"
				+ "\"instructions\" : \"NNESEESWNWW\""
				+ "}";
		
		RoombaServiceRequest request = gson.fromJson(json, RoombaServiceRequestImpl.class);
		assertEquals(3, request.getDirtPatches().size());
		assertEquals("NNESEESWNWW", request.getInstructions());
		assertEquals(new RoomDimensions(5, 5), request.getRoomSize());
		assertEquals(new RoomCoordinates(1,2), request.getStartingPosition());
	}

}
