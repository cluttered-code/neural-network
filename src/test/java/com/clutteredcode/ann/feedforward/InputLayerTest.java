package com.clutteredcode.ann.feedforward;

import com.clutteredcode.ann.InputNeuron;
import com.clutteredcode.ann.Neuron;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static mockit.Deencapsulation.setField;

/**
 * @author David Clutter
 */
public class InputLayerTest {

    @Tested
    @SuppressWarnings("unused")
    private InputLayer inputLayer;

    @Injectable
    @SuppressWarnings("unused")
    private List<InputNeuron> neurons;

    @Test
    public void testFire(@Mocked final Neuron neuron) {
        final List<Double> inputs = Arrays.asList(42.0, 128.0, 64.0);
        final List<Neuron> localNeurons = Arrays.asList(neuron, neuron, neuron);
        setField(inputLayer, "neurons", localNeurons);

        new Expectations() {{
            neuron.fire(inputs.get(0)); times = 1;
            neuron.fire(inputs.get(1)); times = 1;
            neuron.fire(inputs.get(2)); times = 1;
        }};

        inputLayer.fire(inputs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFile_Exception() {
        final List<Double> inputs = Arrays.asList(42.0, 128.0, 64.0);

        inputLayer.fire(inputs);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMutate() {
        inputLayer.mutate(Math.PI);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCrossover() {
        inputLayer.crossover(inputLayer);
    }
}