package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Groupa;
/**
* groupa 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class GroupaRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Groupa findById(int id) {

		log.info(String.format("Find groupa for id %d.", id));
		return em.find(Groupa.class, id);
	}

	public List<Groupa> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Groupa> criteria = cb.createQuery(Groupa.class);
		criteria.from(Groupa.class);
		log.info("Find all groupas.");
		return em.createQuery(criteria).getResultList();
	}
}

