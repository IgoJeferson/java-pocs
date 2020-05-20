package com.github.igocoelho.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.client.predicate.ResponsePredicate;
import io.vertx.ext.web.codec.BodyCodec;

/**
 * Performing HTTP requests to other services
 * a Vert.x application that use the API at https://icanhazdadjoke.com/api and displays a new joke every 3 seconds:
 */
public class JokeVerticle extends AbstractVerticle {

    private HttpRequest<JsonObject> request;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new JokeVerticle());
    }

    @Override
    public void start() {

        // Get a WebClient attached to the current Vert.x instance.
        request = WebClient.create(vertx)
                // HTTP GET request for path / to host icanhazdadjoke.com, on port 443 (HTTPS).
                .get(443, "icanhazdadjoke.com", "/")
                //  to enable SSL encryption
                .ssl(true)
                // Explicitly say that we want JSON data.
                .putHeader("Accept", "application/json")
                // The response will automatically be converted to JSON
                .as(BodyCodec.jsonObject())
                // We expect a HTTP 200 status code, else it will fail the response.
                .expect(ResponsePredicate.SC_OK);

        vertx.setPeriodic(3000, id -> fetchJoke());
    }

    private void fetchJoke() {
        request.send(asyncResult -> {
            if (asyncResult.succeeded()) {
                // The body is a JSON object, and we write the result to the console
                System.out.println(asyncResult.result().body().getString("joke"));
                System.out.println("🤣");
                System.out.println();
            }
        });
    }

}