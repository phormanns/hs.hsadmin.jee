package de.hsadmin.web;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

public class MainPanel extends CustomComponent{

	private static final long serialVersionUID = -1085100738394404620L;

	public MainPanel() {
		final String helpBaseURL = I18N.getText("help.baseurl");
		final Component helpContent = new BrowserFrame(I18N.getText("main.panel.title"), new ExternalResource(helpBaseURL + "hsadmin"));
		helpContent.setSizeFull();
		setCompositionRoot(helpContent);
		setSizeFull();
	}
}
