package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Office;
/**
* office 服务类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class OfficeRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Office> officeEventSrc;

	public Response register(Office office) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering office for id:"+ office.getId());
		em.persist(office);
		officeEventSrc.fire(office);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete office for id: "+ id);
		Office office = em.find(Office.class,id);
		em.remove(office);
		return builder.build();
	}
}

