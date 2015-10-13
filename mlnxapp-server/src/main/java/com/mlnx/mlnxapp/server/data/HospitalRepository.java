package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Hospital;
/**
* hospital 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class HospitalRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Hospital findById(int id) {

		log.info(String.format("Find hospital for id %d.", id));
		return em.find(Hospital.class, id);
	}

	public List<Hospital> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Hospital> criteria = cb.createQuery(Hospital.class);
		criteria.from(Hospital.class);
		log.info("Find all hospitals.");
		return em.createQuery(criteria).getResultList();
	}
}

