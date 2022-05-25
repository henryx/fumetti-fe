package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.data.NationResult;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AddNationDialog extends Dialog {
    private final Configuration config;
    private Grid<NationResult> grid;
    private TextField nameField;
    private TextField signField;

    public AddNationDialog(Configuration config) {
        this.config = config;
        var vl = new VerticalLayout();
        var formLayout = initForm();

        var buttonLayout = this.initButtons();

        vl.setPadding(false);
        vl.setAlignItems(FlexComponent.Alignment.STRETCH);
        vl.add(formLayout, buttonLayout);

        add(vl);
    }

    private FormLayout initForm() {
        this.nameField = new TextField("Nome", "", "");
        this.signField = new TextField("Sigla", "", "");

        var layout = new FormLayout();
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        layout.add(this.nameField, this.signField);

        return layout;
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla", e -> close());
        save.addClickListener(e -> {
        });

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.getStyle().set("margin-inline-end", "auto");

        var layout = new HorizontalLayout();
        layout.add(save, cancel);

        return layout;
    }

    public AddNationDialog setGrid(Grid<NationResult> grid) {
        this.grid = grid;
        return this;
    }
}
