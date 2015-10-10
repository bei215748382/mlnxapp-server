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
import com.mlnx.mlnxapp.server.model.Article;
import com.mlnx.mlnxapp.server.data.ArticleRepository;
import com.mlnx.mlnxapp.server.service.ArticleRegistration;
/**
* article rest类
* Sat Oct 10 15:06:05 CST 2015 GenEntityMysql工具类生成
*/ 
@Path("/articles")
@RequestScoped
public class ArticleRestService {

	@Inject
	private Logger log;

	@Inject
	private Validator validator;

	@Inject
	private ArticleRepository repository;

	@Inject
	private ArticleRegistration registration;

	@GET
	@Path("/all")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Article> findAll() {

		List<Article> articles = repository.findAll();
		if (articles == null || articles.size() == 0) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return articles;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Article article) {

		Response.ResponseBuilder builder = null;
		try {
			validate(article);
			registration.register(article);
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

	private void validate(Article article) throws ConstraintViolationException, ValidationException {

		Set<ConstraintViolation<Article>> violations = validator.validate(article);
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