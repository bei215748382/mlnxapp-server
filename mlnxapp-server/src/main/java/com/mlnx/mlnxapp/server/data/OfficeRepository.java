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
import com.mlnx.mlnxapp.server.model.Office;
import com.mlnx.mlnxapp.server.model.Office_doctor;
/**
* office 仓库类
* Mon Oct 05 17:20:19 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class OfficeRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Office findById(Long id) {

		log.info(String.format("Find office for id %d.", id));
		return em.find(Office.class, id);
	}

	public List<Office> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Office> criteria = cb.createQuery(Office.class);
		criteria.from(Office.class);
		log.info("Find all offices.");
		return em.createQuery(criteria).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Doctor> findAllDoctors(String name) {
		Query query = em.createNativeQuery(
				"select * from office where name = '" + name + "'",
				Office.class);
		List<Office> result = query.getResultList();
		Iterator<Office> iterator = result.iterator();
		int office_id = 0;
		while (iterator.hasNext()) {
			Office h =  iterator.next();
			office_id = h.getId();
		}
		Query query1 = em.createNativeQuery(
				"select * from office_doctor where office_id = "
						+ office_id, Office_doctor.class);
		List<Office_doctor> result1 = query1.getResultList();
		Iterator<Office_doctor> iterator1 = result1.iterator();
		if(!iterator1.hasNext()){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		while (iterator1.hasNext()) {
			Office_doctor hd = iterator1.next();
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

