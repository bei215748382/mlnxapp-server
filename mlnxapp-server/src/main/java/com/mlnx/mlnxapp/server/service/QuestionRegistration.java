package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Question;
/**
* question 服务类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class QuestionRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Question> questionEventSrc;

	public Response register(Question question) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering question for id:"+ question.getId());
		em.persist(question);
		questionEventSrc.fire(question);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete question for id: "+ id);
		Question question = em.find(Question.class,id);
		em.remove(question);
		return builder.build();
	}
}

