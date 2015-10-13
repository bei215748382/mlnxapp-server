package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Groupa;
/**
* groupa 服务类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class GroupaRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Groupa> groupaEventSrc;

	public Response register(Groupa groupa) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering groupa for id:"+ groupa.getId());
		em.persist(groupa);
		groupaEventSrc.fire(groupa);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete groupa for id: "+ id);
		Groupa groupa = em.find(Groupa.class,id);
		em.remove(groupa);
		return builder.build();
	}
}

