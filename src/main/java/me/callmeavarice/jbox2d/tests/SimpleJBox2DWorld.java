package me.callmeavarice.jbox2d.tests;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.testbed.framework.TestbedTest;

public class SimpleJBox2DWorld extends TestbedTest {
    @Override
    public void initTest(boolean b) {
        Vec2 gravity = new Vec2(0.0f, -10.0f);
        boolean doSleep = true;

        World world = getWorld();
        world.setAllowSleep(doSleep);
        world.setGravity(gravity);

        // Ground body
        {
            BodyDef bd = new BodyDef();
            Body ground = getWorld().createBody(bd);

            EdgeShape shape = new EdgeShape();
            shape.set(new Vec2(-40.0f, 0.0f), new Vec2(40.0f, 0.0f));
            ground.createFixture(shape, 0.0f);
        }

        {
            BodyDef bd = new BodyDef();
            bd.type = BodyType.DYNAMIC;
            bd.position.set(0.0f, 40.0f);
            Body m_body1 = getWorld().createBody(bd);

//            PolygonShape m_shape1 = new PolygonShape();
//            m_shape1.setAsBox(0.5f, 0.5f, new Vec2(-0.5f, 0.0f), 0.0f);
            CircleShape m_shape1 = new CircleShape();
            m_shape1.m_radius = 0.5f;
            Fixture m_piece1 = m_body1.createFixture(m_shape1, 1.0f);
        }
    }

    @Override
    public String getTestName() {
        return "Simple World";
    }
}
