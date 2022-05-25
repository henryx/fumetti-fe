package com.application.fumetti.views.nations;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.data.NationResult;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;

public class AddNationDialog extends Dialog {
    private final Configuration config;
    private Grid<NationResult> grid;

    public AddNationDialog(Configuration config) {
        this.config = config;
    }

    public AddNationDialog setGrid(Grid<NationResult> grid) {
        this.grid = grid;
        return this;
    }
}
