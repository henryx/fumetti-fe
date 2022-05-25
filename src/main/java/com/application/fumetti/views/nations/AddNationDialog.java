package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.data.NationResult;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class AddNationDialog extends Dialog {
    private final Configuration config;
    private Grid<NationResult> grid;

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
        return null;
    }

    private HorizontalLayout initButtons() {
        return null;
    }

    public AddNationDialog setGrid(Grid<NationResult> grid) {
        this.grid = grid;
        return this;
    }
}
