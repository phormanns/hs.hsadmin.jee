package de.hsadmin.module.property.mapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.module.ValueObject;
import de.hsadmin.module.property.Property;

public class DefaultListParameterMapMapper<VO extends ValueObject> implements ParameterMapMapper<List<VO>> {
	
	final private Class<? extends ValueObject> elementsType;

	public DefaultListParameterMapMapper(Class<? extends ValueObject> elementsType) {
		this.elementsType = elementsType;
	}

	@Override
	public void writeValueToParameterMap(Map<String, Object> rpcParameter, String propertyName, List<VO> value) 
					throws TechnicalException, UserException {
		List<Map<String,Object>> list = new ArrayList<>();
		for (Object o : value) {
			assert o instanceof ValueObject;
			ValueObject vo = (ValueObject) o;
			Map<String,Object> map = new HashMap<>();
			List<Property<?>> properties = vo.properties();
			for (Property<?> p : properties) {
				p.copyValueToParameterMap(map);
			}
			list.add(map);
		}
		rpcParameter.put(propertyName, list);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VO> readValueFromParameterMap(Map<String, Object> rpcParameter, String propertyName) 
				throws TechnicalException, UserException {
		final List<VO> value = new ArrayList<>();
		final Object list = rpcParameter.get(propertyName);
		try {
			if (list instanceof Object[]) {
				for (Object obj : (Object[])list) {
					if (obj instanceof Map<?, ?>) {
						final VO vo = (VO) elementsType.newInstance();
						final Map<?, ?> map = (Map<?, ?>) obj;
						for (Object key : map.keySet()) {
							if (key instanceof String) {
								final Property<?> property = vo.get((String)key);
								property.copyValueFromParameterMap((Map<String, Object>) map);
							}
						}
						value.add(vo);
					}
				}
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new TechnicalException(e);
		}
		return value;
	}
}