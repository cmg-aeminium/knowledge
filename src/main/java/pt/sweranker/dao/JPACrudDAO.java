/*
 * Copyright (c) 2019  Fashion Concierge
 * All rights reserved.
 */
package pt.sweranker.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;

/**
 * @author Carlos Manuel
 */
public abstract class JPACrudDAO<T> implements CrudDAO<T> {

    private final Class<T> entityClass;

    public JPACrudDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext(unitName = "sweranker-data")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(T entity) {
        if (entity != null) {
            create(entity, false);
        }
    }

    @Override
    public void create(T entity, boolean flush) {
        if (entity != null) {
            getEntityManager().persist(entity);
            if (flush) {
                getEntityManager().flush();
            }
        }
    }

    @Override
    public void edit(T entity) {
        if (entity != null) {
            // If this happens to be an Injected entity, then saving requires an extra step. This is rare, but most User types are injected via authentication, so...
            if (entity instanceof TargetInstanceProxy<?>) {
                getEntityManager().merge(((TargetInstanceProxy<?>) entity).weld_getTargetInstance());
            } else {
                getEntityManager().merge(entity);
            }
        }
    }

    @Override
    public void remove(T entity) {
        if (entity != null) {
            getEntityManager().remove(getEntityManager().merge(entity));
        }
    }

    @Override
    public T findById(Long id) {
        if (id == null || id <= 0) {
            return null;
        }

        return getEntityManager().find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> cq = getEntityManager().getCriteriaBuilder().createQuery(entityClass);
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        Root<T> root = cq.from(entityClass);

        cq.select(cq.from(entityClass));
        cq.orderBy(cb.asc(root.get("id")));

        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public Long count() {
        CriteriaQuery<Long> cq = getEntityManager().getCriteriaBuilder().createQuery(Long.class);
        Root<T> rt = cq.from(entityClass);

        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        TypedQuery<Long> q = getEntityManager().createQuery(cq);

        return q.getSingleResult();
    }

}
