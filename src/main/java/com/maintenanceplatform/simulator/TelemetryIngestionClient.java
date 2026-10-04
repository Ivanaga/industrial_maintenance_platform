package com.maintenanceplatform.simulator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Locale;

// Sends generated telemetry readings to the maintenance platform ingestion API.
public class TelemetryIngestionClient 
{
    private final HttpClient httpClient;
    private final String baseUrl;

    public TelemetryIngestionClient(String baseUrl) 
    {
        this.httpClient = HttpClient.newHttpClient();
        this.baseUrl = baseUrl;
    }

    public void sendReading(Long sensorId, double value, Instant timestamp) throws IOException, InterruptedException 
    {
        String json = String.format(Locale.ROOT,
            """
            {
            "sensorId": %d,
            "value": %f,
            "timestamp": "%s"
            }
            """, sensorId, value, timestamp);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + "/api/sensor-readings")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(json)).build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 400) 
        {
            throw new IllegalStateException("Failed to send telemetry. Status: " + response.statusCode() + ", body: " + response.body());
        }
    }
}