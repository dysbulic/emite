package com.calclab.hablar.user.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserWidget extends Composite implements UserDisplay {

    private static UserMessages messages;

    public static void setMessages(final UserMessages messages) {
	UserWidget.messages = messages;
    }

    public static UserMessages i18n() {
	return messages;
    }

    interface UserWidgetUiBinder extends UiBinder<Widget, UserWidget> {
    }

    private static UserWidgetUiBinder uiBinder = GWT.create(UserWidgetUiBinder.class);

    @UiField
    FlowPanel container;
    @UiField
    Button close;

    public UserWidget() {
	initWidget(uiBinder.createAndBindUi(this));
	close.ensureDebugId("UserWidget-close");
	close.setText(i18n().closeAction());
    }

    @Override
    public void addPage(final EditorPage<?> page) {
	// final HeaderPresenter head = new HeaderPresenter(page, new
	// AccordionHeaderWidget(page.getId()));
	// container.add(head.getDisplay().asWidget());
	container.add(page.getDisplay().asWidget());
    }

    @Override
    public Widget asWidget() {
	return this;
    }

    @Override
    public HasClickHandlers getClose() {
	return close;
    }
}
