package de.hsadmin.xmlrpc;

import java.util.List;
import java.util.Map;


public interface Remote {

	public abstract List<Map<String, Object>> search(
			String runAsUser,
			String ticket, 
			Map<String, Object> whereParams
		) throws RemoteException;

	public abstract Map<String, Object> add(
			String runAsUser, 
			String ticket,
			Map<String, Object> setParams
		) throws RemoteException;

	public abstract void delete(
			String runAsUser, 
			String ticket,
			Map<String, Object> whereParams
		) throws RemoteException;

	public abstract List<Map<String, Object>> update(
			String runAsUser,
			String ticket, 
			Map<String, Object> setParams,
			Map<String, Object> whereParams
		) throws RemoteException;

}