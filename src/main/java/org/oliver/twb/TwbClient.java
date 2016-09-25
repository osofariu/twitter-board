package org.oliver.twb;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

import java.util.Properties;

class TwbClient extends AbstractVerticle {

    Properties properties;
    TwitterClient twitterClient;

    TwbClient(Properties properties, TwitterClient twitterClient) {
        this.properties = properties;
        this.twitterClient = twitterClient;
    }

    @Override
    public void start() throws Exception {

        twitterClient.connect(properties.getProperty("CONSUMER_KEY"),
                            properties.getProperty("CONSUMER_SECRET"),
                            properties.getProperty("USER_TOKEN"),
                            properties.getProperty("USER_SECRET"));
    }
}
