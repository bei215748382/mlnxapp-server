package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Doctor_groupa;
/**
* doctor_groupa 服务类
* Wed Sep 30 14:35:32 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class Doctor_groupaRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Doctor_groupa> doctor_groupaEventSrc;

	public Response register(Doctor_groupa doctor_groupa) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering "+ doctor_groupa.getId());
		em.persist(doctor_groupa);
		doctor_groupaEventSrc.fire(doctor_groupa);
		return builder.build();
	}
	
	
}

