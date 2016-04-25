package com.tompierce.roomba;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.tompierce.roomba.model.RoomCoordinates;
import com.tompierce.roomba.model.RoombaSimulation;
import com.zaxxer.hikari.HikariDataSource;

public class RoombaDAOImpl implements RoombaDAO {

	private HikariDataSource datasource;

	public RoombaDAOImpl(final HikariDataSource datasource) throws SQLException {
		this.datasource = datasource;
	}

	@Override
	public long recordSimulationResults(RoombaSimulation sim) {
				
		final String sql = "INSERT INTO roomba_service.simulations"
				+ "(instructions, start_x, start_y, finish_x, finish_y, room_size_x, room_size_y, num_clean_patches, dirt_patches) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, sim.getInstructions());
			statement.setInt(2, sim.getStartingCoordinates().getX());
			statement.setInt(3, sim.getStartingCoordinates().getY());
			statement.setInt(4, sim.getFinalCoordinates().getX());
			statement.setInt(5, sim.getFinalCoordinates().getY());
			statement.setInt(6, sim.getRoomDimensions().getX());
			statement.setInt(7, sim.getRoomDimensions().getY());
			statement.setInt(8, sim.getCleanedPatches().size());
			statement.setString(9, sim.getDirtyPatches());
			statement.executeUpdate();

			ResultSet rs = statement.getGeneratedKeys();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			throw new RoombaDatabaseException(e);
		} finally {
			try {
				if (statement != null) statement.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new RoombaDatabaseException(e);
			}
		}
	}

	@Override
	public RoombaServiceResponse retrieveSimulationResult(final long simId) {

		final String sql = "SELECT finish_x, finish_y, num_clean_patches"
				+ " FROM roomba_service.simulations"
				+ " WHERE id=?";

		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = datasource.getConnection();
			statement = conn.prepareStatement(sql);
			statement.setLong(1, simId);
			ResultSet rs = statement.executeQuery();
			if (!rs.next()) {
				throw new SimulationNotFoundException();
			}
			return new RoombaServiceResponseImpl(simId, new RoomCoordinates(rs.getInt(1), rs.getInt(2)), rs.getInt(3));
		} catch (SQLException e) {
			throw new RoombaDatabaseException(e);
		} finally {
			try {
				if (statement != null) statement.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				throw new RoombaDatabaseException(e);
			}
		}
	}

	public class RoombaDatabaseException extends RuntimeException {

		private static final long serialVersionUID = -4550408993634909397L;

		public RoombaDatabaseException(Exception e) {
			super("Database error");
			e.printStackTrace();
		}

	}
	
	public class SimulationNotFoundException extends RuntimeException {

		private static final long serialVersionUID = 8028491194040972305L;

		public SimulationNotFoundException() {
			super("Not found.");
		}

	}
}
