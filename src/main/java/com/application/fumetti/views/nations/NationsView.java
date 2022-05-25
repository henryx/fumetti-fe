package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

@PageTitle("Nazioni")
@Route(value = "nations", layout = MainLayout.class)
public class NationsView extends VerticalLayout {
    private final Configuration config;

    @VaadinServiceScoped
    public NationsView(Configuration config) {
        this.config = config;

        var title = new H1("Nazioni");
        title.setVisible(true);

        add(title);
    }
}
