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
import static mockit.Deencapsulation.setField;

/**
 * @author cluttered.code@gmail.com
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
}