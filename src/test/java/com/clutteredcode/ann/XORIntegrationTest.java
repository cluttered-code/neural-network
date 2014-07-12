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
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class XORIntegrationTest {

    @Test
    public void xorTest() {
        // Input Layer
        final Neuron inputNeuron1 = new Neuron(ActivationType.LINEAR, 0.0, new double[]{1.0});
        final Neuron inputNeuron2 = new Neuron(ActivationType.LINEAR, 0.0, new double[]{1.0});
        final List<Neuron> inputNeuronList = Arrays.asList(inputNeuron1, inputNeuron2);
        final FeedForwardInputLayer inputLayer = new FeedForwardInputLayer(inputNeuronList);

        // Hidden Layer
        final Neuron hiddenNeuron1 = new Neuron(ActivationType.SIGMOID, -90, new double[]{60, 60});
        final Neuron hiddenNeuron2 = new Neuron(ActivationType.SIGMOID, -40, new double[]{80, 80});
        final List<Neuron> hiddenNeuronList = Arrays.asList(hiddenNeuron1, hiddenNeuron2);
        final FeedForwardLayer hiddenLayer = new FeedForwardLayer(hiddenNeuronList);

        // Output Layer
        final Neuron outputNeuron = new Neuron(ActivationType.SIGMOID, -30, new double[]{-60, 60});
        final List<Neuron> outputNeuronList = Arrays.asList(outputNeuron);
        final FeedForwardLayer outputLayer = new FeedForwardLayer(outputNeuronList);

        // Network
        final List<FeedForwardLayer> layerList = Arrays.asList(inputLayer, hiddenLayer, outputLayer);
        final FeedForwardNetwork network = new FeedForwardNetwork(layerList);

        // Results
        final double r1 = network.fire(new double[]{0.0, 0.0})[0]; // 0.0000000000000936
        final double r2 = network.fire(new double[]{1.0, 0.0})[0]; // 0.9999999999999065
        final double r3 = network.fire(new double[]{0.0, 1.0})[0]; // 0.9999999999999065
        final double r4 = network.fire(new double[]{1.0, 1.0})[0]; // 0.0000000000000936

        // Validations
        assertEquals(0, Math.round(r1));
        assertEquals(1, Math.round(r2));
        assertEquals(1, Math.round(r3));
        assertEquals(0, Math.round(r4));
    }
}