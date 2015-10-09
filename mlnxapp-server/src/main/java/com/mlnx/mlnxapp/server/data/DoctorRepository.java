package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Doctor;
/**
* doctor 仓库类
* Mon Oct 05 17:20:19 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class DoctorRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Doctor findById(int id) {

		log.info(String.format("Find doctor for id %d.", id));
		return em.find(Doctor.class, id);
	}

	public List<Doctor> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteria = cb.createQuery(Doctor.class);
		criteria.from(Doctor.class);
		log.info("Find all doctors.");
		return em.createQuery(criteria).getResultList();
	}
}

