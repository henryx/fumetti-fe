package com.application.fumetti.frontend.views.series;

import com.application.fumetti.frontend.Configuration;
import com.application.fumetti.frontend.mappers.Response;
import com.application.fumetti.frontend.mappers.data.SeriesData;
import com.application.fumetti.frontend.utils.Notifications;
import com.application.fumetti.frontend.utils.Requests;
import com.application.fumetti.frontend.views.MainLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

@PageTitle("Serie")
@Route(value = "/series", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class SeriesView extends Div {
    private final Configuration config;

    @VaadinServiceScoped
    public SeriesView(Configuration config) {
        this.config = config;

        var upper = new VerticalLayout();
        var bottom = new VerticalLayout();

        var title = new H2("Serie");
        title.setVisible(true);

        var addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        addButton.getElement().setAttribute("aria-label", "Aggiungi serie");
        addButton.addClickListener(e -> addButton.getUI().ifPresent(ui -> ui.navigate("/series/add")));

        upper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        upper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        bottom.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        bottom.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        bottom.setAlignItems(FlexComponent.Alignment.STRETCH);

        upper.add(title);
        bottom.add(addButton, initGrid());
        add(upper, bottom);
    }

    private Grid<SeriesData> initGrid() {
        var mapper = new ObjectMapper();
        var req = new Requests(this.config);
        var grid = new Grid<>(SeriesData.class, false);

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addColumn(SeriesData::id).setHeader("Id").setVisible(false);
        grid.addColumn(SeriesData::name).setHeader("Nome");
        grid.addColumn(cd -> cd.genre().description()).setHeader("Genere");
        grid.addColumn(cd -> cd.status().description()).setHeader("Stato");
        grid.addColumn(cd -> cd.frequency().description()).setHeader("Periodicità");
        grid.addColumn(cd -> cd.editor().name()).setHeader("Editore");
        try {
            var body = req.get("/api/v1/series");
            var data = mapper.readValue(body, Response.class);
            var series = data.getData().stream().map(e -> {
                var map = (HashMap<String, Object>) e;
                return SeriesData.map(map);
            }).toList();
            grid.setItems(series);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        return grid;
    }
}
