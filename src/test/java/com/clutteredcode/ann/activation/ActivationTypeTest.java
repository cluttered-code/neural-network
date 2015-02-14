package com.clutteredcode.ann.activation;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static mockit.Deencapsulation.getField;

/**
 * @author David Clutter
 */
public class ActivationTypeTest {

    @Test
    public void testGetLinearActivationFunction() {
        final ActivationFunction expected = getField(ActivationType.class, "LINEAR_FUNCTION");
        final ActivationFunction actual = ActivationType.LINEAR.getActivationFunction();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetSigmoidActivationFunction() {
        final ActivationFunction expected = getField(ActivationType.class, "SIGMOID_FUNCTION");
        final ActivationFunction actual = ActivationType.SIGMOID.getActivationFunction();

        assertEquals(expected, actual);
    }

    @Test
    public void testGetHyperbolicTangentActivationFunction() {
        final ActivationFunction expected = getField(ActivationType.class, "TAN_H_FUNCTION");
        final ActivationFunction actual = ActivationType.TAN_H.getActivationFunction();

        assertEquals(expected, actual);
    }

    @Test
    public void testRandomLinear() {
        new MockUp<Math>() {
            @Mock(invocations = 1)
            @SuppressWarnings("unused")
            double random() {
                return 0.0;
            }
        };

        final ActivationType activationType = ActivationType.random();

        assertEquals(ActivationType.LINEAR, activationType);
    }

    @Test
    public void testRandomSigmoid() {
        new MockUp<Math>() {
            @Mock(invocations = 1)
            @SuppressWarnings("unused")
            double random() {
                return 0.35;
            }
        };

        final ActivationType activationType = ActivationType.random();

        assertEquals(ActivationType.SIGMOID, activationType);
    }

    @Test
    public void testRandomHyperbolicTangent() {
        new MockUp<Math>() {
            @Mock(invocations = 1)
            @SuppressWarnings("unused")
            double random() {
                return 0.75;
            }
        };

        final ActivationType activationType = ActivationType.random();

        assertEquals(ActivationType.TAN_H, activationType);
    }

    @Test
    public void testValueOfLinear() {
        final String linear = "LINEAR";
        final ActivationType activationType = ActivationType.valueOf(linear);

        assertEquals(ActivationType.LINEAR, activationType);
    }

    @Test
    public void testValueOfSigmoid() {
        final String sigmoid = "SIGMOID";
        final ActivationType activationType = ActivationType.valueOf(sigmoid);

        assertEquals(ActivationType.SIGMOID, activationType);
    }

    @Test
    public void testValueOfHyperbolicTangent() {
        final String tanH = "TAN_H";
        final ActivationType activationType = ActivationType.valueOf(tanH);

        assertEquals(ActivationType.TAN_H, activationType);
    }
}