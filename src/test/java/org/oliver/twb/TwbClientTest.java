package org.oliver.twb;

import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import java.util.Properties;

import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

@RunWith(VertxUnitRunner.class)
public class TwbClientTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    private Properties testProperties = mock(Properties.class);
    private TwitterClient twitterClient = mock(TwitterClient.class);
    private TwbClient subject;

    @Before
    public void setUp() {
        subject = new TwbClient(testProperties, twitterClient);
    }

    @Test
    public void givenTwitterClientWithPropertiesMakeSureTwitterPropsArePresent(TestContext context) throws Exception {

        when(testProperties.getProperty("CONSUMER_KEY")).thenReturn("key123");
        when(testProperties.getProperty("CONSUMER_SECRET")).thenReturn("prop123");
        when(testProperties.getProperty("USER_TOKEN")).thenReturn("token123");
        when(testProperties.getProperty("USER_SECRET")).thenReturn("secret123");

        subject.start();

        ArgumentCaptor<String> arg1Captor = forClass(String.class);
        ArgumentCaptor<String> arg2Captor = forClass(String.class);
        ArgumentCaptor<String> arg3Captor = forClass(String.class);
        ArgumentCaptor<String> arg4Captor = forClass(String.class);

        verify(twitterClient).connect(arg1Captor.capture(), arg2Captor.capture(), arg3Captor.capture(), arg4Captor.capture());
        context.assertEquals("key123", arg1Captor.getValue());
        context.assertEquals("prop123", arg2Captor.getValue());
        context.assertEquals("token123", arg3Captor.getValue());
        context.assertEquals("secret123", arg4Captor.getValue());
    }
}
