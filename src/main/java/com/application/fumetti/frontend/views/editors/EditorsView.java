package com.application.fumetti.frontend.views.editors;

import com.application.fumetti.frontend.Configuration;
import com.application.fumetti.frontend.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.quarkus.annotation.VaadinServiceScoped;

@PageTitle("Editori")
@Route(value = "/editors", layout = MainLayout.class)
public class EditorsView extends Div {
    private final Configuration config;

    @VaadinServiceScoped
    public EditorsView(Configuration config) {
        this.config = config;
        var upper = new VerticalLayout();
        var bottom = new VerticalLayout();

        var title = new H2("Editori");
        title.setVisible(true);

        var addButton = new Button(new Icon(VaadinIcon.PLUS));
        addButton.addThemeVariants(ButtonVariant.LUMO_ICON);
        addButton.getElement().setAttribute("aria-label", "Aggiungi editore");
        addButton.addClickListener(e -> addButton.getUI().ifPresent(ui -> ui.navigate("/editors/add")));

        upper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        upper.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);

        bottom.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        bottom.setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        bottom.setAlignItems(FlexComponent.Alignment.STRETCH);

        upper.add(title);
        bottom.add(addButton);
        add(upper, bottom);
    }
}