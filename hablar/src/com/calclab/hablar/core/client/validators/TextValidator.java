package com.calclab.hablar.core.client.validators;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.HasKeyDownHandlers;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.HasText;

public class TextValidator {
    private final ArrayList<Validator<String>> validators;
    private final HasText errorText;
    private final HasState<Boolean> acceptEnabled;
    private final HasText hasValue;

    public TextValidator(final HasKeyDownHandlers keys, final HasText hasValue, final HasText errorText,
	    final HasState<Boolean> acceptState) {
	this.hasValue = hasValue;
	this.errorText = errorText;
	acceptEnabled = acceptState;
	validators = new ArrayList<Validator<String>>();

	final Command command = new Command() {
	    @Override
	    public void execute() {
		validate();
	    }
	};
	keys.addKeyDownHandler(new KeyDownHandler() {
	    @Override
	    public void onKeyDown(final KeyDownEvent event) {
		DeferredCommand.addCommand(command);
	    }
	});
    }

    public void add(final Validator<String> validator) {
	validators.add(validator);
    }

    public void validate() {
	final String value = hasValue.getText();
	for (final Validator<String> validator : validators) {
	    if (!validator.isValid(value)) {
		errorText.setText(validator.getMessage());
		acceptEnabled.setState(false);
		return;
	    }
	}
	errorText.setText("");
	acceptEnabled.setState(true);
    }

}
