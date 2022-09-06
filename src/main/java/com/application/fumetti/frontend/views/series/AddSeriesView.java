package com.application.fumetti.frontend.views.series;

import com.application.fumetti.frontend.Configuration;
import com.application.fumetti.frontend.mappers.Response;
import com.application.fumetti.frontend.mappers.data.EditorData;
import com.application.fumetti.frontend.mappers.data.SeriesData;
import com.application.fumetti.frontend.mappers.data.lookup.series.FrequencyData;
import com.application.fumetti.frontend.mappers.data.lookup.series.GenreData;
import com.application.fumetti.frontend.mappers.data.lookup.series.StatusData;
import com.application.fumetti.frontend.utils.Notifications;
import com.application.fumetti.frontend.utils.Requests;
import com.application.fumetti.frontend.views.MainLayout;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
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
    private final TextArea noteArea;
    private final TextField name;
    private EditorData editorSelected;
    private FrequencyData frequencySelected;
    private GenreData genreSelected;
    private StatusData statusSelected;

    public AddSeriesView(Configuration config) {
        this.config = config;
        this.mapper = new ObjectMapper();

        var upper = new VerticalLayout();
        var center = new VerticalLayout();
        var bottom = new VerticalLayout();

        upper.setSpacing(false);
        bottom.setSpacing(false);

        upper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        upper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        center.setSpacing(false);
        center.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        center.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        center.setAlignItems(FlexComponent.Alignment.STRETCH);

        var title = new H2("Aggiungi serie");

        this.name = new TextField("Nome");
        var editorCombo = new ComboBox<EditorData>("Editore");
        var genreCombo = new ComboBox<GenreData>("Genere");
        var frequencyCombo = new ComboBox<FrequencyData>("Periodicità");
        var statusCombo = new ComboBox<StatusData>("Stato");
        this.noteArea = new TextArea("Note");

        try {
            editorCombo.setItems(fetchData("/api/v1/editors").stream().map(ie -> {
                var map = (HashMap<String, Object>) ie;
                return EditorData.map(map);
            }).toList());

            genreCombo.setItems(fetchData("/api/v1/series/genre").stream().map(ie -> {
                var map = (HashMap<String, Object>) ie;
                return GenreData.map(map);
            }).toList());

            frequencyCombo.setItems(fetchData("/api/v1/series/frequencies").stream().map(ie -> {
                var map = (HashMap<String, Object>) ie;
                return FrequencyData.map(map);
            }).toList());

            statusCombo.setItems(fetchData("/api/v1/series/status").stream().map(ie -> {
                        var map = (HashMap<String, Object>) ie;
                        return StatusData.map(map);
                    }).toList()
            );

            editorCombo.setItemLabelGenerator(EditorData::name);
            editorCombo.addValueChangeListener(e -> editorSelected = e.getValue());

            genreCombo.setItemLabelGenerator(GenreData::description);
            genreCombo.addValueChangeListener(e -> genreSelected = e.getValue());

            frequencyCombo.setItemLabelGenerator(FrequencyData::description);
            frequencyCombo.addValueChangeListener(e -> frequencySelected = e.getValue());

            statusCombo.setItemLabelGenerator(StatusData::description);
            statusCombo.addValueChangeListener(e -> statusSelected = e.getValue());

            upper.add(title);
            center.add(this.name, editorCombo, genreCombo, frequencyCombo, statusCombo, this.noteArea);
            bottom.add(this.initButtons());
        } catch (URISyntaxException | IOException | InterruptedException ex) {
            Notifications.error(ex);
        }

        add(upper, center, bottom);
    }

    private HorizontalLayout initButtons() {
        var save = new Button("Salva");
        var cancel = new Button("Annulla");
        cancel.addClickListener(e -> cancel.getUI().ifPresent(ui -> ui.navigate("/series")));
        save.addClickListener(e -> {
            try {
                var req = new Requests(this.config);
                var payload = new SeriesData(null,
                        this.name.getValue(),
                        this.genreSelected,
                        this.editorSelected,
                        this.frequencySelected,
                        this.statusSelected,
                        this.noteArea.getValue());

                var body = this.mapper.writeValueAsString(payload);
                req.post("/api/v1/series", body);

                Notifications.success("Inserted series successfully");
                save.getUI().ifPresent(ui -> ui.navigate("/series"));
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

    private List fetchData(String endpoint) throws IOException, URISyntaxException, InterruptedException {
        var req = new Requests(this.config);
        var body = req.get(endpoint);
        var data = this.mapper.readValue(body, Response.class);

        return data.getData();
    }
}
