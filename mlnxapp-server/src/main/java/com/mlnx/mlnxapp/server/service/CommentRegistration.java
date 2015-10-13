package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Comment;
/**
* comment 服务类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class CommentRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Comment> commentEventSrc;

	public Response register(Comment comment) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering comment for id:"+ comment.getId());
		em.persist(comment);
		commentEventSrc.fire(comment);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete comment for id: "+ id);
		Comment comment = em.find(Comment.class,id);
		em.remove(comment);
		return builder.build();
	}
}

