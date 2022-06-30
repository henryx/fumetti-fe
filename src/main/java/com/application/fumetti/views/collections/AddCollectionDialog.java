package com.application.fumetti.views.collections;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.CollectionData;
import com.application.fumetti.mappers.data.EditorData;
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

public class AddCollectionDialog extends Dialog {

    private final Configuration config;
    private final ObjectMapper mapper;

    private TextField nameField;
    private EditorData editorSelected;

    public AddCollectionDialog(Configuration config) {
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

        var editorCombo = new ComboBox<EditorData>("Editore");
        try {
            var body = req.get("/editors");
            var data = this.mapper.readValue(body, Response.class);

            var editors = data.getData().stream().map(e -> {
                var map = (HashMap<String, Object>) e;
                return EditorData.map(map);
            }).toList();
            editorCombo.setItems(editors);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }

        editorCombo.setItemLabelGenerator(EditorData::name);
        editorCombo.addValueChangeListener(e -> editorSelected = e.getValue());

        var layout = new FormLayout();
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        layout.add(this.nameField, editorCombo);

        return layout;
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla", e -> close());
        save.addClickListener(e -> {
            try {
                var req = new Requests(this.config);
                var payload = new CollectionData(null,
                        this.nameField.getValue(),
                        this.editorSelected);

                var body = this.mapper.writeValueAsString(payload);
                req.post("/collections", body);

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
