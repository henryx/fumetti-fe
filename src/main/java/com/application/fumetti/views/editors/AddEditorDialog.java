package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.data.NationResult;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AddEditorDialog extends Dialog {

    private final Configuration config;
    private NationResult nationSelected;

    public AddEditorDialog(Configuration config) {
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
        var firstNameField = new TextField("Nome", "", "");
        var lastNameField = new TextField("Sede", "", "");
        var websiteField = new TextField("Sito Web", "", "");

        var nationsCombo = new ComboBox<NationResult>("Nazione");
        nationsCombo.setItemLabelGenerator(NationResult::getName);
        nationsCombo.addValueChangeListener(e -> nationSelected = e.getValue());


        var layout = new FormLayout();
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        layout.add(firstNameField, lastNameField, websiteField, nationsCombo);

        return layout;
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla", e -> close());

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        save.getStyle().set("margin-inline-end", "auto");

        var layout = new HorizontalLayout();
        layout.add(save, cancel);

        return layout;
    }
}
