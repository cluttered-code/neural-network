package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationFunction;
import com.clutteredcode.ann.activation.ActivationType;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.*;
import static mockit.Deencapsulation.*;

/**
 * @author David Clutter
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

    @Test
    public void testMutate_none() {
        final List<Double> localWeights = Arrays.asList(2.67, 29.55, 45.83);

        setField(neuron, "weights", localWeights);

        new Expectations(ActivationType.class) {{
            ActivationType.random(); times = 0;
        }};

        final Neuron mutatedNeuron = neuron.mutate(0.0);

        final ActivationType expectedActivationType = getField(neuron, "activationType");
        final ActivationType actualActivationType = getField(mutatedNeuron, "activationType");
        assertEquals(expectedActivationType, actualActivationType);

        final double expectedBias = getField(neuron, "bias");
        final double actualBias = getField(mutatedNeuron, "bias");
        assertEquals(expectedBias, actualBias);

        final List<Double> expectedWeights = getField(neuron, "weights");
        final List<Double> actualWeights = getField(mutatedNeuron, "weights");
        assertNotSame(expectedWeights, actualWeights);
        assertEquals(expectedWeights, actualWeights);
    }

    @Test
    public void testMutate_all() {
        final List<Double> localWeights = Arrays.asList(2.67, 29.55, 45.83);

        setField(neuron, "weights", localWeights);

        new Expectations(ActivationType.class, neuron) {{
            ActivationType.random(); times = 1; result = ActivationType.TAN_H;
            invoke(neuron, "randomBoundedDouble"); times = 4;
        }};

        final Neuron mutatedNeuron = neuron.mutate(1.0);

        final ActivationType expectedActivationType = getField(neuron, "activationType");
        final ActivationType actualActivationType = getField(mutatedNeuron, "activationType");
        assert(expectedActivationType != actualActivationType);

        final double expectedBias = getField(neuron, "bias");
        final double actualBias = getField(mutatedNeuron, "bias");
        assert(expectedBias != actualBias);

        final List<Double> expectedWeights = getField(neuron, "weights");
        final List<Double> actualWeights = getField(mutatedNeuron, "weights");
        for(int i = 0; i < localWeights.size(); ++i)
            assert(!expectedWeights.get(i).equals(actualWeights.get(i)));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCrossover(@Mocked final Random random) {
        final List<Double> weights = Arrays.asList(1.0, 3.0, 5.0);
        setField(neuron, "random", random);
        setField(neuron, "weights", weights);

        final ActivationType mateActivationType = ActivationType.TAN_H;
        final double mateBias = 25.0;
        final List<Double> mateWeights = Arrays.asList(2.0, 4.0, 6.0);
        final Neuron mate = newInstance(Neuron.class, mateActivationType, mateBias, mateWeights);

        new Expectations() {{
            random.nextBoolean(); times = 5; returns(true, false, true, false, true);
        }};

        final Neuron crossoverNeuron = neuron.crossover(mate);

        assert(mateActivationType == getField(crossoverNeuron, "activationType"));
        assertEquals(bias, getField(crossoverNeuron, "bias"));
        assertEquals(mateWeights.get(0), ((List<Double>) getField(crossoverNeuron, "weights")).get(0));
        assertEquals(weights.get(1), ((List<Double>) getField(crossoverNeuron, "weights")).get(1));
        assertEquals(mateWeights.get(2), ((List<Double>) getField(crossoverNeuron, "weights")).get(2));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCrossover_opposite(@Mocked final Random random) {
        final List<Double> weights = Arrays.asList(1.0, 3.0, 5.0);
        setField(neuron, "random", random);
        setField(neuron, "weights", weights);

        final ActivationType mateActivationType = ActivationType.TAN_H;
        final double mateBias = 25.0;
        final List<Double> mateWeights = Arrays.asList(2.0, 4.0, 6.0);
        final Neuron mate = newInstance(Neuron.class, mateActivationType, mateBias, mateWeights);

        new Expectations() {{
            random.nextBoolean(); times = 5; returns(false, true, false, true, false);
        }};

        final Neuron crossoverNeuron = neuron.crossover(mate);

        assert(activationType == getField(crossoverNeuron, "activationType"));
        assertEquals(mateBias, getField(crossoverNeuron, "bias"));
        assertEquals(weights.get(0), ((List<Double>) getField(crossoverNeuron, "weights")).get(0));
        assertEquals(mateWeights.get(1), ((List<Double>) getField(crossoverNeuron, "weights")).get(1));
        assertEquals(weights.get(2), ((List<Double>) getField(crossoverNeuron, "weights")).get(2));
    }
}