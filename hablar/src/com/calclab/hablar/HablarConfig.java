package com.calclab.hablar;

import com.calclab.emite.browser.client.PageAssist;
import com.calclab.hablar.core.client.HablarDisplay;
import com.calclab.hablar.rooms.client.HablarRoomsConfig;
import com.calclab.hablar.search.client.SearchConfig;

public class HablarConfig {

    /**
     * Retrieve Hablar configuration from meta tags in html
     */
    public static HablarConfig getFromMeta() {
	final HablarConfig config = new HablarConfig();

	config.hasRoster = PageAssist.isMetaTrue("hablar.roster");
	config.hasSearch = PageAssist.isMetaTrue("hablar.search");
	config.hasSignals = PageAssist.isMetaTrue("hablar.hasSignals");
	config.hasChat = PageAssist.isMetaTrue("hablar.hasChats");
	config.hasVCard = PageAssist.isMetaTrue("hablar.hasVCard");
	config.hasCopyToClipboard = PageAssist.isMetaTrue("hablar.hasCopyToClipboard");

	config.dockRoster = PageAssist.getMeta("hablar.dockRoster");

	final String layout = PageAssist.getMeta("hablar.layout");
	if ("tabs".equals(layout)) {
	    config.layout = HablarDisplay.Layout.tabs;
	} else {
	    config.layout = HablarDisplay.Layout.accordion;
	}

	config.roomsConfig = HablarRoomsConfig.getFromMeta();
	config.searchConfig = SearchConfig.getFromMeta();
	return config;
    }

    /**
     * The Rooms configuration
     */
    public HablarRoomsConfig roomsConfig = new HablarRoomsConfig();

    /**
     * Has ChatModule
     */
    public boolean hasChat = true;

    /**
     * Dock the roster: "left" or "right"
     */
    public String dockRoster = "left";

    /**
     * Choose a layout
     */
    public HablarDisplay.Layout layout = HablarDisplay.Layout.tabs;

    /**
     * Install Roster module
     */
    public boolean hasRoster = true;

    /**
     * Install Search module
     */
    public boolean hasSearch = true;

    /**
     * Install signals module
     */
    public boolean hasSignals = true;

    /**
     * Show user page docked on top
     */
    public boolean dockUser = true;

    /**
     * The Search module configuration
     */
    public SearchConfig searchConfig = new SearchConfig();

    /**
     * Install the copy-to-clipboard module
     */
    public boolean hasCopyToClipboard = true;

    /**
     * Install the VCard module
     */
    public boolean hasVCard = true;

}
