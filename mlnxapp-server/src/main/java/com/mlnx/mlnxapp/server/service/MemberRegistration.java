package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Member;
/**
* member 服务类
* Fri Oct 09 15:18:37 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class MemberRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Member> memberEventSrc;

	public Response register(Member member) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering member for id:"+ member.getId());
		em.persist(member);
		memberEventSrc.fire(member);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete member for id: "+ id);
		Member member = em.find(Member.class,id);
		em.remove(member);
		return builder.build();
	}
}

