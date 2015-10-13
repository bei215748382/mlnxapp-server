package com.mlnx.mlnxapp.server.data;

import java.util.List;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import com.mlnx.mlnxapp.server.model.Product;
/**
* product 仓库类
* Tue Oct 13 09:56:44 CST 2015 GenEntityMysql工具类生成
*/ 
@ApplicationScoped
public class ProductRepository {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	public Product findById(int id) {

		log.info(String.format("Find product for id %d.", id));
		return em.find(Product.class, id);
	}

	public List<Product> findAll() {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Product> criteria = cb.createQuery(Product.class);
		criteria.from(Product.class);
		log.info("Find all products.");
		return em.createQuery(criteria).getResultList();
	}
}

