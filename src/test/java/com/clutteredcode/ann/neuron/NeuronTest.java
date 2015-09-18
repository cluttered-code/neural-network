/*
 * Copyright 2015 David Clutter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.clutteredcode.ann.neuron;

import com.clutteredcode.ann.activation.Activation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static mockit.Deencapsulation.*;
import static org.junit.Assert.*;

/**
 * @author David Clutter
 */
@RunWith(JMockit.class)
public class NeuronTest {

    @Tested @Mocked
    @SuppressWarnings("unused")
    private Neuron neuron;

    @Injectable
    @SuppressWarnings("unused")
    private final Activation activation = Activation.LINEAR;

    @Injectable
    @SuppressWarnings("unused")
    private final double bias = 42.0;

    @Injectable
    @SuppressWarnings("unused")
    private List<Double> inputWeights;

    @Test
    public void testFullConstructor() {
        final Activation actualActivation = getField(neuron, "activation");
        final double actualBias = getField(neuron, "bias");
        final List<Double> actualWeights = getField(neuron, "inputWeights");

        assertNotNull(neuron);
        assertEquals(actualActivation, actualActivation);
        assertEquals(bias, actualBias, 0.0);
        assertEquals(inputWeights, actualWeights);
    }

    @Test
    public void testGeneratingConstructor() {
        final int numInputs = 5;
        final Neuron localNeuron = new Neuron(activation, numInputs);
        final List<Double> weights = getField(localNeuron, "inputWeights");

        assertEquals(numInputs, weights.size());
    }

    @Test
    public void testFireWithDouble(@Mocked @SuppressWarnings("unused") final Collections collection, @Mocked final List<Double> inputs) {
        final double input = 42.0;

        new Expectations() {{
            Collections.singletonList(input);
            times = 1;
            result = inputs;
            neuron.fire(inputs);
            times = 1;
        }};

        neuron.fire(input);
    }

    @Test
    public void testFireWithList() {
        final List<Double> inputs = Arrays.asList(42.0);
        final double dotProduct = 2525.0;
        final double expected = 12345.0;

        new Expectations() {{
            invoke(neuron, "dotProductWithWeights", inputs);
            times = 1;
            result = dotProduct;
            activation.evaluate(dotProduct - bias);
            times = 1;
            result = expected;
        }};

        final double actual = neuron.fire(inputs);

        assertEquals(expected, actual, 0.0);
    }

    @Test
    public void testDotProductWithWeights() {
        final List<Double> localWeights = Arrays.asList(83.4, 12.34, 34.68);
        final List<Double> inputs = Arrays.asList(2.67, 29.55, 45.83);
        final double expected = 2176.7093999999997;

        setField(neuron, "inputWeights", localWeights);

        final double result = invoke(neuron, "dotProductWithWeights", inputs);

        assertEquals(expected, result, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDotProductWithWeights_Exception() {
        final List<Double> localWeights = Arrays.asList(83.4);
        final List<Double> inputs = Arrays.asList(2.67, 29.55, 45.83);

        setField(neuron, "inputWeights", localWeights);

        invoke(neuron, "dotProductWithWeights", inputs);
    }

    @Test
    public void testBoundedRandom_Minimum(@Mocked final Random random) {
        setField(neuron, "random", random);

        new Expectations() {{
            random.nextDouble();
            times = 1;
            result = 0.0;
        }};

        final double actual = invoke(neuron, "randomBoundedDouble");
        assertEquals((double) Neuron.MINIMUM_BOUND, actual, 0.0);
    }

    @Test
    public void testBoundedRandom_Maximum(@Mocked final Random random) {
        setField(neuron, "random", random);

        new Expectations() {{
            random.nextDouble();
            times = 1;
            result = 0.999999999999999999999999;
        }};

        final double actual = invoke(neuron, "randomBoundedDouble");
        assertEquals((double) Neuron.MAXIMUM_BOUND, actual, 0.0);
    }

    @Test
    public void testMutate_none() {
        final List<Double> localWeights = Arrays.asList(2.67, 29.55, 45.83);

        setField(neuron, "inputWeights", localWeights);

        new Expectations(Activation.class) {{
            Activation.random();
            times = 0;
        }};

        final Neuron mutatedNeuron = neuron.mutate(0.0);

        final Activation expectedActivation = getField(neuron, "activation");
        final Activation actualActivation = getField(mutatedNeuron, "activation");
        assertEquals(expectedActivation, actualActivation);

        final double expectedBias = getField(neuron, "bias");
        final double actualBias = getField(mutatedNeuron, "bias");
        assertEquals(expectedBias, actualBias, 0.0);

        final List<Double> expectedWeights = getField(neuron, "inputWeights");
        final List<Double> actualWeights = getField(mutatedNeuron, "inputWeights");
        assertNotSame(expectedWeights, actualWeights);
        assertEquals(expectedWeights, actualWeights);
    }

    @Test
    public void testMutate_all() {
        final List<Double> localWeights = Arrays.asList(2.67, 29.55, 45.83);

        setField(neuron, "inputWeights", localWeights);

        new Expectations(Activation.class) {{
            Activation.random();
            times = 1;
            result = Activation.TAN_H;
            invoke(neuron, "randomBoundedDouble");
            times = 4;
        }};

        final Neuron mutatedNeuron = neuron.mutate(1.0);

        final Activation expectedActivation = getField(neuron, "activation");
        final Activation actualActivation = getField(mutatedNeuron, "activation");
        assert (expectedActivation != actualActivation);

        final double expectedBias = getField(neuron, "bias");
        final double actualBias = getField(mutatedNeuron, "bias");
        assert (expectedBias != actualBias);

        final List<Double> expectedWeights = getField(neuron, "inputWeights");
        final List<Double> actualWeights = getField(mutatedNeuron, "inputWeights");
        for (int i = 0; i < localWeights.size(); ++i)
            assert (!expectedWeights.get(i).equals(actualWeights.get(i)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCrossover(@Mocked final Random random) {
        final List<Double> weights = Arrays.asList(1.0, 3.0, 5.0);
        setField(neuron, "random", random);
        setField(neuron, "inputWeights", weights);

        final Activation mateActivation = Activation.TAN_H;
        final double mateBias = 25.0;
        final List<Double> mateWeights = Arrays.asList(2.0, 4.0, 6.0);
        final Neuron mate = newInstance(Neuron.class, mateActivation, mateBias, mateWeights);

        new Expectations() {{
            random.nextBoolean();
            times = 5;
            returns(true, false, true, false, true);
        }};

        final Neuron crossoverNeuron = neuron.crossover(mate);

        assert (mateActivation == getField(crossoverNeuron, "activation"));
        assertEquals(bias, getField(crossoverNeuron, "bias"), 0.0);
        assertEquals(mateWeights.get(0), ((List<Double>) getField(crossoverNeuron, "inputWeights")).get(0));
        assertEquals(weights.get(1), ((List<Double>) getField(crossoverNeuron, "inputWeights")).get(1));
        assertEquals(mateWeights.get(2), ((List<Double>) getField(crossoverNeuron, "inputWeights")).get(2));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCrossover_opposite(@Mocked final Random random) {
        final List<Double> weights = Arrays.asList(1.0, 3.0, 5.0);
        setField(neuron, "random", random);
        setField(neuron, "inputWeights", weights);

        final Activation mateActivation = Activation.TAN_H;
        final double mateBias = 25.0;
        final List<Double> mateWeights = Arrays.asList(2.0, 4.0, 6.0);
        final Neuron mate = newInstance(Neuron.class, mateActivation, mateBias, mateWeights);

        new Expectations() {{
            random.nextBoolean();
            times = 5;
            returns(false, true, false, true, false);
        }};

        final Neuron crossoverNeuron = neuron.crossover(mate);

        assert (activation == getField(crossoverNeuron, "activation"));
        assertEquals(mateBias, getField(crossoverNeuron, "bias"), 0.0);
        assertEquals(weights.get(0), ((List<Double>) getField(crossoverNeuron, "inputWeights")).get(0));
        assertEquals(mateWeights.get(1), ((List<Double>) getField(crossoverNeuron, "inputWeights")).get(1));
        assertEquals(weights.get(2), ((List<Double>) getField(crossoverNeuron, "inputWeights")).get(2));
    }
}