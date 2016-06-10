package de.hsadmin.service.customer;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.hsadmin.bo.customer.Contact;
import de.hsadmin.bo.customer.Customer;
import de.hsadmin.bo.customer.SEPADirectDebit;
import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.login.RequestContext;
import de.hsadmin.login.RequiredScope;
import de.hsadmin.login.Role;
import de.hsadmin.login.ScopePolicy;
import de.hsadmin.module.impl.AbstractModule;
import de.hsadmin.module.util.QueryBuilder;

@Stateless
public class SEPADirectDebitService extends AbstractModule<SEPADirectDebitVO> implements SEPADirectDebitServiceLocal {

	@PersistenceContext(name="hsar")
	private EntityManager entityManager;

	@Override
	public SEPADirectDebitVO buildVO() throws TechnicalException {
		return new SEPADirectDebitVO();
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public SEPADirectDebitVO create(final RequestContext requestContext, final SEPADirectDebitVO prototype)
			throws UserException, TechnicalException {
		final SEPADirectDebitVO vo = super.create(requestContext, prototype);
		final SEPADirectDebit bo = new SEPADirectDebit();
		bo.setCustomer(findCustomerByName(prototype.getCustomer()));
		vo.copyPropertiesToPersistentObject(bo);
		entityManager.persist(bo);
		final SEPADirectDebit newBO = findMandatByValues(vo);
		final SEPADirectDebitVO newVO = new SEPADirectDebitVO();
		newVO.copyPropertiesFromPersistentObject(newBO);
		return newVO;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM), @ScopePolicy(value=Role.CUSTOMER, property="customer")})
	public List<SEPADirectDebitVO> read(final RequestContext requestContext, final SEPADirectDebitVO criteria) 
			throws UserException, TechnicalException {
		final List<SEPADirectDebitVO> emptyList = super.read(requestContext, criteria);
		final List<SEPADirectDebit> list = QueryBuilder.newBuilder(entityManager, SEPADirectDebit.class).getResultList(criteria);
		for (SEPADirectDebit c : list) {
			final SEPADirectDebitVO vo = new SEPADirectDebitVO();
			vo.copyPropertiesFromPersistentObject(c);
			emptyList.add(vo);
		}
		return emptyList;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public List<SEPADirectDebitVO> update(final RequestContext requestContext, final SEPADirectDebitVO criteria, final SEPADirectDebitVO prototype) 
			throws UserException, TechnicalException {
		final List<SEPADirectDebitVO> mandantsForUpdate = super.update(requestContext, criteria, prototype);
		for (SEPADirectDebitVO vo : mandantsForUpdate) {
			final SEPADirectDebit bo = findMandatByValues(vo);
			prototype.copyPropertiesToPersistentObject(bo);
			vo.copyPropertiesFromPersistentObject(bo);
		}
		return mandantsForUpdate;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public void delete(final RequestContext requestContext, final SEPADirectDebitVO criteria)
			throws UserException, TechnicalException {
		super.delete(requestContext, criteria);
		final List<Contact> list = QueryBuilder.newBuilder(entityManager, Contact.class).getResultList(criteria);
		for (Contact c : list) {
			entityManager.remove(c);
		}
	}

	private SEPADirectDebit findMandatByValues(final SEPADirectDebitVO prototype) throws UserException, TechnicalException {
		final Query query = entityManager.createQuery("SELECT m FROM SEPADirectDebit m WHERE m.bankIBAN = :bankIBAN AND m.bankBIC = :bankBIC AND m.customer.name = :customer AND m.mandatSigned = :mandatSigned");
		query.setParameter("bankIBAN", prototype.get("bankIBAN").getValue());
		query.setParameter("bankBIC", prototype.get("bankBIC").getValue());
		query.setParameter("customer", prototype.get("customer").getValue());
		query.setParameter("mandatSigned", prototype.get("mandatSigned").getValue());
		return (SEPADirectDebit) query.getSingleResult();
	}
	
	private Customer findCustomerByName(final String customer) throws UserException {
		final Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = :name");
		query.setParameter("name", customer);
		return (Customer) query.getSingleResult();
	}
	
}
