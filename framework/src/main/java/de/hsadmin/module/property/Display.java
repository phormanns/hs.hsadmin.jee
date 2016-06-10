package de.hsadmin.module.property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Display {

	int sequence() default 9999;
	DisplayPolicy visible() default DisplayPolicy.ALWAYS; 

}
