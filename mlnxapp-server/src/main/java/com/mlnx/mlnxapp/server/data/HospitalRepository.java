package com.mlnx.mlnxapp.server.data;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import com.mlnx.mlnxapp.server.model.Doctor;
import com.mlnx.mlnxapp.server.model.Hospital;
import com.mlnx.mlnxapp.server.model.Hospital_doctor;

/**
 * hospital 仓库类 Mon Oct 05 17:20:19 CST 2015 GenEntityMysql工具类生成
 */
@ApplicationScoped
public class HospitalRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Hospital findById(Long id) {

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

	/**
	 * 查找医院下所有医生
	 * 
	 * @param name
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Doctor> findAllDoctors(String name) {
		Query query = em.createNativeQuery(
				"select * from hospital where name = '" + name + "'",
				Hospital.class);
		List<Hospital> result = query.getResultList();
		Iterator<Hospital> iterator = result.iterator();
		int hospital_id = 0;
		while (iterator.hasNext()) {
			Hospital h =  iterator.next();
			hospital_id = h.getId();
		}
		System.out.println(hospital_id);
		Query query1 = em.createNativeQuery(
				"select * from hospital_doctor where hospital_id = "
						+ hospital_id, Hospital_doctor.class);
		List<Hospital_doctor> result1 = query1.getResultList();
		Iterator<Hospital_doctor> iterator1 = result1.iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		while (iterator1.hasNext()) {
			Hospital_doctor hd = iterator1.next();
			sb.append(hd.getDoctor_id());
			if (iterator1.hasNext()) {
				sb.append(",");
			}
		}
		sb.append(")");
		Query query2 = em.createNativeQuery("select * from doctor where id in "
				+ sb.toString(), Doctor.class);
		return query2.getResultList();
	}
}
