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
import mockit.Tested;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static mockit.Deencapsulation.getField;

/**
 * @author cluttered.code@gmail.com
 */
public class InputNeuronTest {

    @Tested
    @SuppressWarnings("unused")
    private InputNeuron inputNeuron;

    @Test
    public void testConstructor() {
        final ActivationType activationType = getField(inputNeuron, "activationType");
        final double bias = getField(inputNeuron, "bias");
        final List<Double> weights = getField(inputNeuron, "weights");

        assertEquals(ActivationType.LINEAR, activationType);
        assertEquals(0.0, bias);
        assertEquals(Collections.singletonList(1.0), weights);
    }
}