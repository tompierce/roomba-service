package com.tompierce.roomba;

import com.tompierce.roomba.model.RoombaSimulation;

public interface RoombaDAO {

	long recordSimulationResults(final RoombaSimulation sim);

	RoombaServiceResponse retrieveSimulationResult(final long l);

}
