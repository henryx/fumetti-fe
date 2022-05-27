package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.EditorResult;
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
import java.net.URISyntaxException;

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
            this.grid.setItems(data.getData());
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
