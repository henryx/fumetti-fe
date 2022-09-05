package com.application.fumetti.frontend.views.series;

import com.application.fumetti.frontend.Configuration;
import com.application.fumetti.frontend.mappers.Response;
import com.application.fumetti.frontend.mappers.data.EditorData;
import com.application.fumetti.frontend.mappers.data.lookup.series.GenreData;
import com.application.fumetti.frontend.utils.Notifications;
import com.application.fumetti.frontend.utils.Requests;
import com.application.fumetti.frontend.views.MainLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@PageTitle("Aggiungi Serie")
@Route(value = "/series/add", layout = MainLayout.class)
public class AddSeriesView extends Div {
    private final Configuration config;
    private final ObjectMapper mapper;
    private GenreData genreSelected;
    private EditorData editorSelected;

    public AddSeriesView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();

        var upper = new VerticalLayout();
        var center = new VerticalLayout();
        var bottom = new VerticalLayout();

        upper.setSpacing(false);
        center.setSpacing(false);
        bottom.setSpacing(false);

        var title = new H2("Aggiungi serie");

        var name = new TextField("Nome");
        var editorCombo = new ComboBox<EditorData>("Editore");
        var genreCombo = new ComboBox<GenreData>("Genere");

        try {
            editorCombo.setItems(fetchData("/editors").stream().map(ie -> {
                var map = (HashMap<String, Object>) ie;
                return EditorData.map(map);
            }).toList());

            genreCombo.setItems(fetchData("/series/genre").stream().map(ie -> {
                var map = (HashMap<String, Object>) ie;
                return GenreData.map(map);
            }).toList());

            editorCombo.setItemLabelGenerator(EditorData::name);
            editorCombo.addValueChangeListener(e -> editorSelected = e.getValue());

            genreCombo.setItemLabelGenerator(GenreData::description);
            genreCombo.addValueChangeListener(e -> genreSelected = e.getValue());

        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Notifications.error(ex);
        }

        upper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        upper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        center.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        center.setAlignItems(FlexComponent.Alignment.STRETCH);

        upper.add(title);
        center.add(name, editorCombo, genreCombo);
        add(upper, center, bottom);
    }

    private List fetchData(String endpoint) throws IOException, URISyntaxException, InterruptedException {
        var req = new Requests(this.config);
        var body = req.get(endpoint);
        var data = this.mapper.readValue(body, Response.class);

        return data.getData();
    }
}
