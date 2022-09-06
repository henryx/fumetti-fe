package com.application.fumetti.frontend.views.editors;

import com.application.fumetti.frontend.Configuration;
import com.application.fumetti.frontend.utils.Notifications;
import com.application.fumetti.frontend.views.MainLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@PageTitle("Aggiungi Editore")
@Route(value = "/editors/add", layout = MainLayout.class)
public class AddEditorsView extends Div {
    private final Configuration config;
    private final ObjectMapper mapper;

    public AddEditorsView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();

        var upper = new VerticalLayout();
        var center = new VerticalLayout();
        var bottom = new VerticalLayout();

        bottom.add(this.initButtons());

        add(upper, center, bottom);
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla");
        cancel.addClickListener(e -> cancel.getUI().ifPresent(ui -> ui.navigate("/editors")));
        save.addClickListener(e -> {
            Notifications.success("Editor inserted successfully");
            save.getUI().ifPresent(ui -> ui.navigate("/editors"));
        });
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.getStyle().set("margin-inline-end", "auto");

        var layout = new HorizontalLayout();
        layout.add(save, cancel);

        return layout;
    }
}