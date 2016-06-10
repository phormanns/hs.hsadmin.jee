package de.hsadmin.login;


public @interface ScopePolicy {

	Role value();
	String property() default "";

}
