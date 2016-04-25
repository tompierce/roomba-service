package com.tompierce.roomba;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoomDimensions;

public class RoombaSimulationServiceTest {

	@Test
	public void testShouldReturnCorrectResponseForSimulation() {

		RoombaDAO mockDao = mock(RoombaDAO.class);
		when(mockDao.recordSimulationResults(Mockito.any())).thenReturn((long) 25);

		List<RoomCoordinates> dirtPatches = new ArrayList<RoomCoordinates>();
		dirtPatches.add(new RoomCoordinates(1, 0));
		dirtPatches.add(new RoomCoordinates(2, 2));
		dirtPatches.add(new RoomCoordinates(2, 3));

		RoombaService service = new RoombaServiceImpl(mockDao);

		RoombaServiceRequest request = new RoombaServiceRequestImpl("NNESEESWNWW",
				new RoomDimensions(5, 5), new RoomCoordinates(1, 2), dirtPatches);

		RoombaServiceResponse response = service.doSimulation(request);

		assertNotNull(response);
		assertEquals(new RoomCoordinates(1, 3), response.getCoords());
		assertEquals(1, response.getPatches());
		assertEquals(25, response.getSimId());
	}

	@Test
	public void testShouldReturnPreviousSimulation() {

		RoombaDAO mockDao = mock(RoombaDAO.class);
		RoombaServiceResponse mockResponse = mock(RoombaServiceResponse.class);		
		when(mockDao.retrieveSimulationResult((long) 25)).thenReturn(mockResponse);
		RoombaService service = new RoombaServiceImpl(mockDao);

		RoombaServiceResponse response = service.retrieveSimulation("25");
		
		assertNotNull(response);
	}

	
}
