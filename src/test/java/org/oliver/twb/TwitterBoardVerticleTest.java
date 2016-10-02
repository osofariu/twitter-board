package org.oliver.twb;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class TwitterBoardVerticleTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext(Vertx::vertx);

    Vertx vertx;

    @Before
    public void init(TestContext context) {
        vertx = rule.vertx();
        vertx.deployVerticle(TwitterBoardVerticle.class.getName());
    }

    @Test
    public void testMe() {
        //
    }
}
