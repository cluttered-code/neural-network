package com.clutteredcode.activation;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author david.clutter@ca.com
 */
@RunWith(JMockit.class)
public class ActivationTest {

    @Test
    public void testEvaluateLinear() {
        final Activation activation = Activation.LINEAR;
        for(int i = -10; i <= 10; ++i) {
            final double result = activation.evaluate(i);
            assertEquals(i, result, 0.0);
        }
    }

    @Test
    public void testEvaluateSigmoid() {
        final ActivationFunction TEST_SIGMOID = input -> 1 / (1 + Math.exp(-input));
        final Activation activation = Activation.SIGMOID;
        for(int i = -10; i <= 10; ++i) {
            final double result = activation.evaluate(i);
            assertEquals(TEST_SIGMOID.evaluate(i), result, 0.0);
        }
    }

    @Test
    public void testEvaluateHyperbolicTangent() {
        final Activation activation = Activation.TAN_H;
        for(int i = -10; i <= 10; ++i) {
            final double result = activation.evaluate(i);
            assertEquals(Math.tanh(i), result, 0.0);
        }
    }

    @Test
    public void testRandomLinear(@Mocked final Random random) {
        new Expectations() {{
            random.nextDouble(); times = 2; returns (0.0, 0.33);
        }};

        final Activation lowerBound = Activation.random();
        final Activation upperBound = Activation.random();

        assertEquals(Activation.LINEAR, lowerBound);
        assertEquals(Activation.LINEAR, upperBound);
    }

    @Test
    public void testRandomSigmoid(@Mocked final Random random) {
        new Expectations() {{
            random.nextDouble(); times = 2; returns (0.34, 0.66);
        }};

        final Activation lowerBound = Activation.random();
        final Activation upperBound = Activation.random();

        assertEquals(Activation.SIGMOID, lowerBound);
        assertEquals(Activation.SIGMOID, upperBound);
    }

    @Test
    public void testRandomHyperbolicTangent(@Mocked final Random random) {
        new Expectations() {{
            random.nextDouble(); times = 2; returns (0.67, 0.99);
        }};

        final Activation lowerBound = Activation.random();
        final Activation upperBound = Activation.random();

        assertEquals(Activation.TAN_H, lowerBound);
        assertEquals(Activation.TAN_H, upperBound);
    }

    @Test
    public void testValueOf() {
        for(final Activation activation : Activation.values()) {
            final Activation result = Activation.valueOf(activation.toString());
            assertEquals(activation, result);
        }
    }
}