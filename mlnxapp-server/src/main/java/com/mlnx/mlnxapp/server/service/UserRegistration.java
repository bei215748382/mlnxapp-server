package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.User;
/**
* user 服务类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class UserRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<User> userEventSrc;

	public Response register(User user) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering user for id:"+ user.getId());
		em.persist(user);
		userEventSrc.fire(user);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete user for id: "+ id);
		User user = em.find(User.class,id);
		em.remove(user);
		return builder.build();
	}
}

