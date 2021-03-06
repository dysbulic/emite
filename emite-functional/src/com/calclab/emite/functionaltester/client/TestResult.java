package com.calclab.emite.functionaltester.client;

import com.calclab.suco.client.events.Event;
import com.calclab.suco.client.events.Listener;

public class TestResult {

    public static enum State {
	notRunned, running, failed, succeed
    }
    private final FunctionalTest test;
    private long beginTime;
    private int assertions;
    private int failures;
    private int aciertos;
    private long endTime;
    private final Event<State> stateChanged;
    private State state;
    private final FunctionalTestSuite suite;
    private final String name;

    public TestResult(String name, FunctionalTest test, FunctionalTestSuite suite) {
	this.name = name;
	this.test = test;
	this.suite = suite;
	this.stateChanged = new Event<State>("testResult.stateChanged");
	setState(State.notRunned);
    }

    public void addAssertion(boolean isValid) {
	assertions++;
	if (isValid)
	    aciertos++;
	else
	    failures++;
    }

    public void finish() {
	endTime = System.currentTimeMillis();
	setState(failures > 0 ? State.failed : State.succeed);
    }

    public String getName() {
	return name;
    }

    public State getState() {
	return state;
    }

    public FunctionalTestSuite getSuite() {
	return suite;
    }

    public String getSummary() {
	String summary = "";
	summary += " [ Assertions: " + assertions;
	summary += " , Ok: " + aciertos;
	summary += " , Failed: " + failures + " ] ";
	summary += "It took: " + (endTime - beginTime) + " milliseconds.";
	return summary;
    }

    public FunctionalTest getTest() {
	return test;
    }

    public void onStateChanged(Listener<State> listener) {
	stateChanged.add(listener);
    }

    public void start() {
	beginTime = System.currentTimeMillis();
	endTime = 0;
	assertions = failures = aciertos = 0;
	setState(State.running);
    }

    private void setState(State state) {
	this.state = state;
	stateChanged.fire(state);
    }

}
