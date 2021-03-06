package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.EditorData;
import com.application.fumetti.mappers.data.NationData;
import com.application.fumetti.utils.Notifications;
import com.application.fumetti.utils.Requests;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

public class AddEditorDialog extends Dialog {

    private final Configuration config;
    private final ObjectMapper mapper;

    private TextField nameField;
    private TextField siteField;
    private TextField websiteField;
    private NationData nationSelected;

    public AddEditorDialog(Configuration config) {
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
        this.siteField = new TextField("Sede", "", "");
        this.websiteField = new TextField("Sito Web", "", "");

        var nationsCombo = new ComboBox<NationData>("Nazione");
        try {
            var body = req.get("/nations");
            var data = this.mapper.readValue(body, Response.class);

            var nations = data.getData().stream().map(e -> {
                var map = (HashMap<String, Object>) e;
                return NationData.map(map);
            }).toList();
            nationsCombo.setItems(nations);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }

        nationsCombo.setItemLabelGenerator(NationData::name);
        nationsCombo.addValueChangeListener(e -> nationSelected = e.getValue());

        var layout = new FormLayout();
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        layout.add(this.nameField, this.siteField, this.websiteField, nationsCombo);

        return layout;
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla", e -> close());
        save.addClickListener(e -> {
            try {
                var req = new Requests(this.config);
                var payload = new EditorData(null,
                        this.nameField.getValue(),
                        this.siteField.getValue(),
                        this.websiteField.getValue(),
                        this.nationSelected);

                var body = this.mapper.writeValueAsString(payload);
                req.post("/editors", body);

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

}
