package com.application.fumetti.views.currencies;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CurrencyData;
import com.application.fumetti.utils.Notifications;
import com.application.fumetti.utils.Requests;
import com.application.fumetti.views.MainLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NumberRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.NumberFormat;
import java.util.HashMap;

@PageTitle("Valuta")
@Route(value = "currencies", layout = MainLayout.class)
public class CurrenciesView extends VerticalLayout {

    private final Configuration config;
    private final ObjectMapper mapper;
    private final Grid<CurrencyData> grid;

    @VaadinServiceScoped
    public CurrenciesView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();
        var req = new Requests(this.config);

        var title = new H1("Valuta");
        title.setVisible(true);

        this.grid = new Grid<CurrencyData>();
        this.grid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.grid.addColumn(CurrencyData::id).setHeader("Id").setVisible(false);
        this.grid.addColumn(CurrencyData::name).setHeader("Nome");
        this.grid.addColumn(CurrencyData::symbol).setHeader("Simbolo");
        this.grid.addColumn(new NumberRenderer<>(CurrencyData::valueLire, NumberFormat.getNumberInstance())).setHeader("Valore Lire");
        this.grid.addColumn(new NumberRenderer<>(CurrencyData::valueEuro, NumberFormat.getNumberInstance())).setHeader("Valore Euro");
        try {
            var body = req.get("/currencies");
            var data = this.mapper.readValue(body, Response.class);

            var currencies = data.getData().stream().map(e -> {
                var map = (HashMap<String, Object>) e;
                return CurrencyData.map(map);
            }).toList();
            this.grid.setItems(currencies);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }

        var addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        addButton.getElement().setAttribute("aria-label", "Aggiungi valuta");
        addButton.addClickListener(clickEvent -> {
            var dialog = new AddCurrencyDialog(this.config);
            dialog.setGrid(this.grid);
            dialog.open();
        });

        add(title, addButton, this.grid);
    }
}
