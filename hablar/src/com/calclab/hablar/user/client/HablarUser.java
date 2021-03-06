package com.calclab.hablar.user.client;

import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.Hablar.Chain;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.user.client.presence.PresencePage;
import com.calclab.hablar.user.client.presence.PresenceWidget;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class HablarUser implements EntryPoint {

    private static UserMessages userMessages;

    public static UserMessages i18n() {
	return userMessages;
    }

    public static void install(final Hablar hablar) {
	final HablarEventBus eventBus = hablar.getEventBus();
	final UserPage user = new UserPage(eventBus, new UserWidget());
	hablar.addPage(user);

	final UserContainer container = new UserContainer(eventBus, user);
	hablar.addContainer(container, Chain.after);

	final PresenceWidget display = new PresenceWidget();
	final PresencePage presence = new PresencePage(eventBus, display);
	hablar.addPage(presence, UserContainer.ROL);
    }

    public static void setMessages(final UserMessages messages) {
	userMessages = messages;
    }

    @Override
    public void onModuleLoad() {
	UserMessages messages = (UserMessages) GWT.create(UserMessages.class);
	setMessages(messages);
	PresenceWidget.setMessages(messages);
	UserWidget.setMessages(messages);
    }

}
