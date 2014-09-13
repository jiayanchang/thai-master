/*
 * Created on 2006-9-8
 * 
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.thai.db.dao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.magic.thai.util.PaginationSupport;

public class HibernateCommonDAO<T> extends HibernateDaoSupport {
    private static final Logger logger = Logger.getLogger(HibernateCommonDAO.class);

    private boolean cacheQueries = false;

    private String queryCacheRegion;

    private Class<?> entityClass = java.io.Serializable.class;

    protected void initDao() throws Exception {
        super.initDao();
        getHibernateTemplate().setCacheQueries(cacheQueries);
        if (queryCacheRegion != null) {
            getHibernateTemplate().setQueryCacheRegion(queryCacheRegion);
            getHibernateTemplate().setAllowCreate(false);
        }
    }

    public void setCacheQueries(boolean cacheQueries) {
        this.cacheQueries = cacheQueries;
    }

    public void setQueryCacheRegion(String queryCacheRegion) {
        this.queryCacheRegion = queryCacheRegion;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    public Serializable create(final T entity) {
        return getHibernateTemplate().save(entity);
    }

    public void update(final T entity) {
        getHibernateTemplate().update(entity);
    }

    public void delete(final T entity) {
        getHibernateTemplate().delete(entity);
    }

    // helper methods for query
    public T loadById(final Serializable id) {
        return (T) getHibernateTemplate().get(this.entityClass, id);
    }


    public List<T> find(final String query) {
        return getHibernateTemplate().find(query);
    }

    public List<T> find(final String queryString, Object... values) {
        return getHibernateTemplate().find(queryString, values);
    }

    public PaginationSupport find(final Criterion[] criterions, int page, int rowPerPage) {
        return find(this.entityClass, criterions, null, page, rowPerPage);
    }

    public PaginationSupport find(String hql, int page, int rowPerPage) {
        return find(getSession().createQuery(hql), page, rowPerPage);
    }
    
    public PaginationSupport find(Query query, int page, int rowPerPage) {
        if (page < 1) {
            page = 1;
        }
        return paginationFindByNativeSql(query, page, rowPerPage);
    }
    
    public PaginationSupport find(final Criterion[] criterions, final Order[] orders, int page, int rowPerPage) {
        if (page < 1) {
            page = 1;
        }
        return find(this.entityClass, criterions, orders, page, rowPerPage);
    }

    public PaginationSupport find(final Criterion[] criterions, final Order[] orders, final String joinTarget,
            final ArrayList joinQueryList, int page, int rowPerPage) {
        if (page < 1) {
            page = 1;
        }
        return find(this.entityClass, criterions, orders, joinTarget, joinQueryList, page, rowPerPage);
    }

    public List find(final Criterion[] criterions, final Order[] orders) {
        final Class<?> entity = this.entityClass;
        return (List<?>) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(entity);
                if (criterions != null) {
                    for (Criterion criterion : criterions) {
                        criteria.add(criterion);
                    }
                }
                if (orders != null) {
                    for (Order order : orders) {
                        criteria.addOrder(order);
                    }
                }
                criteria.setProjection(null);
                return criteria.list();
            }
        });
    }

    public List find(final List<Criterion> temp) {
        Criterion[] criterions = new Criterion[temp.size()];
        temp.toArray(criterions);
        return this.find(criterions, null);
    }

    public List find(final List<Criterion> temp, final Order[] orders) {
        Criterion[] criterions = new Criterion[temp.size()];
        temp.toArray(criterions);
        return this.find(criterions, orders);
    }

    public PaginationSupport find(final List<Criterion> temp, int page, int rowPerPage) {
        Criterion[] criterions = new Criterion[temp.size()];
        temp.toArray(criterions);
        return find(this.entityClass, criterions, null, page, rowPerPage);
    }

    public PaginationSupport find(final List<Criterion> temp, final Order[] orders, int page, int rowPerPage) {
        Criterion[] criterions = new Criterion[temp.size()];
        temp.toArray(criterions);

        if (page < 1) {
            page = 1;
        }
        return find(this.entityClass, criterions, orders, page, rowPerPage);
    }

    
    protected List<T> find(final org.hibernate.Criteria criteria) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {

                criteria.setProjection(null);
//                criteria.setFirstResult(ps.getStartIndex()).setMaxResults(ps.getCountOnEachPage());
                criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
                // ִ�в�ѯ���
//                ps.setItems(criteria.list());
                List list = criteria.list();
                return list;
            }
        });
    }

    private PaginationSupport find(final Class<?> entity, final Criterion[] criterions, final Order[] orders,
            final int page, final int rowPerPage) {
        return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(entity);
                if (criterions != null) {
                    for (Criterion criterion : criterions) {
                        criteria.add(criterion);
                    }
                }
                PaginationSupport ps = new PaginationSupport(page, rowPerPage);
                ps.setTotalCount(((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
                if (orders != null) {
                    for (Order order : orders) {
                        criteria.addOrder(order);
                    }
                }
                criteria.setProjection(null);
                criteria.setFirstResult(ps.getStartIndex()).setMaxResults(ps.getCountOnEachPage());

                ps.setItems(criteria.list());
                return ps;
            }
        });
    }

    private PaginationSupport find(final Class<?> entity, final Criterion[] criterions, final Order[] orders,
            final String joinTarget, final ArrayList joinQueryList, final int page, final int rowPerPage) {
        return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Criteria criteria = session.createCriteria(entity);
                if (criterions != null) {
                    for (Criterion criterion : criterions) {
                        criteria.add(criterion);
                    }
                    Criterion[] joinQuerycriterions = new Criterion[joinQueryList.size()];
                    joinQueryList.toArray(joinQuerycriterions);
                    joinQuerycriterions = joinQuerycriterions;
                    for (Criterion joinCriterion : joinQuerycriterions) {
                        criteria.createCriteria(joinTarget).add(joinCriterion);
                    }
                }
                PaginationSupport ps = new PaginationSupport(page, rowPerPage);
                ps.setTotalCount(((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
                if (orders != null) {
                    for (Order order : orders) {
                        criteria.addOrder(order);
                    }
                }
                criteria.setProjection(null);
                criteria.setFirstResult(ps.getStartIndex()).setMaxResults(ps.getCountOnEachPage());
                List result = new ArrayList();
                for (Object obj : criteria.list()) {
                    Object[] tempArray = (Object[]) obj;
                    for (Object targetClass : tempArray) {
                        if (targetClass.getClass().isAssignableFrom(entity)) {
                            result.add(targetClass);
                        }
                    }
                }
                ps.setItems(result);
                return ps;
            }
        });
    }

    public Object uniqueResult(final String query, final Object[] params) {
        return super.getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query q = session.createQuery(query);
                if (params != null) {
                    for (int i = 0; i < params.length; i++) {
                        q.setParameter(i, params[i]);
                    }
                }
                q.setFirstResult(0);
                q.setMaxResults(1);
                return q.uniqueResult();
            }
        });
    }
    
    private PaginationSupport paginationFindByNativeSql(final Query query, final int page, final int rowPerPage) {
        return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query queryObject = query;
                PaginationSupport ps = new PaginationSupport(page, rowPerPage);
                ps.setTotalCount(queryObject.list().size());
                queryObject.setFirstResult(ps.getStartIndex());
                queryObject.setMaxResults(ps.getCountOnEachPage());
                ps.setItems(queryObject.list());
                return ps;
            }
        });
    }

}
