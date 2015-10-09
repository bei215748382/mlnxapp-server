package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Hospital;
/**
* hospital 服务类
* Fri Oct 09 15:18:37 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class HospitalRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Hospital> hospitalEventSrc;

	public Response register(Hospital hospital) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering hospital for id:"+ hospital.getId());
		em.persist(hospital);
		hospitalEventSrc.fire(hospital);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete hospital for id: "+ id);
		Hospital hospital = em.find(Hospital.class,id);
		em.remove(hospital);
		return builder.build();
	}
}

