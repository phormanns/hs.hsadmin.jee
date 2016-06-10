package de.hsadmin.service.pac;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.hsadmin.bo.pac.Hive;
import de.hsadmin.bo.pac.INetAddress;
import de.hsadmin.common.error.TechnicalException;
import de.hsadmin.common.error.UserException;
import de.hsadmin.login.RequestContext;
import de.hsadmin.login.RequiredScope;
import de.hsadmin.login.Role;
import de.hsadmin.login.ScopePolicy;
import de.hsadmin.module.impl.AbstractModule;
import de.hsadmin.module.property.Property;
import de.hsadmin.module.property.StringProperty;
import de.hsadmin.module.util.QueryBuilder;

@Stateless
public class HiveService extends AbstractModule<HiveVO> implements HiveServiceLocal {

	@PersistenceContext(name="hsar")
	private EntityManager entityManager;

	@Override
	public HiveVO buildVO() throws TechnicalException {
		return new HiveVO();
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public HiveVO create(final RequestContext requestContext, final HiveVO prototype)
			throws UserException, TechnicalException {
		final HiveVO vo = super.create(requestContext, prototype);
		final Hive bo = new Hive();
		final Property<?> inetAddressProperty = prototype.get("inetAddress");
		assert inetAddressProperty instanceof StringProperty;
		bo.setInetAddr(findInetAddress(((StringProperty) inetAddressProperty).getValue()));
		vo.copyPropertiesToPersistentObject(bo);
		entityManager.persist(bo);
		final Hive newBO = findHiveByName(vo);
		final HiveVO newVO = new HiveVO();
		newVO.copyPropertiesFromPersistentObject(newBO);
		return newVO;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM), @ScopePolicy(value=Role.HIVE, property="name")})
	public List<HiveVO> read(final RequestContext requestContext, final HiveVO criteria) 
			throws UserException, TechnicalException {
		final List<HiveVO> emptyList = super.read(requestContext, criteria);
		final List<Hive> list = QueryBuilder.newBuilder(entityManager, Hive.class).getResultList(criteria);
		for (Hive c : list) {
			HiveVO vo = new HiveVO();
			vo.copyPropertiesFromPersistentObject(c);
			emptyList.add(vo);
		}
		return emptyList;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public List<HiveVO> update(final RequestContext requestContext, final HiveVO criteria, final HiveVO prototype) 
			throws UserException, TechnicalException {
		final List<HiveVO> hivesForUpdate = super.update(requestContext, criteria, prototype);
		for (HiveVO vo : hivesForUpdate) {
			final Hive bo = findHiveByName(vo);
			prototype.copyPropertiesToPersistentObject(bo);
			vo.copyPropertiesFromPersistentObject(bo);
		}
		return hivesForUpdate;
	}

	@Override
	@RequiredScope({@ScopePolicy(Role.SYSTEM)})
	public void delete(final RequestContext requestContext, final HiveVO criteria)
			throws UserException, TechnicalException {
		super.delete(requestContext, criteria);
		final List<Hive> list = QueryBuilder.newBuilder(entityManager, Hive.class).getResultList(criteria);
		for (Hive c : list) {
			entityManager.remove(c);
		}
	}

	private Hive findHiveByName(final HiveVO prototype) throws UserException, TechnicalException {
		final Query query = entityManager.createQuery("SELECT h FROM Hive h WHERE h.name = :name");
		query.setParameter("name", prototype.get("name").getValue());
		return (Hive) query.getSingleResult();
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
	
}
