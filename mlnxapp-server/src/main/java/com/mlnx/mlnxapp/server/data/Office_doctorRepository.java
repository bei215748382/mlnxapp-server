package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Office_doctor;
/**
* office_doctor 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class Office_doctorRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Office_doctor findById(int id) {

		log.info(String.format("Find office_doctor for id %d.", id));
		return em.find(Office_doctor.class, id);
	}

	public List<Office_doctor> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Office_doctor> criteria = cb.createQuery(Office_doctor.class);
		criteria.from(Office_doctor.class);
		log.info("Find all office_doctors.");
		return em.createQuery(criteria).getResultList();
	}
}

