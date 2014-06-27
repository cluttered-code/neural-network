package com.clutteredcode.ann.activation;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class ActivationTest {

    @Test
    public void testLinearActivationFunction() {
        final ActivationFunction af = ActivationType.LINEAR.getFunction();

        assertEquals(-374.25, af.activate(-374.25));
        assertEquals(-25.687, af.activate(-25.687));
        assertEquals(0.0, af.activate(0.0));
        assertEquals(15.923, af.activate(15.923));
        assertEquals(89.34, af.activate(89.34));
    }

    @Test
    public void testHyperbolicTangentActivationFunction() {
        final ActivationFunction af = ActivationType.TAN_H.getFunction();
        final ActivationFunction expected = Math::tanh;

        assertEquals(expected.activate(-374.25), af.activate(-374.25));
        assertEquals(expected.activate(-25.687), af.activate(-25.687));
        assertEquals(expected.activate(0.0), af.activate(0.0));
        assertEquals(expected.activate(15.923), af.activate(15.923));
        assertEquals(expected.activate(89.34), af.activate(89.34));
    }

    @Test
    public void testSigmoidActivationFunction() {
        final ActivationFunction af = ActivationType.SIGMOID.getFunction();
        final ActivationFunction expected = (final double input) -> 1 / (1 + Math.exp(-input));

        assertEquals(expected.activate(-374.25), af.activate(-374.25));
        assertEquals(expected.activate(-25.687), af.activate(-25.687));
        assertEquals(expected.activate(0.0), af.activate(0.0));
        assertEquals(expected.activate(15.923), af.activate(15.923));
        assertEquals(expected.activate(89.34), af.activate(89.34));
    }
}