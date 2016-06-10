package de.hsadmin.xmlrpc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.ejb.EJBException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserErrorList;
import de.hsadmin.common.error.UserException;
import de.hsadmin.login.LoginServiceLocal;
import de.hsadmin.login.RequestContext;
import de.hsadmin.module.Module;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.property.Property;

public abstract class AbstractRemote<VO extends ValueObject> implements Remote {

	final private LoginServiceLocal login;
	final private Module<VO> module;
	
	@SuppressWarnings("unchecked")
	public AbstractRemote() {
		final Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.core.LocalInitialContextFactory");
        Context ctx = null;
        try {
 			ctx = new InitialContext(props);
			login = (LoginServiceLocal) ctx.lookup("LoginServiceLocal");
			module = (Module<VO>) ctx.lookup(getModuleLookup());
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} finally {
			if (ctx != null) {
				try { ctx.close(); } catch (NamingException e) { }
			}
		}
	}

	@Override
	public List<Map<String, Object>> search(final String runAsUser, final String ticket,
			final Map<String, Object> whereParams) throws RemoteException {
		try {
			final RequestContext requestContext = login.createContext(ticket, runAsUser);
			final VO queryValue = module.buildVO();
			mapXmlRpc2VO(whereParams, queryValue);
			final List<VO> list = module.read(requestContext, queryValue);
			final List<Map<String, Object>> resultList = new ArrayList<>();
			for (VO value : list) {
				final Map<String, Object> resultValue = new HashMap<>();
				mapVO2XmlRpc(value, resultValue);
				resultList.add(resultValue);
			}
			return resultList;
		} catch (UserException | TechnicalException | EJBException e) {
			if (e instanceof EJBException && e.getCause() != null) {
				analyzeEJBException(e.getCause());
			}
			throw new RemoteException(e);
		}
	}

	@Override
	public Map<String, Object> add(final String runAsUser, final String ticket,
			final Map<String, Object> setParams) throws RemoteException {
		try {
			final RequestContext requestContext = login.createContext(ticket, runAsUser);
			final VO setValue = module.buildVO();
			mapXmlRpc2VO(setParams, setValue);
			final VO newValue = module.create(requestContext, setValue);
			final Map<String, Object> resultValue = new HashMap<>();
			mapVO2XmlRpc(newValue, resultValue);
			return resultValue;
		} catch (UserException | TechnicalException | EJBException e) {
			if (e instanceof EJBException && e.getCause() != null) {
				throw analyzeEJBException(e.getCause());
			}
			throw new RemoteException(e);
		}
	}

	@Override
	public List<Map<String, Object>> update(final String runAsUser, final String ticket,
			final Map<String, Object> setParams, final Map<String, Object> whereParams)
			throws RemoteException {
		try {
			final RequestContext requestContext = login.createContext(ticket, runAsUser);
			final VO queryValue = module.buildVO();
			mapXmlRpc2VO(whereParams, queryValue);
			final VO setValue = module.buildVO();
			mapXmlRpc2VO(setParams, setValue);
			final List<VO> list = module.update(requestContext, queryValue, setValue);
			final List<Map<String, Object>> resultList = new ArrayList<>();
			for (VO value : list) {
				final Map<String, Object> resultValue = new HashMap<>();
				mapVO2XmlRpc(value, resultValue);
				resultList.add(resultValue);
			}
			return resultList;
		} catch (UserException | TechnicalException | EJBException e) {
			if (e instanceof EJBException && e.getCause() != null) {
				analyzeEJBException(e.getCause());
			}
			throw new RemoteException(e);
		}
	}

	@Override
	public void delete(final String runAsUser, final String ticket,
			final Map<String, Object> whereParams) throws RemoteException {
		try {
			final RequestContext requestContext = login.createContext(ticket, runAsUser);
			final VO queryValue = module.buildVO();
			mapXmlRpc2VO(whereParams, queryValue);
			module.delete(requestContext, queryValue);
		} catch (UserException | TechnicalException | EJBException e) {
			if (e instanceof EJBException && e.getCause() != null) {
				throw analyzeEJBException(e.getCause());
			}
			throw new RemoteException(e);
		}
	}

	protected abstract String getModuleLookup();
	
	public ValueObject createValueObject() throws TechnicalException {
		return module.buildVO();
	}

	private RemoteException analyzeEJBException(final Throwable cause) {
		final TechnicalException analyzedException = new TechnicalException("EJBException: " + cause.getMessage());
		if (cause instanceof EntityExistsException) {
			return new RemoteException(new UserException(new UserError(UserError.MSG_ENTITY_EXISTS)));
		}
		if (cause instanceof PersistenceException) {
			final Throwable cause2 = ((PersistenceException)cause).getCause();
			if (cause2 instanceof EntityExistsException) {
				return new RemoteException(new UserException(new UserError(UserError.MSG_ENTITY_EXISTS)));
			}
		}
		return new RemoteException(analyzedException);
	}

	private void mapVO2XmlRpc(final ValueObject valueObject, final Map<String, Object> resultValue)
			throws UserException, TechnicalException {
		final UserErrorList errors = new UserErrorList();
		final List<Property<?>> props = valueObject.properties();
		for (Property<?> p : props) {
			p.copyValueToParameterMap(resultValue);
		}
		errors.raiseException();
	}

	private void mapXmlRpc2VO(final Map<String, Object> paramsMap,
			final VO valueObject) throws UserException, TechnicalException {
		final UserErrorList errors = new UserErrorList();
		final Set<String> keySet = paramsMap.keySet();
		for (String key : keySet) {
			if (valueObject.hasProperty(key)) {
				final Property<?> property = valueObject.get(key);
				property.copyValueFromParameterMap(paramsMap);
			} else {
				errors.add(UserError.MSG_FIELD_DOESNOT_EXIST, key);
			}
		}
		errors.raiseException();
	}

}
