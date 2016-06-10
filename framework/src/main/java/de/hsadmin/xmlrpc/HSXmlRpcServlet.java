package de.hsadmin.xmlrpc;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.metadata.XmlRpcSystemImpl;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcHandlerMapping;
import org.apache.xmlrpc.webserver.XmlRpcServlet;

public class HSXmlRpcServlet extends XmlRpcServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected XmlRpcHandlerMapping newXmlRpcHandlerMapping()
			throws XmlRpcException {
		XmlRpcHandlerMapping handlerMapping = super.newXmlRpcHandlerMapping();
		XmlRpcSystemImpl.addSystemHandler((PropertyHandlerMapping) handlerMapping);
		return handlerMapping;
	}

}
