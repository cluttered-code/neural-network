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

import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class FeedForwardNetworkTest {

    @Test
    public void testConstructor() {
        final List<FeedForwardLayer> layers = new ArrayList<>();

        final FeedForwardNetwork network = new FeedForwardNetwork(layers);

        assertEquals(layers, network.layers);
    }

    @Test
    public void testFire() {
        final double[] initial = new double[0];

        final List<FeedForwardLayer> layers = new ArrayList<>();
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));

        final FeedForwardNetwork network = new FeedForwardNetwork(layers);

        // Mock FeedForward fire response
        new MockUp<FeedForwardLayer>() {
            double[] array = initial;

            @Mock(invocations = 5)
            public double[] fire(final double[] inputs) {
                assertEquals(array, inputs);
                array = new double[array.length + 1];
                return array;
            }
        };

        final double[] outputs = network.fire(initial);

        assertEquals(5, outputs.length);
    }
}