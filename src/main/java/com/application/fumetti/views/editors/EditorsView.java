package com.application.fumetti.views.editors;

import com.application.fumetti.Configuration;
import com.application.fumetti.mappers.EditorsResponse;
import com.application.fumetti.mappers.data.EditorResult;
import com.application.fumetti.views.MainLayout;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@PageTitle("Editori")
@Route(value = "editors", layout = MainLayout.class)
public class EditorsView extends VerticalLayout {
    private final Configuration config;

    @VaadinServiceScoped
    public EditorsView(Configuration config) {
        this.config = config;

        H1 title = new H1("Editori");
        title.setVisible(true);

        Grid<EditorResult> grid = new Grid<>(EditorResult.class, false);
        grid.addColumn(EditorResult::getName).setHeader("Nome");
        grid.addColumn(EditorResult::getSite).setHeader("Sede");
        grid.addColumn(EditorResult::getWebsite).setHeader("Sito web");

        try {
            var data = this.fetch();
            grid.setItems(data.getData());
        } catch (URISyntaxException | IOException | InterruptedException ignored) {
        }

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

    private EditorsResponse fetch() throws URISyntaxException, IOException, InterruptedException {
        try {
            var uri = new URI(this.config.proto(),
                    null, this.config.host(), this.config.port(),
                    "/editors", null, null);

            var client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            var request = HttpRequest.newBuilder()
                    .GET()
                    .uri(uri)
                    .setHeader("Accept", "application/json")
                    .build();

            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new HttpResponseException(response.statusCode(), "Error in connection");
            }

            return EditorsResponse.map(response.body());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            Notification notification = new Notification();
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

            Div text = new Div(new Text("Failed to retrieve data: " + e.getMessage()));

            Button closeButton = new Button(new Icon("lumo", "cross"));
            closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
            closeButton.getElement().setAttribute("aria-label", "Close");
            closeButton.addClickListener(event -> {
                notification.close();
            });

            HorizontalLayout layout = new HorizontalLayout(text, closeButton);
            layout.setAlignItems(Alignment.CENTER);

            notification.add(layout);
            notification.open();
            notification.setPosition(Notification.Position.MIDDLE);

            throw e;
        }
    }
}
