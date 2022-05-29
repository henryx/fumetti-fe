package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CurrencyData;
import com.application.fumetti.mappers.data.NationData;
import com.application.fumetti.utils.Notifications;
import com.application.fumetti.utils.Requests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class AddNationDialog extends Dialog {
    private final Configuration config;
    private final ObjectMapper mapper;

    private Grid<NationData> grid;
    private TextField nameField;
    private TextField signField;
    private CurrencyData currencySelected;

    public AddNationDialog(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();

        var vl = new VerticalLayout();
        var formLayout = initForm();

        var buttonLayout = this.initButtons();

        vl.setPadding(false);
        vl.setAlignItems(FlexComponent.Alignment.STRETCH);
        vl.add(formLayout, buttonLayout);

        add(vl);
    }

    private FormLayout initForm() {
        var req = new Requests(this.config);

        this.nameField = new TextField("Nome", "", "");
        this.signField = new TextField("Sigla", "", "");
        var currenciesCombo = new ComboBox<CurrencyData>("Valuta");
        try {
            var body = req.get("/currencies");
            var data = this.mapper.readValue(body, Response.class);
            var currencies = data.getData().stream().map(ie -> {
                var map = (HashMap<String, Object>) ie;
                return CurrencyData.map(map);
            }).toList();
            currenciesCombo.setItems(currencies);

            currenciesCombo.setItemLabelGenerator(CurrencyData::name);
            currenciesCombo.addValueChangeListener(e -> currencySelected = e.getValue());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }

        var layout = new FormLayout();
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        layout.add(this.nameField, this.signField, currenciesCombo);
        layout.setColspan(this.nameField, 2);

        return layout;
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla", e -> close());
        save.addClickListener(e -> {
            try {
                var req = new Requests(this.config);
                var payload = new NationData(null,
                        this.nameField.getValue(),
                        this.signField.getValue(),
                        this.currencySelected);
                var body = this.mapper.writeValueAsString(payload);
                req.post("/nations", body);

                var items = req.get("/nations");
                var data = this.mapper.readValue(items, Response.class);
                var nations = data.getData().stream().map(ie -> {
                    var map = (HashMap<String, Object>) ie;
                    return NationData.map(map);
                }).toList();
                this.grid.setItems(nations);
                this.grid.getDataProvider().refreshAll();

                close();
            } catch (URISyntaxException | IOException | InterruptedException ex) {
                Notifications.error(ex);
            }
        });

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.getStyle().set("margin-inline-end", "auto");

        var layout = new HorizontalLayout();
        layout.add(save, cancel);

        return layout;
    }

    public AddNationDialog setGrid(Grid<NationData> grid) {
        this.grid = grid;
        return this;
    }
}
