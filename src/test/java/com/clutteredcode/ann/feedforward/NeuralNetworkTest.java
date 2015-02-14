package com.clutteredcode.ann.feedforward;

import mockit.Expectations;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author David Clutter
 */
public class NeuralNetworkTest {

    @Mocked
    @SuppressWarnings("unused")
    private InputLayer inputLayer;

    @Mocked
    @SuppressWarnings("unused")
    private Layer layer;

    @SuppressWarnings("unused")
    private NeuralNetwork neuralNetwork;

    @SuppressWarnings("unused")
    private List<Layer> layers;

    @Before
    public void setUp() {
        layers = Arrays.asList(inputLayer, layer, layer);
        neuralNetwork = new NeuralNetwork(layers);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalConstructor() {
        layers = Arrays.asList(layer, layer);
        neuralNetwork = new NeuralNetwork(layers);
    }

    @Test
    public void testFire() {
        final List<Double> inputs = Arrays.asList(Math.PI, Math.E);
        final List<Double> output = Collections.emptyList();

        new Expectations() {{
            inputLayer.fire(inputs); times = 1; result = output;
            layer.fire(output); times = 2; result = output;
        }};

        neuralNetwork.fire(inputs);
    }

    @Test
    public void testMutate() {
        final double rate = 0.42;

        new Expectations() {{
            inputLayer.mutate(anyDouble); times = 0;
            //layer.mutate(rate); times = layers.size() - 1;
        }};

        neuralNetwork.mutate(rate);
    }

    @Test
    public void testCrossover() {
        new Expectations() {{
            inputLayer.crossover((Layer) any); times = 0;
            //layer.crossover(layer); times = layers.size() - 1;
        }};

        neuralNetwork.crossover(neuralNetwork);
    }
}