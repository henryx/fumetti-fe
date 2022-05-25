package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.NationResult;
import com.application.fumetti.utils.Notifications;
import com.application.fumetti.utils.Requests;
import com.application.fumetti.views.MainLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URISyntaxException;

@PageTitle("Nazioni")
@Route(value = "nations", layout = MainLayout.class)
public class NationsView extends VerticalLayout {
    private final Configuration config;
    private final Grid<NationResult> grid;
    private final ObjectMapper mapper;

    @VaadinServiceScoped
    public NationsView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();
        var req = new Requests(this.config);

        var title = new H1("Nazioni");
        title.setVisible(true);

        this.grid = new Grid<>(NationResult.class, false);
        this.grid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.grid.addColumn(NationResult::getId).setHeader("Id").setVisible(false);
        this.grid.addColumn(NationResult::getName).setHeader("Nome");
        this.grid.addColumn(NationResult::getSign).setHeader("Simbolo");

        try {
            var body = req.get("/nations");
            var data = this.mapper.readValue(body, Response.class);
            this.grid.setItems(data.getData());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }
        this.grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        add(title, this.grid);
    }
}
