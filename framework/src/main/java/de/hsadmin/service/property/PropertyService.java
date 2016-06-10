package de.hsadmin.service.property;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.login.RequestContext;
import de.hsadmin.login.RequiredScope;
import de.hsadmin.login.Role;
import de.hsadmin.login.ScopePolicy;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.impl.AbstractModule;
import de.hsadmin.module.property.Display;
import de.hsadmin.module.property.DisplayPolicy;
import de.hsadmin.module.property.ReadWrite;
import de.hsadmin.module.property.ReadWritePolicy;
import de.hsadmin.module.property.Search;
import de.hsadmin.module.property.SearchPolicy;
import de.hsadmin.xmlrpc.AbstractRemote;

@Stateless
public class PropertyService extends AbstractModule<PropertyVO> implements PropertyServiceLocal {

	@PersistenceContext(name="hsar")
	private EntityManager entityManager;

	@Override
	public PropertyVO buildVO() throws TechnicalException {
		return new PropertyVO();
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.NONE)})
	public PropertyVO create(final RequestContext requestContext, final PropertyVO prototype)
			throws UserException, TechnicalException {
		return null;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.ANY)})
	public List<PropertyVO> read(final RequestContext requestContext, final PropertyVO criteria) 
			throws UserException, TechnicalException {
		final List<PropertyVO> emptyList = super.read(requestContext, criteria);
		final Properties remoteServicesProperties = new Properties();
		final String requestedModuleName = criteria.getModule();
		try {
			remoteServicesProperties.load(getClass().getClassLoader().getResourceAsStream("org/apache/xmlrpc/webserver/XmlRpcServlet.properties"));
			final Enumeration<?> propertyNames = remoteServicesProperties.propertyNames();
			while (propertyNames.hasMoreElements()) {
				final String properyName = (String) propertyNames.nextElement();
				if (requestedModuleName != null && !requestedModuleName.equals(properyName)) {
					continue;
				}
				final Class<?> serviceRemoteClass = Class.forName(remoteServicesProperties.getProperty(properyName));
				@SuppressWarnings("unchecked")
				final AbstractRemote<ValueObject> serviceRemote = (AbstractRemote<ValueObject>) serviceRemoteClass.newInstance();
				final ValueObject valueObject = serviceRemote.createValueObject();
				final Field[] declaredFields = valueObject.getClass().getDeclaredFields();
				for (Field f : declaredFields) {
					final PropertyVO vo = buildVO();
					vo.setModule(properyName);
					vo.setName(f.getName());
					final ReadWrite readWrite = f.getAnnotation(ReadWrite.class);
					if (readWrite == null) {
						vo.setReadwriteable(ReadWritePolicy.NONE.name().toLowerCase());
					} else {
						vo.setReadwriteable(readWrite.value().name().toLowerCase());
					}
					final Search search = f.getAnnotation(Search.class);
					if (search == null) {
						vo.setSearchable(SearchPolicy.NONE.name().toLowerCase());
					} else {
						vo.setSearchable(search.value().name().toLowerCase());
					}
					final Display sequence = f.getAnnotation(Display.class);
					if (sequence == null) {
						vo.setDisplaySequence(Integer.valueOf(9999));
						vo.setDisplayVisible(DisplayPolicy.ALWAYS.name().toLowerCase());;
					} else {
						vo.setDisplaySequence(sequence.sequence());
						vo.setDisplayVisible(sequence.visible().name().toLowerCase());;
					}
					final Pattern pattern = f.getAnnotation(Pattern.class);
					if (pattern == null) {
						vo.setValidationRegexp("[a-zA-Z0-9\\_\\-\\.\\,\\ ]*");
					} else {
						vo.setValidationRegexp(pattern.regexp());
					}
					final Size size = f.getAnnotation(Size.class);
					if (size == null) {
						vo.setMinLength(Integer.valueOf(0));
						vo.setMaxLength(Integer.valueOf(999));
					} else {
						vo.setMinLength(size.min());
						vo.setMaxLength(size.max());
					}
					vo.setType(printableTypeName(valueObject.get(f.getName()).getValueType()));
					emptyList.add(vo);
				}
			}
		} catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			throw new TechnicalException(e);
		}
		return emptyList;
	}

	private String printableTypeName(Class<?> type) {
		String name = type.getName().toLowerCase();
		if (name.indexOf('.') >= 0) {
			final int lastDot = name.lastIndexOf('.');
			name = name.substring(lastDot + 1);
		}
		if ("arraylist".equals(name)) {
			name = "array";
		}
		return name;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.NONE)})
	public List<PropertyVO> update(RequestContext requestContext, PropertyVO criteria, PropertyVO prototype) 
			throws UserException, TechnicalException {
		return null;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.NONE)})
	public void delete(RequestContext requestContext, PropertyVO criteria)
			throws UserException, TechnicalException {
	}

}
