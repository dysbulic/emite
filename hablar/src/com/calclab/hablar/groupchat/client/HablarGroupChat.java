package com.calclab.hablar.groupchat.client;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.hablar.chat.client.ui.ChatPage;
import com.calclab.hablar.chat.client.ui.ChatPresenter;
import com.calclab.hablar.core.client.Hablar;
import com.calclab.hablar.core.client.Idify;
import com.calclab.hablar.core.client.container.PageAddedEvent;
import com.calclab.hablar.core.client.container.PageAddedHandler;
import com.calclab.hablar.core.client.container.overlay.OverlayContainer;
import com.calclab.hablar.core.client.page.PagePresenter.Visibility;
import com.calclab.hablar.core.client.ui.icon.HablarIcons;
import com.calclab.hablar.core.client.ui.icon.HablarIcons.IconType;
import com.calclab.hablar.core.client.ui.menu.SimpleAction;
import com.calclab.hablar.rooms.client.HablarRoomsConfig;
import com.calclab.hablar.rooms.client.open.EditRoomWidget;
import com.calclab.hablar.roster.client.groups.RosterGroupPresenter;
import com.calclab.hablar.roster.client.page.RosterPage;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class HablarGroupChat implements EntryPoint {
    public static final String ACTION_ID_CONVERT = "HablarGroupChat-convertToGroup-";
    public static final String ACTION_ID_OPEN = "HablarGroupChat-openGroupChatAction";
    private static GroupChatMessages messages;

    public static GroupChatMessages i18n() {
	return messages;
    }

    public static void install(final Hablar hablar, final HablarRoomsConfig config) {
	final OpenGroupChatPresenter openGroupPage = new OpenGroupChatPresenter(config.roomsService, hablar
		.getEventBus(), new EditRoomWidget());
	hablar.addPage(openGroupPage, OverlayContainer.ROL);
	final ConvertToGroupChatPresenter convertToGroupPage = new ConvertToGroupChatPresenter(config.roomsService,
		hablar.getEventBus(), new EditRoomWidget());
	hablar.addPage(convertToGroupPage, OverlayContainer.ROL);

	hablar.addPageAddedHandler(new PageAddedHandler() {
	    @Override
	    public void onPageAdded(final PageAddedEvent event) {
		if (event.isType(ChatPresenter.TYPE)) {
		    final ChatPage chatPage = (ChatPage) event.getPage();
		    final XmppURI uri = chatPage.getChat().getURI();
		    chatPage.addAction(createConvertToGroupChatAction(uri, convertToGroupPage));
		} else if (event.isType(RosterPage.TYPE)) {
		    final RosterPage roster = (RosterPage) event.getPage();
		    roster.getGroupMenu().addAction(openGroupChatAction(openGroupPage));
		}
	    }
	}, true);
    }

    public static void setMessages(final GroupChatMessages groupChatMessages) {
	messages = groupChatMessages;
    }

    private static SimpleAction<ChatPage> createConvertToGroupChatAction(final XmppURI uri,
	    final ConvertToGroupChatPresenter convertToGroupPage) {
	final String actionId = ACTION_ID_CONVERT + Idify.id(uri);
	return new SimpleAction<ChatPage>(i18n().convertToGroupAction(), actionId, HablarIcons.get(IconType.buddyAdd)) {
	    @Override
	    public void execute(final ChatPage chatPage) {
		convertToGroupPage.setChat(chatPage.getChat());
		convertToGroupPage.requestVisibility(Visibility.focused);
	    }
	};
    }

    private static SimpleAction<RosterGroupPresenter> openGroupChatAction(final OpenGroupChatPresenter openGroupPage) {
	return new SimpleAction<RosterGroupPresenter>(i18n().openGroupChatAction(), ACTION_ID_OPEN, HablarIcons
		.get(IconType.groupChat)) {
	    @Override
	    public void execute(final RosterGroupPresenter target) {
		openGroupPage.setGroupName(target.getGroupName());
		openGroupPage.requestVisibility(Visibility.focused);
	    }
	};
    }

    @Override
    public void onModuleLoad() {
	HablarGroupChat.setMessages((GroupChatMessages) GWT.create(GroupChatMessages.class));
    }

}
