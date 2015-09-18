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
package com.clutteredcode.ann.network.feedforward;

import com.clutteredcode.ann.neuron.InputNeuron;
import com.clutteredcode.ann.neuron.Neuron;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mockit.Deencapsulation.newInstance;
import static mockit.Deencapsulation.setField;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

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
            neuron.fire(inputs);
            times = 3;
            returns(inputs);
        }};

        final List<Double> results = layer.fire(inputs);
        assertEquals(inputs, results);
    }

    @Test
    public void testMutate(@Mocked final Neuron neuron) {
        final double rate = 0.42;
        final List<Neuron> localNeurons = Arrays.asList(neuron, neuron, neuron);
        setField(layer, "neurons", localNeurons);

        new Expectations() {{
            neuron.mutate(rate);
            times = localNeurons.size();
            result = neuron;
        }};

        final Layer result = layer.mutate(rate);
        assertNotSame(layer, result);
    }

    @Test
    public void testCrossover(@Mocked final Neuron neuron) {
        final List<Neuron> localNeurons = Arrays.asList(neuron, neuron, neuron);
        setField(layer, "neurons", localNeurons);

        final Layer mate = newInstance(Layer.class, localNeurons);

        new Expectations() {{
            neuron.crossover(neuron);
            times = localNeurons.size();
            result = neuron;
        }};

        final Layer result = layer.crossover(mate);
        assertNotSame(layer, result);
    }
}