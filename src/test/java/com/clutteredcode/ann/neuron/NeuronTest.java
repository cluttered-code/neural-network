package com.clutteredcode.ann.neuron;

import com.clutteredcode.ann.activation.Activation;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mockit.Deencapsulation.getField;
import static mockit.Deencapsulation.invoke;
import static mockit.Deencapsulation.setField;
import static org.junit.Assert.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class NeuronTest {

    @Tested
    @Mocked
    @SuppressWarnings("unused")
    private Neuron neuron;

    @Injectable
    @SuppressWarnings("unused")
    private Activation activation;

    @Injectable
    private final double bias = Math.PI;

    @Injectable
    @SuppressWarnings("unused")
    private List<Double> inputWeights;

    @Test
    public void testConstructor() {
        final Activation activationField = getField(neuron, "activation");
        assertEquals(activation, activationField);

        final double biasField = getField(neuron, "bias");
        assertEquals(bias, biasField, 0.0);

        final List<Double> inputWeightsField = getField(neuron, "inputWeights");
        assertEquals(inputWeights, inputWeightsField);
    }

    @Test
    public void testFire_Single() {
        final List<Double> inputs = Collections.singletonList(Math.E);

        new Expectations() {{
            neuron.fire(inputs); times = 1; result = Math.PI;
        }};

        final double result = neuron.fire(Math.E);

        assertEquals(Math.PI, result, 0.0);
    }

    @Test
    public void testFire_List(@Mocked final List<Double> inputs) {
        new Expectations() {{
            invoke(neuron, "dotProductWithWeights", inputs); times = 1; result = Math.E;
            activation.evaluate(Math.E - bias); times = 1; result = Math.E;
        }};

        final double result = neuron.fire(inputs);

        assertEquals(Math.E, result, 0.0);
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
}