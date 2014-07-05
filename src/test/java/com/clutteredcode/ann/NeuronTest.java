/**
 * (C) Copyright 2014 David Clutter (cluttered.code@gmail.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationFunction;
import com.clutteredcode.ann.activation.ActivationType;
import junit.framework.Assert;
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
        final double bias = 42.0;
        final double[] weights = new double[]{32.0, 64.0, 128.0};

        final Neuron neuron = new Neuron(type, bias, weights);

        assertEquals(type, neuron.activationType);
        assertEquals(bias, neuron.bias);
        assertArrayEquals(weights, neuron.weights, 0.0);
    }

    @Test
    public void testFire() {
        final double input = 5738.873;
        final double output = 674.87;

        final Neuron neuron = new Neuron(ActivationType.random(), Math.PI, new double[0]);

        new MockUp<Neuron>() {
            @Mock(invocations = 1)
            public double fire(final double[] inputs) {
                assertEquals(1, inputs.length);
                assertEquals(input, inputs[0]);
                return output;
            }
        };

        final double result = neuron.fire(input);
        assertEquals(output, result);
    }

    @Test
    public void testFireArray() {
        final double bias = 78.54;
        final double dotProductOutput = 42.0;
        final double activationFunctionOutput = Math.PI;

        final Neuron neuron = new Neuron(ActivationType.SIGMOID, bias, new double[0]);

        // Mock Activation function to validate inputs
        final MockUp<ActivationFunction> af = new MockUp<ActivationFunction>() {
            @Mock(invocations = 1)
            public double activate(final double input) {
                assertEquals(bias + dotProductOutput, input);
                return activationFunctionOutput;
            }
        };

        // Mock ActivationType to return Mocked ActivationType
        new MockUp<ActivationType>() {
            @Mock(invocations = 1)
            public ActivationFunction getFunction() {
                return af.getMockInstance();
            }
        };

        // Mock Neuron dotProduct
        new MockUp<Neuron>() {
            @Mock(invocations = 1)
            public double dotProduct(final double[] inputs) {
                return dotProductOutput;
            }
        };

        assertEquals(activationFunctionOutput, neuron.fire(new double[0]));
    }

    @Test
    @SuppressWarnings("PrimitiveArrayArgumentToVariableArgMethod")
    public void testDotProduct() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final double bias = Math.PI;
        final double[] weights = new double[]{83.4, 12.34, 34.68};
        final double[] inputs = new double[]{2.67, 29.55, 45.83};

        final Neuron neuron = new Neuron(ActivationType.LINEAR, bias, weights);

        // Call private method neuron.dotProduct(double[])
        final Method method = Neuron.class.getDeclaredMethod("dotProduct", double[].class);
        method.setAccessible(true);
        final double result = (double) method.invoke(neuron, inputs);

        assertEquals(2176.7093999999997, result);
    }

    @Test
    @SuppressWarnings("PrimitiveArrayArgumentToVariableArgMethod")
    public void testDotProduct_NaN() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final double bias = 38.0;
        final double[] weights = new double[]{83.4};
        final double[] inputs = new double[]{Double.NaN};

        final Neuron neuron = new Neuron(ActivationType.LINEAR, bias, weights);

        // Call private method neuron.dotProduct(double[])
        final Method method = Neuron.class.getDeclaredMethod("dotProduct", double[].class);
        method.setAccessible(true);
        final double result = (double) method.invoke(neuron, inputs);

        assertEquals(Double.NaN, result);
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("PrimitiveArrayArgumentToVariableArgMethod")
    public void testDotProduct_SizeMismatch() throws Throwable {
        final double bias = 38.0;
        final double[] weights = new double[]{83.4, 12.34, 34.68};
        final double[] inputs = new double[]{2.67, 29.55};

        final Neuron neuron = new Neuron(ActivationType.LINEAR, bias, weights);

        // Call private method neuron.dotProduct(double[])
        final Method method = Neuron.class.getDeclaredMethod("dotProduct", double[].class);
        method.setAccessible(true);
        try {
            method.invoke(neuron, inputs);
            fail();
        } catch (Exception e) {
            throw e.getCause();
        }
    }
}