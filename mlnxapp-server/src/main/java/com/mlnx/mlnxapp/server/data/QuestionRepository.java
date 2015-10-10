package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Question;
/**
* question 仓库类
* Sat Oct 10 15:06:05 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class QuestionRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Question findById(int id) {

		log.info(String.format("Find question for id %d.", id));
		return em.find(Question.class, id);
	}

	public List<Question> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Question> criteria = cb.createQuery(Question.class);
		criteria.from(Question.class);
		log.info("Find all questions.");
		return em.createQuery(criteria).getResultList();
	}
}

