package com.tompierce.roomba;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoomDimensions;

public class RoombaServiceRequestDeserializer
		implements JsonDeserializer<RoombaServiceRequestImpl> {

	@Override
	public RoombaServiceRequestImpl deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {

		JsonObject jsonObject = json.getAsJsonObject();
		
		JsonElement jsonInstructions = jsonObject.get("instructions");
	    String instructions = jsonInstructions.getAsString();

	    int sizeX = jsonObject.get("roomSize").getAsJsonArray().get(0).getAsInt();
	    int sizeY = jsonObject.get("roomSize").getAsJsonArray().get(1).getAsInt();
	    RoomDimensions roomSize = new RoomDimensions(sizeX, sizeY);
	    
	    int startX = jsonObject.get("coords").getAsJsonArray().get(0).getAsInt();
	    int startY = jsonObject.get("coords").getAsJsonArray().get(1).getAsInt();
	    RoomCoordinates startingPosition = new RoomCoordinates(startX, startY);
	    
	    JsonArray patches = jsonObject.get("patches").getAsJsonArray();
	    List<RoomCoordinates> dirtPatches = new ArrayList<RoomCoordinates>();
	    patches.forEach(jsonElement -> {
		    int x = jsonElement.getAsJsonArray().get(0).getAsInt();
		    int y = jsonElement.getAsJsonArray().get(1).getAsInt();
	    	dirtPatches.add(new RoomCoordinates(x, y));
	    });
	    
		return new RoombaServiceRequestImpl(instructions, roomSize, startingPosition, dirtPatches);
	}

}
