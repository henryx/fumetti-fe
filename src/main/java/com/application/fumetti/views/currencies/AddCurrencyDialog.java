package com.application.fumetti.views.currencies;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.data.CurrencyData;
import com.application.fumetti.utils.Notifications;
import com.application.fumetti.utils.Requests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

public class AddCurrencyDialog extends Dialog {

    private final Configuration config;
    private final ObjectMapper mapper;

    private TextField nameField;
    private TextField symbolField;
    private NumberField valueLire;
    private NumberField valueEuro;

    public AddCurrencyDialog(Configuration config) {
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

        this.nameField = new TextField("Nome");
        this.symbolField = new TextField("Simbolo");
        this.valueLire = new NumberField();
        this.valueEuro = new NumberField();

        this.valueLire.setLabel("Valore lire");
        this.valueEuro.setLabel("Valore Euro");

        var layout = new FormLayout();
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        layout.add(this.nameField, this.symbolField, this.valueLire, this.valueEuro);

        return layout;
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla", e -> super.close());
        save.addClickListener(e -> {
            try {
                var req = new Requests(this.config);
                var payload = new CurrencyData(null,
                        this.nameField.getValue(),
                        this.symbolField.getValue(),
                        BigDecimal.valueOf(this.valueLire.getValue()),
                        BigDecimal.valueOf(this.valueEuro.getValue()));

                var body = this.mapper.writeValueAsString(payload);
                req.post("/currencies", body);

                super.close();
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
}
