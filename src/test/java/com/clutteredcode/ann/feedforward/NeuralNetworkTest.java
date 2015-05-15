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
            inputLayer.fire(inputs);
            times = 1;
            result = output;
            layer.fire(output);
            times = 2;
            result = output;
        }};

        neuralNetwork.fire(inputs);
    }

    @Test
    public void testMutate() {
        final double rate = 0.42;

        new Expectations() {{
            inputLayer.mutate(anyDouble);
            times = 0;
            //layer.mutate(rate); times = layers.size() - 1;
        }};

        neuralNetwork.mutate(rate);
    }

    @Test
    public void testCrossover() {
        new Expectations() {{
            inputLayer.crossover((Layer) any);
            times = 0;
            //layer.crossover(layer); times = layers.size() - 1;
        }};

        neuralNetwork.crossover(neuralNetwork);
    }
}