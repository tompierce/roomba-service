package com.tompierce.roomba;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tompierce.roomba.model.RoomCoordinates;

public class RoombaServiceResponseSerializerTest {

	private Gson gson;

	@Before
	public void setup() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(RoombaServiceResponseImpl.class, new RoombaServiceResponseSerializer());
		gson = gsonBuilder.create();
	}

	@Test
	public void testShouldSerializeResponseCorrectly() {
		RoombaServiceResponseImpl response = new RoombaServiceResponseImpl(25, new RoomCoordinates(7, 6), 4);
		final String json = gson.toJson(response);
		final String expected = "{\"simId\":25,\"patches\":4,\"coords\":[7,6]}";
		assertEquals(expected, json);
	}

}
