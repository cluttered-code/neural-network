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
package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationType;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class FeedForwardLayerTest {

    @Test
    public void testConstructor() {
        final List<Neuron> neurons = new ArrayList<>();
        neurons.add(new Neuron(ActivationType.SIGMOID, 34.3, new double[0]));

        final FeedForwardLayer layer = new FeedForwardLayer(neurons);

        assertEquals(neurons, layer.neurons);
    }

    @Test
    public void testFire() {
        final List<Neuron> neurons = new ArrayList<>();
        neurons.add(new Neuron(ActivationType.SIGMOID, 34.3, new double[0]));
        neurons.add(new Neuron(ActivationType.LINEAR, 67.8, new double[0]));
        neurons.add(new Neuron(ActivationType.TAN_H, 11.67, new double[0]));

        final FeedForwardLayer layer = new FeedForwardLayer(neurons);

        // Mock Neuron fire response
        new MockUp<Neuron>() {
            int count = 0;

            @Mock(invocations = 3)
            public double fire(final double[] inputs) {
                return ++count;
            }
        };

        final double[] outputs = layer.fire(new double[0]);

        for(int i = 0; i <neurons.size(); ++i) {
            assertEquals(1.0+i, outputs[i]);
        }
    }
}