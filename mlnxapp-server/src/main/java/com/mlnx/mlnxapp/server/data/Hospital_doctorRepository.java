package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Hospital_doctor;
/**
* hospital_doctor 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class Hospital_doctorRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Hospital_doctor findById(int id) {

		log.info(String.format("Find hospital_doctor for id %d.", id));
		return em.find(Hospital_doctor.class, id);
	}

	public List<Hospital_doctor> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Hospital_doctor> criteria = cb.createQuery(Hospital_doctor.class);
		criteria.from(Hospital_doctor.class);
		log.info("Find all hospital_doctors.");
		return em.createQuery(criteria).getResultList();
	}
}

