package de.hsadmin.module.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserException;
import de.hsadmin.login.RequestContext;
import de.hsadmin.login.RequiredScope;
import de.hsadmin.login.Role;
import de.hsadmin.login.ScopePolicy;
import de.hsadmin.module.Module;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.property.Property;
import de.hsadmin.module.property.StringProperty;

public abstract class AbstractModule<VO extends ValueObject> implements Module<VO> {

	private final ValidationDelegate<VO> validationDelegate;

	public AbstractModule() {
		validationDelegate = new ValidationDelegate<>();
	}

	@Override
	public VO create(final RequestContext requestContext, final VO prototype)
			throws UserException, TechnicalException {
		checkScopePolicy(requestContext, "create");
		validationDelegate.checkPrototypeIsCreateable(prototype);
		return prototype;
	}

	@Override
	public List<VO> read(final RequestContext requestContext, final VO criteria)
			throws UserException, TechnicalException {
		checkScopePolicy(requestContext, "read", criteria);
		validationDelegate.checkCriteriaIsSearchable(criteria);
		final List<VO> readValueObjects = new ArrayList<>();
		return readValueObjects;
	}

	@Override
	public List<VO> update(final RequestContext requestContext, final VO criteria,
			final VO prototype) throws UserException, TechnicalException {
		checkScopePolicy(requestContext, "update", criteria);
		validationDelegate.checkCriteriaIsSearchable(criteria);
		validationDelegate.checkPrototypeIsUpdateable(prototype);
		final List<VO> valueObjectsForUpdate = read(requestContext, criteria);
		return valueObjectsForUpdate;
	}

	@Override
	public void delete(final RequestContext requestContext, final VO criteria)
			throws UserException, TechnicalException {
		checkScopePolicy(requestContext, "delete", criteria);
		validationDelegate.checkCriteriaIsSearchable(criteria);
	}

	protected void checkScopePolicy(final RequestContext requestContext, final String methodName,
			final VO criteria) throws TechnicalException, UserException {
		final String scopeAttribute = checkScopePolicy(requestContext, methodName);
		if (scopeAttribute != null && !scopeAttribute.isEmpty()) {
			final Property<?> property = criteria.get(scopeAttribute);
			if (property.valueIsNullOrEmpty() && property instanceof StringProperty) {
				((StringProperty) property).setValue(requestContext.getLoginUser());
			} else {
				if (!requestContext.getLoginUser().equals(property.getValue())) {
					throw new UserException(new UserError(UserError.MSG_MISSING_AUTHORIZATION, methodName));
				}
			}
		}
	}

	protected String checkScopePolicy(final RequestContext requestContext, final String methodName)
			throws TechnicalException, UserException {
		try {
			Method method = null;
			final Class<?> valueClass = buildVO().getClass();
			if ("update".equals(methodName)) {
				method = getClass().getMethod(methodName, RequestContext.class, valueClass, valueClass);
			} else {
				method = getClass().getMethod(methodName, RequestContext.class, valueClass);
			}
			if (method == null) {
				throw new UserException(new UserError(UserError.MSG_MISSING_AUTHORIZATION, methodName));
			}
			final RequiredScope annotation = method.getAnnotation(RequiredScope.class);
			if (annotation == null) {
				throw new UserException(new UserError(UserError.MSG_MISSING_AUTHORIZATION, methodName));
			}
			final ScopePolicy[] scopePolicies = annotation.value();
			for (ScopePolicy policy : scopePolicies) {
				if (policy.value() == requestContext.getLoginRole() || policy.value() == Role.ANY) {
					return policy.property();
				}
			}
			throw new UserException(new UserError(UserError.MSG_MISSING_AUTHORIZATION, methodName));
		} catch (NoSuchMethodException | SecurityException e) {
			throw new TechnicalException(e);
		}
	}

}
