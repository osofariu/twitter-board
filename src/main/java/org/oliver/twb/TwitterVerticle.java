package org.oliver.twb;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import static java.util.Arrays.asList;

class TwitterVerticle extends AbstractVerticle {

    Properties properties;
    TwitterClient twitterClient;
    Vertx vertx;

    TwitterVerticle(TwitterClient twitterClient, Vertx vertx) {
        this.vertx = vertx;
        this.twitterClient = twitterClient;
    }

    //@Override
    @SuppressWarnings("unchecked")
    public void start() throws Exception {
        vertx.deployVerticle(TwitterBoardVerticle.class.getName(), res -> {
            System.out.println("Success starting TwitterBoardVerticle " + res.succeeded());
        });
        vertx.executeBlocking(future -> {
            try {
                twitterClient.trackTerms(asList("ios"));
                twitterClient.connect();
                twitterClient.processMessages(processTweet());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            future.complete();
        }, res -> {
            if (res.failed()) {
                System.out.println("Failed to execute blocking code");
                System.out.println(res.cause());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private Function<String, String> processTweet() {
        return msg -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String, Object> tweet = mapper.readValue(msg, Map.class);
                String body = tweet.get("text").toString().substring(0, Math.min(60, tweet.get("text").toString().length()));
                vertx.eventBus().send("tweet", "id: " + tweet.get("id") + " At: " + tweet.get("created_at") + " <<" + body + "...>>", res -> {
                    if (res.failed()) System.out.println(res.cause());
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return msg;
        };
    }


    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: TwitterVerticle <configFilename>");
        }

        Properties properties = getProperties(args[0]);

        TwitterClient twitterClient = new TwitterClient(properties.getProperty("CONSUMER_KEY"),
                properties.getProperty("CONSUMER_SECRET"),
                properties.getProperty("USER_TOKEN"),
                properties.getProperty("USER_SECRET"));

        TwitterVerticle client = new TwitterVerticle(twitterClient, Vertx.vertx());

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(client);
    }

    private static Properties getProperties(String propertiesFileName) throws IOException {
        InputStream configStream = new FileInputStream(propertiesFileName);
        Properties properties = new Properties();
        properties.load(configStream);
        return properties;
    }
}
