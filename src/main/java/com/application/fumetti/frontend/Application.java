package com.application.fumetti.frontend;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Theme(value = "fumetti", variant = Lumo.DARK)
@PWA(name = "Fumetti", shortName = "Fumetti", offlineResources = {})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application implements AppShellConfigurator {
}
