package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Report;
/**
* report 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class ReportRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Report findById(int id) {

		log.info(String.format("Find report for id %d.", id));
		return em.find(Report.class, id);
	}

	public List<Report> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Report> criteria = cb.createQuery(Report.class);
		criteria.from(Report.class);
		log.info("Find all reports.");
		return em.createQuery(criteria).getResultList();
	}
}

