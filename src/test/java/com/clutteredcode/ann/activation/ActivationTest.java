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
package com.clutteredcode.ann.activation;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author David Clutter
 */
public class ActivationTest {

    @Test
    public void testRandomLinear() {
        new MockUp<Math>() {
            @Mock(invocations = 1)
            @SuppressWarnings("unused")
            double random() {
                return 0.0;
            }
        };

        final Activation activation = Activation.random();

        assertEquals(Activation.LINEAR, activation);
    }

    @Test
    public void testRandomSigmoid() {
        new MockUp<Math>() {
            @Mock(invocations = 1)
            @SuppressWarnings("unused")
            double random() {
                return 0.35;
            }
        };

        final Activation activation = Activation.random();

        assertEquals(Activation.SIGMOID, activation);
    }

    @Test
    public void testRandomHyperbolicTangent() {
        new MockUp<Math>() {
            @Mock(invocations = 1)
            @SuppressWarnings("unused")
            double random() {
                return 0.75;
            }
        };

        final Activation activation = Activation.random();

        assertEquals(Activation.TAN_H, activation);
    }

    @Test
    public void testValueOfLinear() {
        final String linear = "LINEAR";
        final Activation activation = Activation.valueOf(linear);

        assertEquals(Activation.LINEAR, activation);
    }

    @Test
    public void testValueOfSigmoid() {
        final String sigmoid = "SIGMOID";
        final Activation activation = Activation.valueOf(sigmoid);

        assertEquals(Activation.SIGMOID, activation);
    }

    @Test
    public void testValueOfHyperbolicTangent() {
        final String tanH = "TAN_H";
        final Activation activation = Activation.valueOf(tanH);

        assertEquals(Activation.TAN_H, activation);
    }
}