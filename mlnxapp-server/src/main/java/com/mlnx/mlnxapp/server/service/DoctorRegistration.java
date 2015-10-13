package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Doctor;
/**
* doctor 服务类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class DoctorRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Doctor> doctorEventSrc;

	public Response register(Doctor doctor) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering doctor for id:"+ doctor.getId());
		em.persist(doctor);
		doctorEventSrc.fire(doctor);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete doctor for id: "+ id);
		Doctor doctor = em.find(Doctor.class,id);
		em.remove(doctor);
		return builder.build();
	}
}

