package org.oliver.twb;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Function;

public class TwitterClient {
    BlockingQueue<String> msgQueue;
    Hosts hosts;
    StatusesFilterEndpoint endpoint;
    Authentication auth;
    Client client;

    private String consumerKey;
    private String consumerSecret;
    private String userToken;
    private String userSecret;

    public TwitterClient() {

    }

    public TwitterClient(String consumerKey, String consumerSecret, String token, String secret) {

        msgQueue = new LinkedBlockingQueue<String>(1000);
        hosts = new HttpHosts(Constants.STREAM_HOST);
        endpoint = new StatusesFilterEndpoint();
        auth = new OAuth1(consumerKey, consumerSecret, token, secret);
    }

    public void trackTerms(List<String> terms) {
        endpoint.trackTerms(terms);
    }

    public void connect() {
        ClientBuilder builder = new ClientBuilder()
                .name("Vertx-Twitter-Client")                              // optional: mainly for the logs
                .hosts(hosts)
                .authentication(auth)
                .endpoint(endpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        client = builder.build();
        client.connect();
    }

    public void processMessages(Function<String,String> proc) throws InterruptedException {
        while (!client.isDone()) {
            String msg = msgQueue.take();
            proc.apply(msg);
        }
    }
}

