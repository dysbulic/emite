package com.calclab.hablar.roster.client.groups;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;

import com.calclab.emite.core.client.xmpp.stanzas.XmppURI;
import com.calclab.emite.im.client.roster.RosterGroup;
import com.calclab.emite.im.client.roster.RosterItem;
import com.calclab.emite.im.client.roster.RosterItemsOrder;
import com.calclab.hablar.core.client.Idify;
import com.calclab.hablar.core.client.mvp.Presenter;
import com.calclab.hablar.core.client.ui.menu.Menu;
import com.calclab.suco.client.events.Listener;

@SuppressWarnings("unchecked")
public class RosterGroupPresenter implements Presenter<RosterGroupDisplay> {
    private final static Comparator<RosterItem> ORDER = RosterItemsOrder.order(RosterItemsOrder.byAvailability,
	    RosterItemsOrder.groupedFirst, RosterItemsOrder.byName);

    private final RosterGroupDisplay display;
    private String groupLabel;
    private final HashMap<XmppURI, RosterItemPresenter> itemPresenters;
    private final Menu<RosterItemPresenter> itemMenu;

    private final RosterGroup group;

    public RosterGroupPresenter(final RosterGroup group, final Menu<RosterItemPresenter> itemMenu,
	    final RosterGroupDisplay display) {
	this.group = group;
	this.itemMenu = itemMenu;
	this.display = display;

	itemPresenters = new HashMap<XmppURI, RosterItemPresenter>();
	display.setVisible(group.isAllContacts());

	final Listener<RosterItem> updateListener = new Listener<RosterItem>() {
	    @Override
	    public void onEvent(final RosterItem item) {
		updateRosterItemGroups();
	    }
	};
	group.onItemAdded(updateListener);
	group.onItemChanged(updateListener);
	group.onItemRemoved(updateListener);
	updateRosterItemGroups();
    }

    @Override
    public RosterGroupDisplay getDisplay() {
	return display;
    }

    public String getGroupLabel() {
	return groupLabel;
    }

    public String getGroupName() {
	return group.getName();
    }

    public RosterGroup getRosterGroup() {
	return group;
    }

    public boolean isVisible() {
	return display.isVisible();
    }

    public void toggleVisibility() {
	display.setVisible(!display.isVisible());
    }

    private RosterItemPresenter createRosterItem(final RosterItem item) {
	// FIXME: no mola nada toda esta basura selenium
	final RosterItemDisplay itemDisplay = display.newRosterItemDisplay(Idify.id(group.getName()), Idify.id(item
		.getJID()));
	final RosterItemPresenter presenter = new RosterItemPresenter(group.getName(), itemMenu, itemDisplay);
	itemPresenters.put(item.getJID(), presenter);
	return presenter;
    }

    private RosterItemPresenter getPresenter(final RosterItem item) {
	RosterItemPresenter presenter = itemPresenters.get(item.getJID());
	if (presenter == null) {
	    presenter = createRosterItem(item);
	    display.add(presenter.getDisplay());
	}
	presenter.setItem(item);
	return presenter;
    }

    private void updateRosterItemGroups() {
	display.removeAll();
	itemPresenters.clear();
	final Collection<RosterItem> rosterItems = group.getItemList(ORDER);
	for (final RosterItem item : rosterItems) {
	    getPresenter(item);
	}
    }

}
