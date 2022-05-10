package com.application.fumetti.utils;

import com.application.fumetti.Configuration;
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
        try {
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
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw e;
        }
    }
}
