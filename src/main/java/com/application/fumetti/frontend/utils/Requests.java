package com.application.fumetti.frontend.utils;

import com.application.fumetti.frontend.Configuration;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Requests {
    private final Configuration config;
    private final HttpClient client;

    public Requests(Configuration config) {
        this.config = config;

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public String get(String path) throws URISyntaxException, IOException, InterruptedException {
        var uri = new URI(this.config.proto(),
                null, this.config.host(), this.config.port(),
                path, null, null);

        var request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .setHeader("Accept", "application/json")
                .build();

        var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpResponseException(response.statusCode(), "Error in connection");
        }

        return response.body();
    }

    public String post(String path, String body) throws URISyntaxException, IOException, InterruptedException {
        var uri = new URI(this.config.proto(),
                null, this.config.host(), this.config.port(),
                path, null, null);

        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .uri(uri)
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .build();

        var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new HttpResponseException(response.statusCode(), "Error in connection: " + response.body());
        }

        return response.body();
    }
}
