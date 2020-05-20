package com.github.igocoelho.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class SimpleServerVerticle extends AbstractVerticle {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new SimpleServerVerticle());
    }

    @Override
    public void start() {
        vertx.createHttpServer()
                .requestHandler(r -> r.response().end("Welcome to Vert.x Intro"))
                .listen(config().getInteger("http.port", 8080));
    }

}