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
import com.mlnx.mlnxapp.server.model.Hospital_doctor;
import com.mlnx.mlnxapp.server.data.Hospital_doctorRepository;
import com.mlnx.mlnxapp.server.service.Hospital_doctorRegistration;
/**
* hospital_doctor rest类
* Thu Oct 08 09:13:19 CST 2015 GenEntityMysql工具类生成
*/ 
@Path("/hospital_doctors")
@RequestScoped
public class Hospital_doctorRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private Hospital_doctorRepository repository;

	@Inject
	private Hospital_doctorRegistration registration;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Hospital_doctor> findAll() {

		List<Hospital_doctor> hospital_doctors = repository.findAll();
		if (hospital_doctors == null || hospital_doctors.size() == 0) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return hospital_doctors;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Hospital_doctor hospital_doctor) {

		Response.ResponseBuilder builder = null;
		try {
			validate(hospital_doctor);
			registration.register(hospital_doctor);
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

	private void validate(Hospital_doctor hospital_doctor) throws ConstraintViolationException, ValidationException {

		Set<ConstraintViolation<Hospital_doctor>> violations = validator.validate(hospital_doctor);
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
