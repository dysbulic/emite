package com.calclab.hablar.core.client.pages.accordion;

import com.calclab.hablar.core.client.pages.HeaderDisplay;
import com.calclab.hablar.core.client.ui.icon.HablarIcons;
import com.calclab.hablar.core.client.ui.icon.HablarIcons.IconType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class AccordionHeaderWidget extends Composite implements HeaderDisplay {

    interface AccordionHeaderWidgetUiBinder extends UiBinder<Widget, AccordionHeaderWidget> {
    }

    private static AccordionHeaderWidgetUiBinder uiBinder = GWT.create(AccordionHeaderWidgetUiBinder.class);

    @UiField
    FlowPanel flow;

    @UiField
    FocusPanel self;

    @UiField
    Label title, icon, close;

    public AccordionHeaderWidget(final String pageId) {
	initWidget(uiBinder.createAndBindUi(this));
	ensureDebugId("HeaderWidget-" + pageId);
	close.ensureDebugId("HeaderWidget-" + pageId + "-close");
	close.addStyleName(HablarIcons.get(IconType.close));
    }

    @Override
    public void addIconStyle(final String iconStyle) {
	icon.addStyleName(iconStyle);
    }

    @Override
    public void addStyle(final String styleName) {
	flow.addStyleName(styleName);
    }

    @Override
    public Widget asWidget() {
	return this;
    }

    @Override
    public HasClickHandlers getClose() {
	return close;
    }

    @Override
    public HasText getHeaderTitle() {
	return title;
    }

    @Override
    public HasClickHandlers getOpen() {
	return self;
    }

    @Override
    public void removeIconStyle(final String iconStyle) {
	icon.removeStyleName(iconStyle);
    }

    @Override
    public void removeStyle(final String styleName) {
	flow.removeStyleName(styleName);
    }

    @Override
    public void setCloseIconVisible(final boolean visible) {
	close.setVisible(visible);
    }

    @Override
    public void setHeaderTooltip(final String tooltip) {
	title.setTitle(tooltip);
	icon.setTitle(tooltip);
    }

}
