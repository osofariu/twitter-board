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

@RunWith(VertxUnitRunner.class)
public class SampleVerticleTest {

    Vertx vertx;

    @Rule
    public RunTestOnContext rule = new RunTestOnContext(Vertx::vertx);

     private MessageConsumer<Object> tweetConsumer;

    @Before
    public void setUp(TestContext testContext) {
        vertx = rule.vertx();
        tweetConsumer = vertx.eventBus().consumer("tweet").handler( msg -> {
            testContext.assertEquals("tweet body", msg.body());
        }).exceptionHandler(msg -> {
            System.out.println("HELL");
            System.out.println("MSG **** " + msg.getMessage());
        });
        vertx.deployVerticle(SampleVerticle.class.getName(), testContext.asyncAssertSuccess());
    }

    @After
    public void tearDown(TestContext context) {
        tweetConsumer.unregister();
        System.out.println("td: before close");
        vertx.close(context.asyncAssertSuccess());
        System.out.println("td: after close");
    }

    @Test
    public void shouldReadMessageFromBus(TestContext context) {
    }
}
