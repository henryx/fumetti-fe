package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CurrencyResult;
import com.application.fumetti.mappers.data.EditorResult;
import com.application.fumetti.mappers.data.NationResult;
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
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.HashMap;

@PageTitle("Editori")
@Route(value = "editors", layout = MainLayout.class)
public class EditorsView extends VerticalLayout {
    private final Configuration config;
    private final Grid<EditorResult> grid;
    private final ObjectMapper mapper;

    @VaadinServiceScoped
    public EditorsView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();
        var req = new Requests(this.config);

        var title = new H1("Editori");
        title.setVisible(true);


        this.grid = new Grid<>(EditorResult.class, false);
        this.grid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.grid.addColumn(EditorResult::id).setHeader("Id").setVisible(false);
        this.grid.addColumn(EditorResult::name).setHeader("Nome");
        this.grid.addColumn(EditorResult::hq).setHeader("Sede");
        this.grid.addColumn(EditorResult::website).setHeader("Sito web");

        try {
            var body = req.get("/editors");
            var data = this.mapper.readValue(body, Response.class);
            var editors = data.getData().stream().map(e -> {
                var map = (HashMap<String, Object>) e;
                var nestedNation = (HashMap<String, Object>) map.get("nation");
                var nestedCurrency = (HashMap<String, Object>) nestedNation.get("currency");

                var currency = new CurrencyResult(Long.getLong(nestedCurrency.get("id").toString()), nestedCurrency.get("name").toString(),
                        nestedCurrency.get("symbol").toString(), new BigDecimal(nestedCurrency.get("value_lire").toString()),
                        new BigDecimal(nestedCurrency.get("value_euro").toString()));
                var nation = new NationResult(Long.getLong(nestedNation.get("id").toString()), nestedNation.get("name").toString(),
                        nestedNation.get("sign").toString(), currency);
                return new EditorResult(Long.getLong(map.get("id").toString()), map.get("name").toString(), map.get("hq").toString(), map.get("website").toString(), nation);
            }).toList();
            this.grid.setItems(editors);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }
        this.grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        var addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        addButton.getElement().setAttribute("aria-label", "Aggiungi editore");
        addButton.addClickListener(clickEvent -> {
            var dialog = new AddEditorDialog(this.config);
            dialog.setGrid(this.grid);
            dialog.open();
        });

        setSpacing(false);
        add(title, addButton, this.grid);
    }
}
