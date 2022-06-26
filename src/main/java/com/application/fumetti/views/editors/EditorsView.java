package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.Response;
import com.application.fumetti.mappers.data.EditorData;
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
import java.util.HashMap;

@PageTitle("Editori")
@Route(value = "editors", layout = MainLayout.class)
public class EditorsView extends VerticalLayout {
    private final Configuration config;
    private final Grid<EditorData> grid;
    private final ObjectMapper mapper;

    @VaadinServiceScoped
    public EditorsView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();
        var req = new Requests(this.config);

        var title = new H1("Editori");
        title.setVisible(true);


        this.grid = new Grid<>(EditorData.class, false);
        this.grid.setSelectionMode(Grid.SelectionMode.MULTI);
        this.grid.addColumn(EditorData::id).setHeader("Id").setVisible(false);
        this.grid.addColumn(EditorData::name).setHeader("Nome");
        this.grid.addColumn(EditorData::hq).setHeader("Sede");
        this.grid.addColumn(EditorData::website).setHeader("Sito web");

        try {
            var body = req.get("/editors");
            var data = this.mapper.readValue(body, Response.class);
            var editors = data.getData().stream().map(e -> {
                var map = (HashMap<String, Object>) e;
                return EditorData.map(map);
            }).toList();
            this.grid.setItems(editors);
        } catch (URISyntaxException | IOException | InterruptedException e) {
            Notifications.error(e);
        }
        this.grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        var addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        addButton.getElement().setAttribute("aria-label", "Aggiungi editore");
        addButton.addClickListener(clickEvent -> {
            var dialog = new AddEditorDialog(this.config);
            dialog.addOpenedChangeListener(event -> {
                if (dialog.isOpened()) {
                    return;
                }

                try {
                    var items = req.get("/editors");
                    var data = this.mapper.readValue(items, Response.class);
                    var editors = data.getData().stream().map(ie -> {
                        var map = (HashMap<String, Object>) ie;
                        return EditorData.map(map);
                    }).toList();
                    this.grid.setItems(editors);
                    this.grid.getDataProvider().refreshAll();
                } catch (URISyntaxException | IOException | InterruptedException ex) {
                    Notifications.error(ex);
                }
            });
            dialog.open();
        });

        setSpacing(false);
        add(title, addButton, this.grid);
    }
}
