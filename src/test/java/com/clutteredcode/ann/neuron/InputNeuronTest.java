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
package com.clutteredcode.ann.neuron;

import com.clutteredcode.ann.activation.Activation;
import com.clutteredcode.ann.neuron.InputNeuron;
import com.clutteredcode.ann.neuron.Neuron;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import static mockit.Deencapsulation.getField;
import static org.junit.Assert.assertEquals;

/**
 * @author David Clutter
 */
@RunWith(JMockit.class)
public class InputNeuronTest {

    @Tested
    @SuppressWarnings("unused")
    private InputNeuron inputNeuron;

    @Test
    public void testConstructor() {
        final Activation activation = getField(inputNeuron, "activation");
        final double bias = getField(inputNeuron, "bias");
        final List<Double> inputWeights = getField(inputNeuron, "inputWeights");

        assertEquals(Activation.LINEAR, activation);
        assertEquals(0.0, bias, 0.0);
        assertEquals(Collections.singletonList(1.0), inputWeights);
    }

    @Test
    public void testMutate() {
        final Neuron result = inputNeuron.mutate(Math.PI);
        assertEquals(inputNeuron, result);
    }

    @Test
    public void testCrossover() {
        final Neuron mate = new Neuron(Activation.random(), 500);
        final Neuron result = inputNeuron.crossover(mate);
        assertEquals(inputNeuron, result);
    }
}