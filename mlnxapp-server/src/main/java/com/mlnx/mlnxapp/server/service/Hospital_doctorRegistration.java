package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Hospital_doctor;
/**
* hospital_doctor 服务类
* Fri Oct 09 15:18:37 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class Hospital_doctorRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Hospital_doctor> hospital_doctorEventSrc;

	public Response register(Hospital_doctor hospital_doctor) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering hospital_doctor for id:"+ hospital_doctor.getId());
		em.persist(hospital_doctor);
		hospital_doctorEventSrc.fire(hospital_doctor);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete hospital_doctor for id: "+ id);
		Hospital_doctor hospital_doctor = em.find(Hospital_doctor.class,id);
		em.remove(hospital_doctor);
		return builder.build();
	}
}

