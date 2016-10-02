package org.oliver.twb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.DeliveryOptions;

public class SampleVerticle extends AbstractVerticle {

    @Override
    public void start() {
        System.out.println("Starting verticle " + SampleVerticle.class.getName());
        vertx.eventBus().send("tweet", "tweet body", reply -> {
            System.out.println("GOT IT");
        });
    }

    @Override
    public void stop(Future<Void> stopFuture) {
        System.out.println("COMPLETE");
        stopFuture.complete();
    }
}
