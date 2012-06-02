package com.mesoft.nowshuttle.webapi.rest;

import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.mesoft.nowshuttle.webapi.db.DbEngine;
import com.mesoft.nowshuttle.webapi.mail.EmailEngine;
import com.mesoft.nowshuttle.webapi.om.Shuttle;
import com.mesoft.nowshuttle.webapi.om.TransferStatus;
import com.mesoft.nowshuttle.webapi.om.TransferStatusRequest;
import com.mesoft.nowshuttle.webapi.om.User;

@Path("/operations")
public class WebApiOperations {

	@GET
	@Path("/getShuttles")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Shuttle> getShuttles() {

		DbEngine db = new DbEngine();

		return db.getShuttles();

	}

	@POST
	@Path("/updateUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(User user) {

		DbEngine db = new DbEngine();
		db.updateUser(user.getUserId(), user.getDisplayName());
		return Response.status(Status.ACCEPTED).build();
	}

	@POST
	@Path("/checkUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkUser(User user) {

		DbEngine db = new DbEngine();

		User u = db.checkUser(user.getEmail(), user.getPassword());
		if (u == null) {
			return Response.status(Status.UNAUTHORIZED).build();
		} else {
			return Response.status(Status.ACCEPTED).build();
		}

	}

	@GET
	@Path("/getTransferList")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TransferStatus> getTransferList(
			@QueryParam("shuttleId") String shuttleId,
			@QueryParam("date") String date) {
		DbEngine db = new DbEngine();
		return db.getTrasferList(shuttleId, date);
	}

	@GET
	@Path("/getUserByEmail")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserByEmail(@QueryParam("email") String email) {
		DbEngine db = new DbEngine();
		return db.findUserByUsername(email);
	}

	@POST
	@Path("/informTransferStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response informTransferStatus(TransferStatusRequest request) {

		DbEngine db = new DbEngine();
		try {

			TransferStatus ts = db.getTrasferStatus(request.getShuttleId(),
					request.getDate(), request.getUserId());
			if (ts == null) {
				db.informTransferStatus(request);
			} else {
				db.updateTransferStatus(request.getState(), ts.getId());
			}
			return Response.status(Status.ACCEPTED).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

	}

	@GET
	@Path("/getUserById")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@QueryParam("userId") String userId) {

		User user = new User();
		user.setUserId(Integer.valueOf(userId));
		user.setDisplayName("Mehmet");
		user.setEmail("mehmetersinbitirgen@gmail.com");
		user.setStatus("2");
		return user;
	}

	@POST
	@Path("/registerShuttle")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerShuttle(String userId, String shuttleId) {
		return Response.status(Status.ACCEPTED).build();

	}

	@POST
	@Path("/updateTransferState")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateTransferState(String userId, String shuttleId,
			String state, String day) {
		// TODO check in for shuttle list
		System.out.println(">>>UserId:" + userId + "shuttleId:" + shuttleId
				+ shuttleId + "state" + state + "Day:" + day);
		return Response.status(Status.ACCEPTED).build();

	}

	@POST
	@Path("/signup")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response signUpUser(User user) {
		Response r = Response.status(Status.ACCEPTED).build();

		DbEngine db = new DbEngine();

		User u = db.findUserByUsername(user.getEmail());

		Random rd = new Random();

		String newPass = String.valueOf(rd.nextInt(10000));

		try {

			if (u == null) {
				User u2 = new User();
				u2.setEmail(user.getEmail());
				u2.setPassword(newPass);
				u2.setStatus("1");
				u2.setDisplayName(user.getEmail());
				db.insertUser(u2);
				EmailEngine.sendMail(user.getEmail(), newPass);
			} else {
				r = Response.status(Status.PRECONDITION_FAILED).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
			r = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return r;
	}
}