package com.application.fumetti.frontend.utils;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class Notifications {

    private static void init(NotificationVariant variant, String message) {
        var notification = new Notification();
        var text = new Div(new Text(message));
        var closeButton = new Button(new Icon("lumo", "cross"));
        var layout = new HorizontalLayout();

        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.getElement().setAttribute("aria-label", "Close");
        closeButton.addClickListener(event -> notification.close());

        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(text, closeButton);

        notification.addThemeVariants(variant);
        notification.add(layout);
        notification.setDuration(5 * 1000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

    public static void error(Exception e) {
        init(NotificationVariant.LUMO_ERROR, "Failed to retrieve data: " + e.getMessage());
    }

    public static void success(String msg) {
        init(NotificationVariant.LUMO_SUCCESS, msg);
    }
}
