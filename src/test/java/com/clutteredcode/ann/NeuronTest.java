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
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static mockit.Deencapsulation.getField;
import static mockit.Deencapsulation.invoke;
import static mockit.Deencapsulation.setField;

/**
 * @author cluttered.code@gmail.com
 */
public class NeuronTest {

    @Tested
    @SuppressWarnings("unused")
    private Neuron neuron;

    @Injectable
    @SuppressWarnings("unused")
    private final ActivationType activationType = ActivationType.LINEAR;

    @Injectable
    @SuppressWarnings("unused")
    private final double bias = 42.0;

    @Injectable
    @SuppressWarnings("unused")
    private List<Double> weights;

    @Test
    public void testFullConstructor() {
        final ActivationType actualActivationType = getField(neuron, "activationType");
        final double actualBias = getField(neuron, "bias");
        final List<Double> actualWeights = getField(neuron, "weights");

        assertNotNull(neuron);
        assertEquals(actualActivationType, actualActivationType);
        assertEquals(bias, actualBias);
        assertEquals(weights, actualWeights);
    }

    @Test
    public void testGeneratingConstructor() {
        final int numInputs = 5;
        final Neuron localNeuron = new Neuron(activationType, numInputs);
        final List<Double> weights = getField(localNeuron, "weights");

        assertEquals(numInputs, weights.size());
    }

    @Test
    public void testFireWithDouble(@Mocked @SuppressWarnings("unused") final Collections collection, @Mocked final List<Double> inputs) {
        final double input = 42.0;

        new Expectations(neuron) {{
            Collections.singletonList(input); times = 1; result = inputs;
            neuron.fire(inputs); times = 1;
        }};

        neuron.fire(input);
    }

    @Test
    public void testFireWithList() {
        final List<Double> inputs = Arrays.asList(42.0);
        final double dotProduct = 2525.0;
        final double expected = 12345.0;
        final ActivationFunction linear = ActivationType.LINEAR.getActivationFunction();

        new Expectations(neuron) {{
            invoke(neuron, "dotProductWithWeights", inputs); times = 1; result = dotProduct;
            activationType.getActivationFunction(); times = 1; result = linear;
            linear.evaluate(dotProduct - bias); times = 1; result = expected;
        }};

        final double actual = neuron.fire(inputs);

        assertEquals(expected, actual);
    }

    @Test
    public void testDotProductWithWeights() {
        final List<Double> localWeights = Arrays.asList(83.4, 12.34, 34.68);
        final List<Double> inputs = Arrays.asList(2.67, 29.55, 45.83);
        final double expected = 2176.7093999999997;

        setField(neuron, "weights", localWeights);

        final double result = invoke(neuron, "dotProductWithWeights", inputs);

        assertEquals(expected, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDotProductWithWeights_Exception() {
        final List<Double> localWeights = Arrays.asList(83.4);
        final List<Double> inputs = Arrays.asList(2.67, 29.55, 45.83);

        setField(neuron, "weights", localWeights);

        invoke(neuron, "dotProductWithWeights", inputs);
    }

    @Test
    public void testBoundedRandom_Minimum(@Mocked final Random random) {
        setField(neuron, "random", random);

        new Expectations() {{
            random.nextDouble(); times = 1; result = 0.0;
        }};

        final double actual = invoke(neuron, "randomBoundedDouble");
        assertEquals((double) Neuron.MINIMUM_BOUND, actual);
    }

    @Test
    public void testBoundedRandom_Maximum(@Mocked final Random random) {
        setField(neuron, "random", random);

        new Expectations() {{
            random.nextDouble(); times = 1; result = 0.999999999999999999999999;
        }};

        final double actual = invoke(neuron, "randomBoundedDouble");
        assertEquals((double) Neuron.MAXIMUM_BOUND, actual);
    }
}