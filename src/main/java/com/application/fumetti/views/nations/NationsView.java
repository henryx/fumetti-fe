package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.NationData;
import com.application.fumetti.utils.Notifications;
import com.application.fumetti.utils.Requests;
import com.application.fumetti.views.MainLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

@PageTitle("Nazioni")
@Route(value = "nations", layout = MainLayout.class)
public class NationsView extends VerticalLayout {
    private final Configuration config;
    private final Grid<NationData> grid;
    private final ObjectMapper mapper;

    @VaadinServiceScoped
    public NationsView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();
        var req = new Requests(this.config);

        var title = new H1("Nazioni");
        title.setVisible(true);

        this.grid = new Grid<>(NationData.class, false);
        this.grid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.grid.addColumn(NationData::id).setHeader("Id").setVisible(false);
        this.grid.addColumn(NationData::name).setHeader("Nome");
        this.grid.addColumn(NationData::sign).setHeader("Simbolo");

        try {
            var body = req.get("/nations");
            var data = this.mapper.readValue(body, Response.class);

            var nations = data.getData().stream().map(e -> {
                var map = (HashMap<String, Object>) e;
                return NationData.map(map);
            }).toList();
            this.grid.setItems(nations);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }
        this.grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        var addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        addButton.getElement().setAttribute("aria-label", "Aggiungi nazione");
        addButton.addClickListener(clickEvent -> {
            var dialog = new AddNationDialog(this.config);
            dialog.setGrid(this.grid);
            dialog.open();
        });

        add(title, addButton, this.grid);
    }
}
