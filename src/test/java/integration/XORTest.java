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
package integration;

import com.clutteredcode.ann.neuron.InputNeuron;
import com.clutteredcode.ann.neuron.Neuron;
import com.clutteredcode.ann.activation.Activation;
import com.clutteredcode.ann.network.feedforward.InputLayer;
import com.clutteredcode.ann.network.feedforward.Layer;
import com.clutteredcode.ann.network.feedforward.NeuralNetwork;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author David Clutter
 */
public class XORTest {

    @Test
    public void xorTest() {
        // Input Layer
        final InputNeuron inputNeuron1 = new InputNeuron();
        final InputNeuron inputNeuron2 = new InputNeuron();
        final List<InputNeuron> inputNeuronList = Arrays.asList(inputNeuron1, inputNeuron2);
        final InputLayer inputLayer = new InputLayer(inputNeuronList);

        // Hidden Layer
        final Neuron hiddenNeuron1 = new Neuron(Activation.SIGMOID, 90.0, Arrays.asList(60.0, 60.0));
        final Neuron hiddenNeuron2 = new Neuron(Activation.SIGMOID, 40.0, Arrays.asList(80.0, 80.0));
        final List<Neuron> hiddenNeuronList = Arrays.asList(hiddenNeuron1, hiddenNeuron2);
        final Layer hiddenLayer = new Layer(hiddenNeuronList);

        // Output Layer
        final Neuron outputNeuron = new Neuron(Activation.SIGMOID, 30, Arrays.asList(-60.0, 60.0));
        final List<Neuron> outputNeuronList = Arrays.asList(outputNeuron);
        final Layer outputLayer = new Layer(outputNeuronList);

        // Network
        final List<Layer> layerList = Arrays.asList(inputLayer, hiddenLayer, outputLayer);
        final NeuralNetwork neuralNetwork = new NeuralNetwork(layerList);

        // Results
        final double r1 = neuralNetwork.fire(Arrays.asList(0.0, 0.0)).getLast().get(0); // 0.0000000000000936
        final double r2 = neuralNetwork.fire(Arrays.asList(1.0, 0.0)).getLast().get(0); // 0.9999999999999065
        final double r3 = neuralNetwork.fire(Arrays.asList(0.0, 1.0)).getLast().get(0); // 0.9999999999999065
        final double r4 = neuralNetwork.fire(Arrays.asList(1.0, 1.0)).getLast().get(0); // 0.0000000000000936

        // Validations
        assertEquals(0.0, r1, 0.000001);
        assertEquals(1.0, r2, 0.000001);
        assertEquals(1.0, r3, 0.000001);
        assertEquals(0.0, r4, 0.000001);
    }
}