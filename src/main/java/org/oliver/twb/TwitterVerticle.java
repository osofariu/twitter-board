package org.oliver.twb;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import static java.util.Arrays.asList;

class TwitterVerticle extends AbstractVerticle {

    TwitterClient twitterClient;

    TwitterVerticle(TwitterClient twitterClient) {
        this.twitterClient = twitterClient;
    }

    //@Override
    public void start() throws Exception {
        twitterClient.processMessages(msg -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                Map<String,Object> tweet = mapper.readValue(msg, Map.class);
                System.out.println("id: " + tweet.get("id") + " At: " + tweet.get("created_at") + " <<" + tweet.get("text") + ">>");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return msg;
        });
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: TwitterVerticle configFilename");
        }
        String configFile = args[0];
        InputStream configStream = new FileInputStream(configFile);
        Properties properties = new Properties();
        properties.load(configStream);

        TwitterClient twitterClient = new TwitterClient(properties.getProperty("CONSUMER_KEY"),
                                                        properties.getProperty("CONSUMER_SECRET"),
                                                        properties.getProperty("USER_TOKEN"),
                                                        properties.getProperty("USER_SECRET"));
        twitterClient.trackTerms(asList("ios"));
        twitterClient.connect();

        TwitterVerticle client = new TwitterVerticle(twitterClient);
        client.start();
    }
}
