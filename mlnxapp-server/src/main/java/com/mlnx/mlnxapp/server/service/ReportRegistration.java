package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Report;
/**
* report 服务类
* Sat Oct 10 15:06:05 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class ReportRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Report> reportEventSrc;

	public Response register(Report report) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering report for id:"+ report.getId());
		em.persist(report);
		reportEventSrc.fire(report);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete report for id: "+ id);
		Report report = em.find(Report.class,id);
		em.remove(report);
		return builder.build();
	}
}

