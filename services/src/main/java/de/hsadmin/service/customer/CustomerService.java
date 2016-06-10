package de.hsadmin.service.customer;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.hsadmin.bo.customer.Customer;
import de.hsadmin.bo.pac.Pac;
import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserError;
import de.hsadmin.common.error.UserErrorList;
import de.hsadmin.common.error.UserException;
import de.hsadmin.common.util.DateUtil;
import de.hsadmin.login.RequestContext;
import de.hsadmin.login.RequiredScope;
import de.hsadmin.login.Role;
import de.hsadmin.login.ScopePolicy;
import de.hsadmin.module.impl.AbstractModule;
import de.hsadmin.module.util.QueryBuilder;

@Stateless
public class CustomerService extends AbstractModule<CustomerVO> implements CustomerServiceLocal {

	@PersistenceContext(name="hsar")
	private EntityManager entityManager;

	@Override
	public CustomerVO buildVO() throws TechnicalException {
		return new CustomerVO();
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public CustomerVO create(final RequestContext requestContext, final CustomerVO prototype)
			throws UserException, TechnicalException {
		final CustomerVO customerVO = super.create(requestContext, prototype);
		final String[] priceLists = customerVO.getPriceLists();
		if (priceLists == null) {
			customerVO.setPriceLists(new String[] { "Standard" });
		}
		final Customer customerBO = new Customer();
		customerVO.copyPropertiesToPersistentObject(customerBO);
		entityManager.persist(customerBO);
		final Customer newBO = findCustomerByName(prototype);
		final CustomerVO newVO = new CustomerVO();
		newVO.copyPropertiesFromPersistentObject(newBO);
		return newVO;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM), @ScopePolicy(value=Role.CUSTOMER, property="name")})
	public List<CustomerVO> read(final RequestContext requestContext, final CustomerVO criteria) throws UserException, TechnicalException {
		final List<CustomerVO> emptyList = super.read(requestContext, criteria);
		final List<Customer> list = QueryBuilder.newBuilder(entityManager, Customer.class).getResultList(criteria);
		for (Customer c : list) {
			final CustomerVO vo = new CustomerVO();
			vo.copyPropertiesFromPersistentObject(c);
			emptyList.add(vo);
		}
		return emptyList;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public List<CustomerVO> update(final RequestContext requestContext, final CustomerVO criteria, final CustomerVO prototype) 
			throws UserException, TechnicalException {
		final List<CustomerVO> customersForUpdate = super.update(requestContext, criteria, prototype);
		for (CustomerVO vo : customersForUpdate) {
			final Customer customer = findCustomerByName(vo);
			prototype.copyPropertiesToPersistentObject(customer);
			vo.copyPropertiesFromPersistentObject(customer);
		}
		return customersForUpdate;
	}
	
	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public void delete(final RequestContext requestContext, final CustomerVO criteria)
			throws UserException, TechnicalException {
		super.delete(requestContext, criteria);
		final List<Customer> list = QueryBuilder.newBuilder(entityManager, Customer.class).getResultList(criteria);
		for (Customer cust : list) {
			checkDeleteConditions(cust);
			entityManager.remove(cust);
		}
	}
	
	private void checkDeleteConditions(Customer customer) throws UserException {
		UserErrorList errors = new UserErrorList();
		Set<Pac> pacs = customer.getPacs();
		if (!pacs.isEmpty()) {
			errors.add(UserError.MSG_CUSTOMER_HAS_PACS, Integer.toString(pacs.size()));
		}
		Date memberSince = customer.getMemberSince();
		if (memberSince != null) {
			Date memberUntil = customer.getMemberUntil();
			if (memberUntil == null) {
				errors.add(UserError.MSG_CUSTOMER_IS_MEMBER, customer.getName());
			} else {
				Date earliestDeleteDate = DateUtil.getInstance(memberUntil).yearsLater(4);
				if (earliestDeleteDate.getTime() > System.currentTimeMillis()) {
					errors.add(UserError.MSG_CUSTOMER_DELETE_DEADLINE_NOT_EXPIRED, customer.getName());
				}
			}
		}
		errors.raiseException();
	}

	private Customer findCustomerByName(final CustomerVO prototype) throws UserException, TechnicalException {
		final Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = :name");
		query.setParameter("name", prototype.get("name").getValue());
		return (Customer) query.getSingleResult();
	}
	
}
