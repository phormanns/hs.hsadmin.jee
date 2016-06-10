package de.hsadmin.module.util;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.ReflectionUtil;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.property.Property;
import de.hsadmin.module.property.mapping.Mapping;

public class QueryBuilder<BO> {

	final private EntityManager em;
	final private Class<BO> clasz;
	
	private QueryBuilder(final EntityManager em, final Class<BO> clasz) {
		this.em = em;
		this.clasz = clasz;
	}
	
	public static <BO> QueryBuilder<BO> newBuilder(final EntityManager em, final Class<BO> clasz) {
		return new QueryBuilder<BO>(em, clasz);
	}
	
	public List<BO> getResultList(final ValueObject criteria)
			throws UserException, TechnicalException {
		final CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		final CriteriaQuery<BO> criteriaQuery = criteriaBuilder.createQuery(clasz);
		final Root<BO> root = criteriaQuery.from(clasz);
		final List<Predicate> predicates = new ArrayList<>();
		for (Property<?> p : criteria.properties()) {
			if (!p.valueIsNullOrEmpty()) {
				Mapping mappingAnnotation = (Mapping) ReflectionUtil.getAnnotation(criteria.getClass(), p.getName(), Mapping.class);
				final String mapping = mappingAnnotation != null ? mappingAnnotation.boMappingPath() : p.getName();
				int pos = 0;
				Join<Object, Object> join = null;
				while (mapping.substring(pos).contains(".")) {
					join = join == null ?
							root.join(mapping.substring(0, mapping.indexOf('.', pos))) 
								: join.join(mapping.substring(0, mapping.indexOf('.', pos)));
					pos = mapping.indexOf('.') + 1;
				}
				final Path<Object> path = join == null ? 
						root.get(mapping.substring(pos))
							: join.get(mapping.substring(pos));
				final ParameterExpression<?> parameter = criteriaBuilder.parameter(p.getValueType(), p.getName());
				predicates.add(criteriaBuilder.equal(path, parameter));
			}
		}
		if (predicates.size() > 0) {
			criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
		}
		final TypedQuery<BO> query = em.createQuery(criteriaQuery);
		for (Property<?> p : criteria.properties()) {
			if (!p.valueIsNullOrEmpty()) {
				query.setParameter(p.getName(), p.getValue());
			}
		}
		return query.getResultList();
	}
	
}
