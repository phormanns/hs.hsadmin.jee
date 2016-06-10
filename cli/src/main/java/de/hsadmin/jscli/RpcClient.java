package de.hsadmin.jscli;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import de.hsadmin.jscli.cas.CASTicketProvider;
import de.hsadmin.jscli.conf.Config;
import de.hsadmin.jscli.exception.JSCliException;

public class RpcClient {

	private static final String XMLRPC_URL = "https://config.hostsharing.net:443/hsar/xmlrpc/hsadmin";
	
	private final List<XmlRpcClient> clientList;
	private final Map<String, XmlRpcClient> clientMap;

	public RpcClient(final CASTicketProvider tgt) throws JSCliException {
		clientList = new ArrayList<XmlRpcClient>();
		clientMap = new HashMap<String, XmlRpcClient>();
		try {
			final String xmlrpcURLsString = Config.getInstance().getProperty("xmlrpcURL", XMLRPC_URL);
			final String[] xmlrpcURLsArray = xmlrpcURLsString.split(",");
			for (final String xmlrpcURL : xmlrpcURLsArray) {
				final XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
				config.setServerURL(new URL(xmlrpcURL));
				config.setEnabledForExtensions(true);
				final XmlRpcClient client = new XmlRpcClient();
				client.setConfig(config);
				clientList.add(client);
			}
		} catch (MalformedURLException e) {
			throw new JSCliException(e);
		}
	}
	
	public List<String> listMethods() throws JSCliException {
		final List<String> methodList = new ArrayList<String>();
		for (final XmlRpcClient client : clientList) {
			final List<Object> execute = execute(client, "system.listMethods");
			for (final Object obj : execute) {
				final String methodString = obj.toString();
				final String[] path = methodString.split("\\.");
				if (path.length == 2) {
					clientMap.put(path[0], client);
				}
				methodList.add(methodString);
			}
		}
		return methodList;
	}

	private List<Object> execute(final XmlRpcClient client, final String method) throws JSCliException {
		return execute(client, method, new ArrayList<Object>());
	}

	private List<Object> execute(final XmlRpcClient client, final String method, final List<?> params) throws JSCliException {
		try {
			final Object execute = client.execute(method, params);
			final ArrayList<Object> list = new ArrayList<Object>();
			if (execute instanceof Object[]) {
				final Object[] resArray = (Object[]) execute;
				for (int idx=0; idx < resArray.length; idx++) {
					list.add(resArray[idx]);				
				}
			}
			if (execute instanceof Map) {
				list.add(execute);				
			}
			return list;
		} catch (XmlRpcException e) {
			throw new JSCliException(e);
		}
	}
	
	public List<Object> execute(final String method, final List<?> params) throws JSCliException {
		final String[] path = method.split("\\.");
		if (path.length == 2) {
			return execute(clientMap.get(path[0]), method, params);
		} else {
			throw new JSCliException("method not found: " + method);
		}
	}
	
}
