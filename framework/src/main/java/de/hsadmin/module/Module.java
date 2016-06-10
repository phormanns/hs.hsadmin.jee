package de.hsadmin.module;

import java.util.List;

import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.login.RequestContext;

public interface Module<VO extends ValueObject> {

	public VO create(RequestContext requestContext, VO prototype) 
			throws UserException, TechnicalException;

	public List<VO> read(RequestContext requestContext, VO criteria) 
			throws UserException, TechnicalException;

	public List<VO> update(RequestContext requestContext, VO criteria, VO prototype) 
			throws UserException, TechnicalException;

	public void delete(RequestContext requestContext, VO criteria) 
			throws UserException, TechnicalException;

	public VO buildVO() throws TechnicalException;

}
