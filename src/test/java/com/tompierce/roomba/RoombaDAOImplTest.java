package com.tompierce.roomba;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.store.fs.FileUtils;
import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.tompierce.roomba.RoombaDAOImpl.SimulationNotFoundException;
import com.tompierce.roomba.model.Room;
import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoomDimensions;
import com.tompierce.roomba.model.RoomImpl;
import com.tompierce.roomba.model.Roomba;
import com.tompierce.roomba.model.RoombaImpl;
import com.tompierce.roomba.model.RoombaSimulation;
import com.tompierce.roomba.model.RoombaSimulationImpl;
import com.zaxxer.hikari.HikariDataSource;

public class RoombaDAOImplTest {

	private RoombaDAO dao;

	@Before
	public void setup() throws SQLException, FileNotFoundException {
		final String jdbcStr = "jdbc:h2:./build/tmp/h2/roomba_service MODE=MySQL";
		
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(jdbcStr);
		dataSource.setUsername("roomba");
		dataSource.setPassword("password");

		dao = new RoombaDAOImpl(dataSource);
		Connection conn = dataSource.getConnection();
		RunScript.execute(conn, new FileReader("sql/01_create.sql"));
	}

	@After
	public void after() throws SQLException {
		FileUtils.deleteRecursive("./build/tmp/h2", false);
	}

	@Test
	public void testShouldRecordSimulationResultsAndAllowRetrieval() {
		Room room = new RoomImpl(new RoomDimensions(10, 10));
		Roomba roomba = new RoombaImpl(new RoomCoordinates(2, 2), "NSEW");
		RoombaSimulation sim = new RoombaSimulationImpl(room, roomba);
		sim.run();
		assertEquals(1, dao.recordSimulationResults(sim));
		assertEquals(2, dao.recordSimulationResults(sim));
		assertEquals(3, dao.recordSimulationResults(sim));

		RoombaServiceResponse response = dao.retrieveSimulationResult(2);
		assertEquals(new RoomCoordinates(2, 2), response.getCoords());
		assertEquals(0, response.getPatches());
		assertEquals(2, response.getSimId());
	}

	@Test(expected = SimulationNotFoundException.class)
	public void testShouldThrowNotFoundExceptionWhenSimulationDoesNotExist() {
		dao.retrieveSimulationResult(123);
	}

	@Test(expected = NullPointerException.class)
	public void testShouldThrowIfNullSimulation() {
		dao.recordSimulationResults(null);
	}

}
