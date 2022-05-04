package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.EditorsResponse;
import com.application.fumetti.mappers.data.EditorResult;
import com.application.fumetti.utils.Requests;
import com.application.fumetti.views.MainLayout;
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

    @VaadinServiceScoped
    public EditorsView(Configuration config) {
        this.config = config;
        var req = new Requests(this.config);

        var title = new H1("Editori");
        title.setVisible(true);

        var grid = new Grid<>(EditorResult.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(EditorResult::getId).setHeader("Id").setVisible(false);
        grid.addColumn(EditorResult::getName).setHeader("Nome");
        grid.addColumn(EditorResult::getSite).setHeader("Sede");
        grid.addColumn(EditorResult::getWebsite).setHeader("Sito web");

        try {
            var body = req.get("/editors");
            var data = EditorsResponse.map(body);
            grid.setItems(data.getData());
        } catch (URISyntaxException | IOException | InterruptedException ignored) {
        }
        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT);

        var addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        addButton.getElement().setAttribute("aria-label", "Aggiungi editore");
        addButton.addClickListener(clickEvent -> {
            var dialog = new AddEditorDialog();
            dialog.open();
        });

        var vl = new VerticalLayout();
        vl.setAlignSelf(Alignment.END, addButton);

        vl.add(addButton);
        add(title, grid, vl);
    }
}
