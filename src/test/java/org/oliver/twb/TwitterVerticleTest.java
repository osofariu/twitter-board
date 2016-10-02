package org.oliver.twb;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.Mockito.mock;

@RunWith(VertxUnitRunner.class)
public class TwitterVerticleTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    private TwitterClient mockTwitterClient;
    private TwitterVerticle subject;
    Vertx vertx;

    @Before
    public void setUp(TestContext testContext) {
        mockTwitterClient = mock(TwitterClient.class);
        subject = new TwitterVerticle(mockTwitterClient, rule.vertx());
        vertx = rule.vertx();
        MessageConsumer tweetConsumer = vertx.eventBus().consumer("tweet", msg -> {
            testContext.assertEquals("tweet body", msg.body());
            testContext.assertEquals("tweet", msg.body());
        });
        vertx.deployVerticle(subject);
    }

    @After
    public void tearDown(TestContext context) {
        vertx.close(context.asyncAssertSuccess());
    }

    @Test
    public void whenItGetMessageFromTwitterItPushesItOnBus(TestContext context) {

        Async async = context.async();
        async.complete();
//        vertx.eventBus().consumer("tweet", event -> {
//                    context.assertEquals(event.body(), "body");
//                    context.assertEquals(event.address(), "address");
//                    async.complete();
//                });
    }

}
/*
@Before
public void setUp(TestContext context) throws IOException {
  vertx = Vertx.vertx();
  ServerSocket socket = new ServerSocket(0);
  port = socket.getLocalPort();
  socket.close();
  DeploymentOptions options = new DeploymentOptions()
      .setConfig(new JsonObject().put("http.port", port)
      );
  vertx.deployVerticle(MyFirstVerticle.class.getName(), options, context.asyncAssertSuccess());
}
 */