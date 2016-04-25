package com.tompierce.roomba;

import com.tompierce.roomba.model.Room;
import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoomImpl;
import com.tompierce.roomba.model.Roomba;
import com.tompierce.roomba.model.RoombaImpl;
import com.tompierce.roomba.model.RoombaSimulation;
import com.tompierce.roomba.model.RoombaSimulationImpl;

public class RoombaServiceImpl implements RoombaService {

	private final RoombaDAO dao;
	
	public RoombaServiceImpl(final RoombaDAO dao) {
		this.dao = dao;
	}

	@Override
	public RoombaServiceResponse doSimulation(final RoombaServiceRequest request) {

		Room room = new RoomImpl(request.getRoomSize());		
		request.getDirtPatches().forEach((coord) -> {
			room.addDirtPatch(coord);
		});

		Roomba roomba = new RoombaImpl(request.getStartingPosition(), request.getInstructions());

		RoombaSimulation sim = new RoombaSimulationImpl(room, roomba);
		sim.run();
		
		RoomCoordinates finalCoords = sim.getFinalCoordinates();
		int numCleanPatches = sim.getCleanedPatches().size();
		
		long simId = dao.recordSimulationResults(sim);
		
		return new RoombaServiceResponseImpl(simId, finalCoords, numCleanPatches);
	}

	@Override
	public RoombaServiceResponse retrieveSimulation(final String simId) {
		return dao.retrieveSimulationResult(Long.parseLong(simId));
	}

}
