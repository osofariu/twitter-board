package org.oliver.twb;

import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.io.*;
import java.util.Arrays;
import java.util.Properties;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@RunWith(VertxUnitRunner.class)
public class TwitterVerticleTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    private Properties testProperties = mock(Properties.class);
    private TwitterClient twitterClient = mock(TwitterClient.class);
    private TwitterVerticle subject;

    @Before
    public void setUp() {
        subject = new TwitterVerticle(twitterClient);
    }

    @Test
    public void stuff(TestContext context) throws Exception {

        when(testProperties.getProperty("CONSUMER_KEY")).thenReturn("key123");
        when(testProperties.getProperty("CONSUMER_SECRET")).thenReturn("prop123");
        when(testProperties.getProperty("USER_TOKEN")).thenReturn("token123");
        when(testProperties.getProperty("USER_SECRET")).thenReturn("secret123");


        TwitterVerticle.main(new String[] {makeConfigFile()});
        subject.start(); // TODO: using vertx unit runner, but it's not set-up correctly yet

        verify(testProperties).getProperty("CONSUMER_KEY");
        verify(testProperties).getProperty("CONSUMER_SECRET");
        verify(testProperties).getProperty("USER_TOKEN");
        verify(testProperties).getProperty("USER_SECRET");
    }

    private String makeConfigFile() throws IOException {
        String configName = "/tmp/twitter.config";
        FileWriter fileWriter = new FileWriter(configName);
        fileWriter.write("CONSUMER_KEY=key123\n");
        fileWriter.write("CONSUMER_SECRET=prop123\n");
        fileWriter.write("USER_TOKEN=token123\n");
        fileWriter.write("USER_SECRET=secret123\n");
        fileWriter.close();
        return configName;
    }
}
