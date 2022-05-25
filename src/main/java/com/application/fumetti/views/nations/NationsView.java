package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.data.NationResult;
import com.application.fumetti.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

@PageTitle("Nazioni")
@Route(value = "nations", layout = MainLayout.class)
public class NationsView extends VerticalLayout {
    private final Configuration config;
    private final Grid<NationResult> grid;

    @VaadinServiceScoped
    public NationsView(Configuration config) {
        this.config = config;

        var title = new H1("Nazioni");
        title.setVisible(true);

        this.grid = new Grid<>(NationResult.class, false);
        this.grid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.grid.addColumn(NationResult::getId).setHeader("Id").setVisible(false);
        this.grid.addColumn(NationResult::getName).setHeader("Nome");
        this.grid.addColumn(NationResult::getSign).setHeader("Simbolo");

        add(title, this.grid);
    }
}
