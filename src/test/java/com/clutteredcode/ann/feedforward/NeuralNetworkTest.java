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

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.Tested;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static mockit.Deencapsulation.setField;

/**
 * @author cluttered.code@gmail.com
 */
public class NeuralNetworkTest {

    @Tested
    @SuppressWarnings("unused")
    private NeuralNetwork neuralNetwork;

    @Injectable
    @SuppressWarnings("unused")
    private List<Layer> layers;

    @Test
    @SuppressWarnings("unchecked")
    public void testFire(@Mocked final Layer layer) {
        final List<Layer> localLayers = Arrays.asList(layer, layer, layer);
        setField(neuralNetwork, "layers", localLayers);

        new Expectations() {{
            layer.fire((List<Double>) any); times = 3;
        }};

        neuralNetwork.fire(new ArrayList());
    }
}