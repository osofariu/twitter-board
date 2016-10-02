package org.oliver.twb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.eventbus.MessageConsumer;

public class TwitterBoardVerticle extends AbstractVerticle {

    @Override
    public void start() {
        System.out.println("TwitterBoard starting..");
        MessageConsumer<Object> tweetConsumer = vertx.eventBus().consumer("tweet", msg -> {
            System.out.println("twitter-board: " + msg.body());
            msg.reply("OK");
        });
    }

    @Override
    public void stop(Future<Void> voidFuture) {
        voidFuture.complete();
    }
}
