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
import com.mlnx.mlnxapp.server.model.Member;
import com.mlnx.mlnxapp.server.data.MemberRepository;
import com.mlnx.mlnxapp.server.service.MemberRegistration;
/**
* member rest类
* Thu Oct 08 09:13:19 CST 2015 GenEntityMysql工具类生成
*/ 
@Path("/members")
@RequestScoped
public class MemberRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private MemberRepository repository;

	@Inject
	private MemberRegistration registration;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Member> findAll() {

		List<Member> members = repository.findAll();
		if (members == null || members.size() == 0) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return members;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Member member) {

		Response.ResponseBuilder builder = null;
		try {
			validate(member);
			registration.register(member);
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

	private void validate(Member member) throws ConstraintViolationException, ValidationException {

		Set<ConstraintViolation<Member>> violations = validator.validate(member);
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
