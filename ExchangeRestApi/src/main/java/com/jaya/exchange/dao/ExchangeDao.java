package com.jaya.exchange.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jaya.exchange.entity.ExchangeOperation;

@Repository
public class ExchangeDao {

	@PersistenceContext
    protected EntityManager entityManager;
	
	public List<ExchangeOperation> findByUserId(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select e from ExchangeOperation e where e.userId = :userId");
		Query query = entityManager.createQuery(sb.toString());
		query.setParameter("userId", userId);
		return query.getResultList();
	}
	
	public ExchangeOperation insert(ExchangeOperation entity) {
		entityManager.persist(entity);
		entityManager.flush();
		return entity;
	}
}
