package com.application.fumetti.frontend.views.series;

import com.application.fumetti.frontend.Configuration;
import com.application.fumetti.frontend.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Aggiungi Serie")
@Route(value = "/series/add", layout = MainLayout.class)
public class AddSeriesView extends Div {
    private final Configuration config;

    public AddSeriesView(Configuration config) {
        this.config = config;
        var upper = new VerticalLayout();
        var bottom = new VerticalLayout();

        var title = new H2("Aggiungi serie");
        var name = new TextField("Nome");

        upper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        upper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        bottom.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        bottom.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        bottom.setAlignItems(FlexComponent.Alignment.STRETCH);

        upper.add(title);
        bottom.add(name);
        add(upper, bottom);
    }
}
