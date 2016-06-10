package de.hsadmin.service.customer;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.hsadmin.bo.customer.Contact;
import de.hsadmin.bo.customer.Customer;
import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.login.RequestContext;
import de.hsadmin.login.RequiredScope;
import de.hsadmin.login.Role;
import de.hsadmin.login.ScopePolicy;
import de.hsadmin.module.impl.AbstractModule;
import de.hsadmin.module.util.QueryBuilder;

@Stateless
public class ContactService extends AbstractModule<ContactVO> implements ContactServiceLocal {

	@PersistenceContext(name="hsar")
	private EntityManager entityManager;

	@Override
	public ContactVO buildVO() throws TechnicalException {
		return new ContactVO();
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public ContactVO create(final RequestContext requestContext, final ContactVO prototype)
			throws UserException, TechnicalException {
		final ContactVO vo = super.create(requestContext, prototype);
		final Contact bo = new Contact();
		bo.setCustomer(findCustomerByName(prototype.getCustomer()));
		vo.copyPropertiesToPersistentObject(bo);
		entityManager.persist(bo);
		final Contact newBO = findContactByNames(vo);
		final ContactVO newVO = new ContactVO();
		newVO.copyPropertiesFromPersistentObject(newBO);
		return newVO;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM), @ScopePolicy(value=Role.CUSTOMER, property="customer")})
	public List<ContactVO> read(final RequestContext requestContext, final ContactVO criteria) 
			throws UserException, TechnicalException {
		final List<ContactVO> emptyList = super.read(requestContext, criteria);
		final List<Contact> list = QueryBuilder.newBuilder(entityManager, Contact.class).getResultList(criteria);
		for (Contact c : list) {
			ContactVO vo = new ContactVO();
			vo.copyPropertiesFromPersistentObject(c);
			emptyList.add(vo);
		}
		return emptyList;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public List<ContactVO> update(final RequestContext requestContext, final ContactVO criteria, final ContactVO prototype) 
			throws UserException, TechnicalException {
		final List<ContactVO> contactsForUpdate = super.update(requestContext, criteria, prototype);
		for (ContactVO vo : contactsForUpdate) {
			final Contact bo = findContactByNames(vo);
			prototype.copyPropertiesToPersistentObject(bo);
			vo.copyPropertiesFromPersistentObject(bo);
		}
		return contactsForUpdate;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public void delete(final RequestContext requestContext, final ContactVO criteria)
			throws UserException, TechnicalException {
		super.delete(requestContext, criteria);
		final List<Contact> list = QueryBuilder.newBuilder(entityManager, Contact.class).getResultList(criteria);
		for (Contact c : list) {
			entityManager.remove(c);
		}
	}

	private Contact findContactByNames(final ContactVO prototype) throws UserException, TechnicalException {
		final Query query = entityManager.createQuery("SELECT c FROM Contact c WHERE c.firstName = :firstname AND c.lastName = :lastname AND c.customer.name = :customer AND c.email = :email");
		query.setParameter("firstname", prototype.get("firstName").getValue());
		query.setParameter("lastname", prototype.get("lastName").getValue());
		query.setParameter("customer", prototype.get("customer").getValue());
		query.setParameter("email", prototype.get("email").getValue());
		return (Contact) query.getSingleResult();
	}
	
	private Customer findCustomerByName(final String customer) throws UserException {
		final Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = :name");
		query.setParameter("name", customer);
		return (Customer) query.getSingleResult();
	}
	
}
