package de.hsadmin.common.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserException;

public class ReflectionUtil {

	public static boolean hasProperty(final Class<?> clasz, final String propertyName) throws UserException {
		try {
			final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, clasz);
			return propertyDescriptor.getReadMethod() != null;
		} catch (IllegalArgumentException | IntrospectionException e) {
			throw new UserException(new UserError(UserError.MSG_FIELD_DOESNOT_EXIST, propertyName));
		}
	}

	public static Annotation getAnnotation(final Class<?> clasz, final String propertyName, Class<? extends Annotation> annotationClass) throws TechnicalException {
		try {
			final Field field = clasz.getDeclaredField(propertyName);
			if (field != null) {
				return field.getAnnotation(annotationClass);
			}
			return null;
		} catch (NoSuchFieldException | SecurityException e) {
			throw new TechnicalException(e);
		}
	}

	public static Object invokeGetter(final Object anObject, final String propertyName) throws TechnicalException {
		try {
			final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, anObject.getClass());
			return propertyDescriptor.getReadMethod().invoke(anObject);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
			throw new TechnicalException(e);
		}
	}

	public static void invokeSetter(final Object anObject, final String propertyName, final Object value) throws TechnicalException {
		try {
			PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, anObject.getClass());
			propertyDescriptor.getWriteMethod().invoke(anObject, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | IntrospectionException e) {
			throw new TechnicalException(e);
		}
	}

	public static Object newInstance(final Object anObject, String propertyName) throws TechnicalException {
		try {
			final PropertyDescriptor propertyDescriptor = new PropertyDescriptor(propertyName, anObject.getClass());
			final Object newInstance = propertyDescriptor.getReadMethod().getReturnType().newInstance();
			invokeSetter(anObject, propertyName, newInstance);
			return newInstance;
		} catch (InstantiationException | IllegalAccessException | IntrospectionException | TechnicalException e) {
			throw new TechnicalException(e);
		}
	}

}
