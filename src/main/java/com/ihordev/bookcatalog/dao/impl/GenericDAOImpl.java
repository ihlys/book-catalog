package com.ihordev.bookcatalog.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ihordev.bookcatalog.dao.GenericDAO;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class GenericDAOImpl<E, ID extends Serializable> implements GenericDAO<E, ID>
{

    private Class<E> entityClass;
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }

    public GenericDAOImpl(Class<E> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(E entity)
    {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(E entity)
    {
        getCurrentSession().update(entity);
    }

    @Override
    public void delete(E entity)
    {
        getCurrentSession().remove(entity);
    }

    @Override
    public E findById(ID id)
    {
        return getCurrentSession().find(entityClass, id);
    }

    @Override
    public E findReferenceById(ID id)
    {
        return getCurrentSession().load(entityClass, id);
    }

    @Override
    public List<E> findAll()
    {
        CriteriaQuery<E> q = getCurrentSession().getCriteriaBuilder().createQuery(entityClass);
        q.select(q.from(entityClass));
        return getCurrentSession().createQuery(q).getResultList();
    }

}
