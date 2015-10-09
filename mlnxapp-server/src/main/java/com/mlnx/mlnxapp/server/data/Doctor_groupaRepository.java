package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Doctor_groupa;
/**
* doctor_groupa 仓库类
* Mon Oct 05 17:20:19 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class Doctor_groupaRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Doctor_groupa findById(Long id) {

		log.info(String.format("Find doctor_groupa for id %d.", id));
		return em.find(Doctor_groupa.class, id);
	}

	public List<Doctor_groupa> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Doctor_groupa> criteria = cb.createQuery(Doctor_groupa.class);
		criteria.from(Doctor_groupa.class);
		log.info("Find all doctor_groupas.");
		return em.createQuery(criteria).getResultList();
	}
}

