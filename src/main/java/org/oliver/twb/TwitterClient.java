package org.oliver.twb;

public class TwitterClient {
    private String consumerKey;
    private String consumerSecret;
    private String userToken;
    private String userSecret;

    public void connect(String consumerKey, String consumerSecret, String userToken, String userSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.userToken = userToken;
        this.userSecret = userSecret;
    }
}
