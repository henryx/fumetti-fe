package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

@PageTitle("Editori")
@Route(value = "editors", layout = MainLayout.class)
public class EditorsView extends VerticalLayout {
    private final Configuration config;

    @VaadinServiceScoped
    public EditorsView(Configuration config) {
        this.config = config;

        H1 title = new H1("Editori");
        title.setVisible(true);
        add(title);
    }
}
