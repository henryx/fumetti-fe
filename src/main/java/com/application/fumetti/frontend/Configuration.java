package com.application.fumetti.frontend;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;

@ConfigMapping(prefix = "backend")
public interface Configuration {
    String host();

    @WithDefault("80")
    int port();

    String proto();
}
