package com.mlnx.mlnxapp.server.service;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.core.Response;
import com.mlnx.mlnxapp.server.model.Product;
/**
* product 服务类
* Sat Oct 10 15:06:05 CST 2015 GenEntityMysql工具类生成
*/ 
@SuppressWarnings("serial")
@Stateless
public class ProductRegistration implements Serializable {

	@Inject
	private Logger log;

	@Inject
	private EntityManager em;

	@Inject
	private Event<Product> productEventSrc;

	public Response register(Product product) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Registering product for id:"+ product.getId());
		em.persist(product);
		productEventSrc.fire(product);
		return builder.build();
	}

	public Response delete(int id) throws Exception {

		Response.ResponseBuilder builder = Response.ok();
		log.info("Delete product for id: "+ id);
		Product product = em.find(Product.class,id);
		em.remove(product);
		return builder.build();
	}
}

