package com.calclab.hablar.rooms.client.open;

import static com.calclab.hablar.rooms.client.HablarRooms.i18n;

import java.util.Collection;
import java.util.HashMap;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.xep.muc.client.Room;
import com.calclab.hablar.core.client.mvp.HablarEventBus;
import com.calclab.hablar.core.client.page.PagePresenter;
import com.calclab.hablar.core.client.validators.TextValidator;
import com.calclab.hablar.core.client.validators.Validators;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public abstract class EditRoomPresenter extends PagePresenter<EditRoomDisplay> {

    private final HashMap<XmppURI, SelectRosterItemPresenter> itemsByUri;
    protected TextValidator roomNameValidator;

    public EditRoomPresenter(final String pageType, final HablarEventBus eventBus, final EditRoomDisplay display) {
	super(pageType, eventBus, display);
	itemsByUri = new HashMap<XmppURI, SelectRosterItemPresenter>();
	display.getCancel().addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(final ClickEvent event) {
		requestVisibility(Visibility.hidden);
	    }
	});

	display.getInvite().addClickHandler(new ClickHandler() {
	    @Override
	    public void onClick(final ClickEvent event) {
		onAccept();
		requestVisibility(Visibility.notFocused);
	    }
	});

	roomNameValidator = new TextValidator(display.getRoomNameKeys(), display.getRoomName(), display
		.getRoomNameError(), display.getAcceptEnabled());
	roomNameValidator.add(Validators.notEmpty(i18n().emptyGroupChatName()));
	// roomNameValidator.add(Validators.hasNotSpaces(i18n().spacesInRoomName()));
	roomNameValidator.add(Validators.isValidRoomName(i18n().notValidGroupChatName()));

    }

    public Collection<SelectRosterItemPresenter> getItems() {
	return itemsByUri.values();
    }

    public void setItem(final XmppURI uri, final boolean enabled, final boolean selected) {
	final SelectRosterItemPresenter item = itemsByUri.get(uri);
	if (item != null) {
	    item.setEnabled(enabled);
	    item.setSelected(selected);
	}
    }

    public void setItems(final Collection<RosterItem> items, final boolean enabled, final boolean selected) {
	display.clearList();
	itemsByUri.clear();
	for (final RosterItem item : items) {
	    createItem(item, enabled, selected);
	}
    }

    protected void createItem(final RosterItem item, final boolean selectable, final boolean selected) {
	final SelectRosterItemDisplay itemDisplay = display.createItem();
	final SelectRosterItemPresenter selectItem = new SelectRosterItemPresenter(item, itemDisplay, selectable);
	selectItem.setSelected(selected);
	display.addItem(itemDisplay);
	itemsByUri.put(item.getJID(), selectItem);
    }

    protected SelectRosterItemPresenter getItem(final XmppURI uri) {
	return itemsByUri.get(uri.getJID());
    }

    protected abstract void onAccept();

    @Override
    protected void onBeforeFocus() {
	onPageOpen();
    }

    protected abstract void onPageOpen();

    protected void sendInvitations(final Room room) {
	final String reasonText = display.getMessage().getText();
	for (final SelectRosterItemPresenter itemPresenter : getItems()) {
	    if (itemPresenter.isSelected()) {
		GWT.log("INVITING: " + itemPresenter.getItem().getJID());
		room.sendInvitationTo(itemPresenter.getItem().getJID(), reasonText);
	    }
	}
    }

}
