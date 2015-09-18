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
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mockit.Deencapsulation.setField;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author David Clutter
 */
public class InputLayerTest {

    @Tested @Mocked
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
            neuron.fire(inputs.get(0));
            times = 1;
            result = inputs.get(0);
            neuron.fire(inputs.get(1));
            times = 1;
            result = inputs.get(1);
            neuron.fire(inputs.get(2));
            times = 1;
            result = inputs.get(2);
        }};

        final List<Double> results = inputLayer.fire(inputs);
        assertEquals(inputs, results);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFile_Exception() {
        final List<Double> inputs = Arrays.asList(42.0, 128.0, 64.0);

        inputLayer.fire(inputs);
        fail();
    }

    @Test
    public void testMutate() {
        final Layer result = inputLayer.mutate(Math.PI);
        assertEquals(inputLayer, result);
    }

    @Test
    public void testCrossover() {
        final Layer mate = new Layer(Collections.emptyList());
        final Layer result = inputLayer.crossover(mate);
        assertEquals(inputLayer, result);
    }
}