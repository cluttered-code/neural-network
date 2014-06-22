package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationFunction;
import com.clutteredcode.ann.activation.ActivationType;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static junit.framework.Assert.fail;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class NeuronTest {

    @Test
    public void testConstructor() {
        final ActivationType type = ActivationType.TAN_H;
        final ActivationFunction function = type.getFunction();
        final double bias = 42.0;
        final double[] weights = new double[]{32.0, 64.0, 128.0};

        final Neuron neuron = new Neuron(type, bias, weights);

        assertEquals(type, neuron.activationType);
        assertEquals(function, neuron.activationFunction);
        assertEquals(bias, neuron.bias);
        assertArrayEquals(weights, neuron.weights, 0.0);
    }

    @Test
    public void testFire() {
        final ActivationType type = ActivationType.SIGMOID;
        final double fireOutput = 42.0;
        final double expected = type.getFunction().activate(fireOutput);

        final Neuron neuron = new Neuron(type, 78.0, new double[0]);

        // Mock and invocation count for dotProduct
        new MockUp<Neuron>() {
            @Mock(invocations = 1)
            public double dotProduct(final double[] inputs) {
                return fireOutput;
            }
        };

        final double result = neuron.fire(new double[0]);

        assertEquals(expected, result);
    }

    @SuppressWarnings("PrimitiveArrayArgumentToVariableArgMethod")
    @Test
    public void testDotProduct() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final double bias = 38.0;
        final double[] weights = new double[] {83.4, 12.34, 34.68};
        final double[] inputs = new double[] {2.67, 29.55, 45.83};

        final Neuron neuron = new Neuron(ActivationType.LINEAR, bias, weights);

        // Call private method neuron.dotProduct(double[])
        final Method method = Neuron.class.getDeclaredMethod("dotProduct", double[].class);
        method.setAccessible(true);
        final double result = (double) method.invoke(neuron, inputs);

        assertEquals(2214.7093999999997, result);
    }

    @SuppressWarnings("PrimitiveArrayArgumentToVariableArgMethod")
    @Test
    public void testDotProduct_NaN() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final double bias = 38.0;
        final double[] weights = new double[] {83.4};
        final double[] inputs = new double[] {Double.NaN};

        final Neuron neuron = new Neuron(ActivationType.LINEAR, bias, weights);

        // Call private method neuron.dotProduct(double[])
        final Method method = Neuron.class.getDeclaredMethod("dotProduct", double[].class);
        method.setAccessible(true);
        final double result = (double) method.invoke(neuron, inputs);

        assertEquals(Double.NaN, result);
    }

    @SuppressWarnings("PrimitiveArrayArgumentToVariableArgMethod")
    @Test(expected = IllegalArgumentException.class)
    public void testDotProduct_SizeMismatch() throws Throwable {
        final double bias = 38.0;
        final double[] weights = new double[] {83.4, 12.34, 34.68};
        final double[] inputs = new double[] {2.67, 29.55};

        final Neuron neuron = new Neuron(ActivationType.LINEAR, bias, weights);

        // Call private method neuron.dotProduct(double[])
        final Method method = Neuron.class.getDeclaredMethod("dotProduct", double[].class);
        method.setAccessible(true);
        try {
            method.invoke(neuron, inputs);
            fail();
        } catch(Exception e) {
            throw e.getCause();
        }
    }
}