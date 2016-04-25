package com.tompierce.roomba;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class RoombaServiceResponseSerializer implements JsonSerializer<RoombaServiceResponseImpl> {

	@Override
	public JsonElement serialize(RoombaServiceResponseImpl src, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject result = new JsonObject();
		result.addProperty("simId", src.getSimId());
		result.addProperty("patches", src.getPatches());
		JsonArray coordArray = new JsonArray();
		coordArray.add(new JsonPrimitive(src.getCoords().getX()));
		coordArray.add(new JsonPrimitive(src.getCoords().getY()));
		result.add("coords", coordArray);
		return result;
	}

}
