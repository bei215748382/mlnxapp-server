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
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mlnx.mlnxapp.server.model.Doctor;
import com.mlnx.mlnxapp.server.model.Office;
import com.mlnx.mlnxapp.server.data.OfficeRepository;
import com.mlnx.mlnxapp.server.service.OfficeRegistration;
/**
* office rest类
* Thu Oct 08 09:13:19 CST 2015 GenEntityMysql工具类生成
*/ 
@Path("/offices")
@RequestScoped
public class OfficeRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private OfficeRepository repository;

	@Inject
	private OfficeRegistration registration;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Office> findAll() {

		List<Office> offices = repository.findAll();
		if (offices == null || offices.size() == 0) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return offices;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Office office) {

		Response.ResponseBuilder builder = null;
		try {
			validate(office);
			registration.register(office);
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
	@Path("/all/doctors")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Doctor> findAllDoctors(String name) {

		List<Doctor> doctors = repository.findAllDoctors(name);
		if (doctors == null || doctors.size() == 0) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return doctors;
	}

	private void validate(Office office) throws ConstraintViolationException, ValidationException {

		Set<ConstraintViolation<Office>> violations = validator.validate(office);
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