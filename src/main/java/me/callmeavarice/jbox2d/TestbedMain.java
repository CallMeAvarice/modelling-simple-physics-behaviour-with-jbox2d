package me.callmeavarice.jbox2d;

import me.callmeavarice.jbox2d.tests.PuttingBounceInTheMoshPit;
import me.callmeavarice.jbox2d.tests.SimpleJBox2DWorld;
import org.jbox2d.testbed.framework.TestbedController;
import org.jbox2d.testbed.framework.TestbedErrorHandler;
import org.jbox2d.testbed.framework.TestbedModel;
import org.jbox2d.testbed.framework.j2d.DebugDrawJ2D;
import org.jbox2d.testbed.framework.j2d.TestPanelJ2D;
import org.jbox2d.testbed.framework.j2d.TestbedSidePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class TestbedMain {
    private static final Logger log = LoggerFactory.getLogger(TestbedMain.class);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            log.warn("Could not set the look and feel to nimbus.  "
                    + "Hopefully you're on a mac so the window isn't ugly as crap.");
        }
        TestbedModel model = new TestbedModel();
        final TestbedController controller =
                new TestbedController(model, TestbedController.UpdateBehavior.UPDATE_CALLED, TestbedController.MouseBehavior.NORMAL,
                        new TestbedErrorHandler() {
                            @Override
                            public void serializationError(Exception e, String message) {
                                JOptionPane.showMessageDialog(null, message, "Serialization Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        });
        TestPanelJ2D panel = new TestPanelJ2D(model, controller);
        model.setPanel(panel);
        model.setDebugDraw(new DebugDrawJ2D(panel, true));

        model.addCategory("Featured");
        model.addTest(new SimpleJBox2DWorld());
        model.addTest(new PuttingBounceInTheMoshPit());

        JFrame testbed = new JFrame();
        testbed.setTitle("JBox2D Testbed");
        testbed.setLayout(new BorderLayout());
        TestbedSidePanel side = new TestbedSidePanel(model, controller);
        testbed.add(panel, "Center");
        testbed.add(new JScrollPane(side), "East");
        testbed.pack();
        testbed.setVisible(true);
        testbed.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                controller.playTest(0);
                controller.start();
            }
        });

    }
}
