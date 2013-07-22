package me.callmeavarice.jbox2d.tests;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.testbed.framework.TestbedTest;

import java.util.Random;

public class PuttingBounceInTheMoshPit extends TestbedTest {
    @Override
    public void initTest(boolean b) {
        Vec2 gravity = new Vec2(0.0f, -10.0f);
        boolean doSleep = true;

        World world = getWorld();
        world.setAllowSleep(doSleep);
        world.setGravity(gravity);

        // Environment body
        {
            BodyDef bd = new BodyDef();
            Body ground = getWorld().createBody(bd);


            EdgeShape shape = new EdgeShape();

            // Ground
            shape.set(new Vec2(-20.0f, 0.0f), new Vec2(20.0f, 0.0f));

            FixtureDef fd = new FixtureDef();
            fd.shape = shape;
            fd.density = 0.0f;
            fd.friction = 0.6f;

            ground.createFixture(fd);

            // Left-hand edge
            shape.set(new Vec2(-20, 0.0f), new Vec2(-20.0f, 40.0f));
            ground.createFixture(fd);

            // Right-hand edge
            shape.set(new Vec2(20, 0.0f), new Vec2(20.0f, 40.0f));
            ground.createFixture(fd);
        }

        {
            // A range of different values to be randomly chosen for each actor to give them all different attributes.
            float[] strength = {0.19f, 0.23f, 0.28f, 0.36f, 0.43f, 0.55f, 0.58f, 0.43f, 0.55f, 0.66f};
            float[] weight = {0.19f, 0.23f, 0.28f, 0.36f, 0.48f, 0.55f, 0.58f, 0.63f, 0.72f, 0.88f};
            float[] power = {1.75f, 2.05f, 2.45f, 2.69f, 3.25f, 3.84f, 4.12f, 4.25f, 4.75f, 5f};

            // We'll model each actor as a circle
            CircleShape m_shape1 = new CircleShape();
            m_shape1.m_radius = 0.9f;
            FixtureDef fd = new FixtureDef();
            fd.shape = m_shape1;
            fd.density = 0.65f;
            fd.friction = 0.5f;
            // Bounciness
            fd.restitution = 0.99f;


            Random randomIndex = new Random();

            // Populate the scene with Moshers
            for(float x = -19.1f; x <= 20f; x=x+1.805f) {
                fd.density = weight[randomIndex.nextInt(10)];
                fd.friction = strength[randomIndex.nextInt(10)];

                BodyDef bd = new BodyDef();
                bd.type = BodyType.DYNAMIC;
                bd.position.set(x, 00.0f);
                Body m_body1 = getWorld().createBody(bd);

                // Jump!
                Vec2 force  = new Vec2(0, power[randomIndex.nextInt(10)]);
                Vec2 point = m_body1.getWorldPoint(m_body1.getWorldCenter());
                m_body1.applyLinearImpulse (force ,point);

                m_body1.createFixture(fd);
            }
        }
    }

    @Override
    public String getTestName() {
        return "Bounce";
    }
}
