package com.mlnx.mlnxapp.server.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Doctor_groupa;
import com.mlnx.mlnxapp.server.data.Doctor_groupaRepository;
import com.mlnx.mlnxapp.server.service.Doctor_groupaRegistration;
/**
* doctor_groupa rest类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@Path("/doctor_groupas")
@RequestScoped
public class Doctor_groupaRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private Doctor_groupaRepository repository;

	@Inject
	private Doctor_groupaRegistration registration;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Doctor_groupa> findAll() {

		List<Doctor_groupa> doctor_groupas = repository.findAll();
		if (doctor_groupas == null || doctor_groupas.size() == 0) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return doctor_groupas;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Doctor_groupa doctor_groupa) {

		Response.ResponseBuilder builder = null;
		try {
			validate(doctor_groupa);
			registration.register(doctor_groupa);
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (Exception e) {
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}
		return builder.build();
	}

	@POST
	@Path("/delete")
	public Response delete(int id ){

		Response.ResponseBuilder builder = null;
		try {
			registration.delete(id);
			builder = Response.ok();
		} catch (Exception e) {
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.ok();
			builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
		}
		return builder.build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Doctor_groupa findById(@PathParam("id") int id) {

		Doctor_groupa doctor_groupa = repository.findById(id);
		if (doctor_groupa == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return doctor_groupa;
	}

	private void validate(Doctor_groupa doctor_groupa) throws ConstraintViolationException, ValidationException {

		Set<ConstraintViolation<Doctor_groupa>> violations = validator.validate(doctor_groupa);
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
		}
	}

	private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {

		log.fine("Validation completed. violations found: " + violations.size());
		Map<String, String> responseObj = new HashMap<String, String>();
		for (ConstraintViolation<?> violation : violations) {
			responseObj.put(violation.getPropertyPath().toString(),violation.getMessage());
		}
		return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
	}
}
