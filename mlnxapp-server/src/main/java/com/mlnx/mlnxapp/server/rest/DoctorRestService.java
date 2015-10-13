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
import com.mlnx.mlnxapp.server.model.Doctor;
import com.mlnx.mlnxapp.server.data.DoctorRepository;
import com.mlnx.mlnxapp.server.service.DoctorRegistration;
/**
* doctor rest类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@Path("/doctors")
@RequestScoped
public class DoctorRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private DoctorRepository repository;

	@Inject
	private DoctorRegistration registration;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Doctor> findAll() {

		List<Doctor> doctors = repository.findAll();
		if (doctors == null || doctors.size() == 0) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return doctors;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Doctor doctor) {

		Response.ResponseBuilder builder = null;
		try {
			validate(doctor);
			registration.register(doctor);
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
	public Doctor findById(@PathParam("id") int id) {

		Doctor doctor = repository.findById(id);
		if (doctor == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return doctor;
	}

	private void validate(Doctor doctor) throws ConstraintViolationException, ValidationException {

		Set<ConstraintViolation<Doctor>> violations = validator.validate(doctor);
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
