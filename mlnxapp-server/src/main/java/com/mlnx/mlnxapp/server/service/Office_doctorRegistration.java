package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Office_doctor;
/**
* office_doctor 服务类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class Office_doctorRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Office_doctor> office_doctorEventSrc;

	public Response register(Office_doctor office_doctor) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering office_doctor for id:"+ office_doctor.getId());
		em.persist(office_doctor);
		office_doctorEventSrc.fire(office_doctor);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete office_doctor for id: "+ id);
		Office_doctor office_doctor = em.find(Office_doctor.class,id);
		em.remove(office_doctor);
		return builder.build();
	}
}

