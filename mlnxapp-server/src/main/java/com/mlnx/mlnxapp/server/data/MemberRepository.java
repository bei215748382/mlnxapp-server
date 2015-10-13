package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Member;
/**
* member 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class MemberRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Member findById(int id) {

		log.info(String.format("Find member for id %d.", id));
		return em.find(Member.class, id);
	}

	public List<Member> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		criteria.from(Member.class);
		log.info("Find all members.");
		return em.createQuery(criteria).getResultList();
	}
}

