package com.tompierce.roomba;

import static spark.Spark.exception;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.port;

import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tompierce.roomba.RoombaDAOImpl.RoombaDatabaseException;
import com.tompierce.roomba.RoombaDAOImpl.SimulationNotFoundException;
import com.zaxxer.hikari.HikariDataSource;

public class ServiceRunner {

	public static void main(String[] args) throws SQLException {

		// database connection setup
		final String host = System.getenv("ROOMBA_DB_PORT_3306_TCP_ADDR");
		final String port = System.getenv("ROOMBA_DB_PORT_3306_TCP_PORT");

		final String jdbcStr = "jdbc:mysql://" + host + ":" + port + "/roomba_service";
		
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(jdbcStr);
		dataSource.setUsername("roomba");
		dataSource.setPassword("password");
		
		RoombaService service = new RoombaServiceImpl(new RoombaDAOImpl(dataSource));
		
		// request and response (de)serialization
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(RoombaServiceRequestImpl.class, new RoombaServiceRequestDeserializer());
		gsonBuilder.registerTypeAdapter(RoombaServiceResponseImpl.class, new RoombaServiceResponseSerializer());
		Gson gson = gsonBuilder.create();

		// set server port
		port(Integer.parseInt(System.getProperty("server.port", "8080")));

		// define routes
		post("/simulation", (req, res) -> {
			RoombaServiceRequest serviceRequest = gson.fromJson(req.body(), RoombaServiceRequestImpl.class);
			return service.doSimulation(serviceRequest);
		}, gson::toJson);

		get("/simulation/:simId", (req, res) -> {
			return service.retrieveSimulation(req.params("simId"));
		}, gson::toJson);

		// handle exceptions
		exception(RoombaDatabaseException.class, (e, request, response) -> {
			response.status(500);
			response.body(gson.toJson(new RoombaServiceErrorResponse(e.getMessage())));
		});
		
		exception(SimulationNotFoundException.class, (e, request, response) -> {
			response.status(404);
			response.body(gson.toJson(new RoombaServiceErrorResponse(e.getMessage())));
		});

		exception(Exception.class, (e, request, response) -> {
			response.status(400);
			response.body(gson.toJson(new RoombaServiceErrorResponse(e.getMessage())));
			e.printStackTrace();
		});

	}

}
