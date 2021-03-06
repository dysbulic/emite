package com.calclab.emite.functionaltester.client.tests;

import com.calclab.emite.core.client.xmpp.session.Session;
import com.calclab.emite.functionaltester.client.Context;
import com.calclab.emite.functionaltester.client.FunctionalTest;
import com.calclab.emite.xep.disco.client.DiscoveryManager;
import com.calclab.suco.client.Suco;

public class ConnectionTestSuite extends BasicTestSuite {

    @Override
    public void afterLogin(Context ctx) {
	Session session = ctx.getSession();
	ctx.assertEquals("State should be disconnected", Session.State.disconnected, session.getState());
    }

    @Override
    public void beforeLogin(Context ctx) {
	DiscoveryManager discoveryManager = Suco.get(DiscoveryManager.class);
	discoveryManager.setActive(false);
	Session session = ctx.getSession();
	ctx.assertEquals("State should be disconnected", Session.State.disconnected, session.getState());
    }

    @Override
    public void registerTests() {
	add("Test connection", new FunctionalTest() {
	    @Override
	    public void run(Context ctx) {
		Session session = ctx.getSession();
		ctx.assertEquals("State should be connected", Session.State.ready, session.getState());
		session.logout();
	    }
	});
    }

}
