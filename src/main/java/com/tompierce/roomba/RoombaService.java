package com.tompierce.roomba;

public interface RoombaService {

	RoombaServiceResponse doSimulation(final RoombaServiceRequest request);

	RoombaServiceResponse retrieveSimulation(final String simId);

}
