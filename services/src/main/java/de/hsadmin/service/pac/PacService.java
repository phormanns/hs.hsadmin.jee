package de.hsadmin.service.pac;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.hsadmin.bo.customer.Customer;
import de.hsadmin.bo.pac.BasePac;
import de.hsadmin.bo.pac.Hive;
import de.hsadmin.bo.pac.INetAddress;
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
public class PacService extends AbstractModule<PacVO> implements PacServiceLocal {

	@PersistenceContext(name="hsar")
	private EntityManager entityManager;

	@Override
	public PacVO buildVO() throws TechnicalException {
		return new PacVO();
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public PacVO create(final RequestContext requestContext, final PacVO prototype)
			throws UserException, TechnicalException {
		final PacVO vo = super.create(requestContext, prototype);
		final Pac bo = new Pac();
		final String inetAddressProperty = prototype.getInetAddress();
		assert inetAddressProperty != null;
		bo.setCurINetAddr(findInetAddress(inetAddressProperty));
		final String hiveProperty = prototype.getHive();
		assert hiveProperty != null;
		bo.setHive(findHiveByName(hiveProperty));
		final String basePacProperty = prototype.getBasePac();
		assert basePacProperty != null;
		bo.setBasePac(findBasePacByName(basePacProperty));
		final String customerProperty = prototype.getCustomer();
		assert customerProperty != null;
		bo.setCustomer(findCustomerByName(customerProperty));
		vo.copyPropertiesToPersistentObject(bo);
		if (bo.getCreated() == null) {
			bo.setCreated(new Date());
		}
		bo.initPacComponents(entityManager, bo.getBasePac(), true);
		entityManager.persist(bo);
		final Pac newBO = findPacByName(vo.getName());
		final PacVO newVO = new PacVO();
		newVO.copyPropertiesFromPersistentObject(newBO);
		return newVO;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM), @ScopePolicy(value=Role.HIVE, property="name")})
	public List<PacVO> read(final RequestContext requestContext, final PacVO criteria) 
			throws UserException, TechnicalException {
		final List<PacVO> emptyList = super.read(requestContext, criteria);
		final List<Pac> list = QueryBuilder.newBuilder(entityManager, Pac.class).getResultList(criteria);
		for (Pac c : list) {
			PacVO vo = new PacVO();
			vo.copyPropertiesFromPersistentObject(c);
			emptyList.add(vo);
		}
		return emptyList;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public List<PacVO> update(final RequestContext requestContext, final PacVO criteria, final PacVO prototype) 
			throws UserException, TechnicalException {
		final List<PacVO> pacsForUpdate = super.update(requestContext, criteria, prototype);
		for (PacVO vo : pacsForUpdate) {
			final Pac bo = findPacByName(vo.getName());
			final String customerName = prototype.getCustomer();
			if (customerName != null && !customerName.isEmpty()) {
				Customer customer = findCustomerByName(customerName);
				bo.setCustomer(customer);
			}
			prototype.copyPropertiesToPersistentObject(bo);
			vo.copyPropertiesFromPersistentObject(bo);
		}
		return pacsForUpdate;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public void delete(final RequestContext requestContext, final PacVO criteria)
			throws UserException, TechnicalException {
		super.delete(requestContext, criteria);
		final List<Pac> list = QueryBuilder.newBuilder(entityManager, Pac.class).getResultList(criteria);
		for (Pac pac : list) {
			checkDeleteConditions(pac);
			entityManager.remove(pac);
		}
	}

	private void checkDeleteConditions(Pac pac) throws UserException {
		UserErrorList errors = new UserErrorList();
		Date cancelledDate = pac.getCancelled();
		if (cancelledDate == null) {
			errors.add(UserError.MSG_PAC_NOT_CANCELLED, pac.getName());
		} else {
			if (cancelledDate.getTime() > System.currentTimeMillis()) {
				errors.add(UserError.MSG_PAC_CANCEL_DATE_IN_FUTURE, pac.getName(), DateUtil.DEFAULT_DATEFORMAT.format(cancelledDate));
			}
		}
		errors.raiseException();
	}

	private Hive findHiveByName(final String hiveName) throws UserException {
		final Query query = entityManager.createQuery("SELECT h FROM Hive h WHERE h.name = :name");
		query.setParameter("name", hiveName);
		return (Hive) query.getSingleResult();
	}
	
	private Pac findPacByName(final String pacName) throws UserException {
		final Query query = entityManager.createQuery("SELECT p FROM Pac p WHERE p.name = :name");
		query.setParameter("name", pacName);
		return (Pac) query.getSingleResult();
	}
	
	private INetAddress findInetAddress(final String inetAddr) throws UserException, TechnicalException {
		final String trimmedINetAddress = inetAddr.trim();
		try {
			InetAddress.getByName(trimmedINetAddress);
		} catch (UnknownHostException e) {
			throw new UserException(e);
		}
		final Query query = entityManager.createNativeQuery("SELECT * FROM inet_addr WHERE inet_addr = inet '" + trimmedINetAddress + "'", INetAddress.class);
		return (INetAddress) query.getSingleResult();
	}
	
	private BasePac findBasePacByName(final String value) {
		final Query query = entityManager.createQuery("SELECT p FROM BasePac p WHERE p.name = :name");
		query.setParameter("name", value);
		return (BasePac) query.getSingleResult();
	}

	private Customer findCustomerByName(final String value) {
		final Query query = entityManager.createQuery("SELECT c FROM Customer c WHERE c.name = :name");
		query.setParameter("name", value);
		return (Customer) query.getSingleResult();
	}

}
