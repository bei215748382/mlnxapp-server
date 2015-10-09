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
import com.mlnx.mlnxapp.server.model.Doctor_groupa;
import com.mlnx.mlnxapp.server.model.Groupa;
/**
* groupa 仓库类
* Mon Oct 05 17:20:19 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class GroupaRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Groupa findById(Long id) {

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

	@SuppressWarnings("unchecked")
	public List<Doctor> findAllDoctors(String name) {
		Query query = em.createNativeQuery(
				"select * from groupa where name = '" + name + "'",
				Groupa.class);
		List<Groupa> result = query.getResultList();
		Iterator<Groupa> iterator = result.iterator();
		int group_id = 0;
		while (iterator.hasNext()) {
			Groupa h =  iterator.next();
			group_id = h.getId();
		}
		System.out.println(group_id);
		Query query1 = em.createNativeQuery(
				"select * from doctor_groupa where group_id = "
						+ group_id, Doctor_groupa.class);
		List<Doctor_groupa> result1 = query1.getResultList();
		Iterator<Doctor_groupa> iterator1 = result1.iterator();
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		while (iterator1.hasNext()) {
			Doctor_groupa hd = iterator1.next();
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

