package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Article;
/**
* article 仓库类
* Sat Oct 10 15:06:05 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class ArticleRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Article findById(int id) {

		log.info(String.format("Find article for id %d.", id));
		return em.find(Article.class, id);
	}

	public List<Article> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Article> criteria = cb.createQuery(Article.class);
		criteria.from(Article.class);
		log.info("Find all articles.");
		return em.createQuery(criteria).getResultList();
	}
}

