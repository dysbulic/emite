package com.calclab.hablar.chat.client.ui;

import java.util.ArrayList;

import com.calclab.emite.core.client.xmpp.stanzas.Presence.Show;
import com.calclab.emite.im.client.chat.Chat;
import com.calclab.hablar.core.client.page.Page;
import com.calclab.hablar.core.client.ui.menu.Action;

public interface ChatPage extends Page<ChatDisplay> {

    void addAction(Action<ChatPage> action);

    Chat getChat();

    String getChatName();

    ArrayList<ChatMessageDisplay> getMessages();

    void setPresence(boolean available, Show show);

}
