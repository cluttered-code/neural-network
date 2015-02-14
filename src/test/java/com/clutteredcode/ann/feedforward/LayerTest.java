package com.clutteredcode.ann.feedforward;

import com.clutteredcode.ann.InputNeuron;
import com.clutteredcode.ann.Neuron;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static mockit.Deencapsulation.newInstance;
import static mockit.Deencapsulation.setField;

/**
 * @author David Clutter
 */
public class LayerTest {

    private Layer layer;

    private final List<InputNeuron> neurons = new ArrayList<>();

    @Before
    public void setUp() {
        layer = new Layer(neurons);
    }

    @Test
    public void testFire(@Mocked final Neuron neuron) {
        final List<Double> inputs = Arrays.asList(42.0, 128.0, 64.0);
        final List<Neuron> localNeurons = Arrays.asList(neuron, neuron, neuron);
        setField(layer, "neurons", localNeurons);

        new Expectations() {{
            neuron.fire(inputs); times = 3; returns(inputs);
        }};

        List<Double> results = layer.fire(inputs);
        assertEquals(inputs, results);
    }

    @Test
    public void testMutate(@Mocked final Neuron neuron) {
        final double rate = 0.42;
        final List<Neuron> localNeurons = Arrays.asList(neuron, neuron, neuron);
        setField(layer, "neurons", localNeurons);

        new Expectations() {{
            neuron.mutate(rate); times = localNeurons.size();
        }};

        layer.mutate(rate);
    }

    @Test
    public void testCrossover(@Mocked final Neuron neuron) {
        final List<Neuron> localNeurons = Arrays.asList(neuron, neuron, neuron);
        setField(layer, "neurons", localNeurons);

        final Layer mate = newInstance(Layer.class, localNeurons);

        new Expectations() {{
            neuron.crossover(neuron); times = localNeurons.size();
        }};

        layer.crossover(mate);
    }
}