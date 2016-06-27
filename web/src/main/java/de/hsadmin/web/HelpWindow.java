package de.hsadmin.web;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class HelpWindow extends Window {

	private static final long serialVersionUID = 1L;
	

	public HelpWindow(final String helpTopic) {
		// Create a sub-window and set the content
		super("Help Window");
		setWidth("80%");
		setHeight("80%");
		// Center it in the browser window
		center();

		final VerticalLayout subContent = new VerticalLayout();
		subContent.setMargin(true);
		subContent.setSizeFull();
		final String helpBaseURL = I18N.getText("help.baseurl");
		final Component helpContent = new BrowserFrame("Help Window", new ExternalResource(helpBaseURL + helpTopic));
		helpContent.setSizeFull();
		subContent.addComponent(helpContent);
		final Button ok = new Button("OK");
		ok.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -6121701552072481416L;

			public void buttonClick(ClickEvent event) {
				close(); // Close the sub-window
			}
		});
		subContent.addComponent(ok);
		subContent.setExpandRatio(helpContent, 1.0f);
		subContent.setExpandRatio(ok, 0.0f);

		setContent(subContent);
	}
}
