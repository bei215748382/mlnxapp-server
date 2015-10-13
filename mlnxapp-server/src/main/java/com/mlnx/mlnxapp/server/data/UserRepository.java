package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.User;
/**
* user 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class UserRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public User findById(int id) {

		log.info(String.format("Find user for id %d.", id));
		return em.find(User.class, id);
	}

	public List<User> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<User> criteria = cb.createQuery(User.class);
		criteria.from(User.class);
		log.info("Find all users.");
		return em.createQuery(criteria).getResultList();
	}
}

