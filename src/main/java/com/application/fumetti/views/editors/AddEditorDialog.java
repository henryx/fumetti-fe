package com.application.fumetti.views.editors;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;

public class AddEditorDialog extends Dialog {

    public AddEditorDialog() {
        var vl = new VerticalLayout();
        var formLayout = initForm();

        var buttonLayout = this.initButtons();

        vl.setPadding(false);
        vl.setAlignItems(FlexComponent.Alignment.STRETCH);
        vl.add(formLayout, buttonLayout);

        add(vl);
    }

    private FormLayout initForm() {
        var firstNameField = new TextField("First name", "John", "");
        var lastNameField = new TextField("Last name", "Smith", "");
        var emailField = new EmailField("Email address");
        emailField.setValue("john.smith@example.com");

        var layout = new FormLayout();
        layout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 2));
        layout.setColspan(emailField, 2);
        layout.add(firstNameField, lastNameField, emailField);

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
