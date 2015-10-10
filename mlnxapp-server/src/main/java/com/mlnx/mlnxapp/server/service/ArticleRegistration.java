package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Article;
/**
* article 服务类
* Sat Oct 10 15:06:05 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class ArticleRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Article> articleEventSrc;

	public Response register(Article article) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering article for id:"+ article.getId());
		em.persist(article);
		articleEventSrc.fire(article);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete article for id: "+ id);
		Article article = em.find(Article.class,id);
		em.remove(article);
		return builder.build();
	}
}

