package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Comment;
/**
* comment 仓库类
* Tue Oct 13 09:56:43 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class CommentRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Comment findById(int id) {

		log.info(String.format("Find comment for id %d.", id));
		return em.find(Comment.class, id);
	}

	public List<Comment> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Comment> criteria = cb.createQuery(Comment.class);
		criteria.from(Comment.class);
		log.info("Find all comments.");
		return em.createQuery(criteria).getResultList();
	}
}

