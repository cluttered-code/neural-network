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
package com.clutteredcode.ann.activation;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class ActivationTest {

    @Test
    public void testLinearActivationFunction() {
        final ActivationFunction af = ActivationType.LINEAR.getFunction();

        assertEquals(-374.25, af.activate(-374.25));
        assertEquals(-25.687, af.activate(-25.687));
        assertEquals(0.0, af.activate(0.0));
        assertEquals(15.923, af.activate(15.923));
        assertEquals(89.34, af.activate(89.34));
    }

    @Test
    public void testHyperbolicTangentActivationFunction() {
        final ActivationFunction af = ActivationType.TAN_H.getFunction();
        final ActivationFunction expected = Math::tanh;

        assertEquals(expected.activate(-374.25), af.activate(-374.25));
        assertEquals(expected.activate(-25.687), af.activate(-25.687));
        assertEquals(expected.activate(0.0), af.activate(0.0));
        assertEquals(expected.activate(15.923), af.activate(15.923));
        assertEquals(expected.activate(89.34), af.activate(89.34));
    }

    @Test
    public void testSigmoidActivationFunction() {
        final ActivationFunction af = ActivationType.SIGMOID.getFunction();
        final ActivationFunction expected = (final double input) -> 1 / (1 + Math.exp(-input));

        assertEquals(expected.activate(-374.25), af.activate(-374.25));
        assertEquals(expected.activate(-25.687), af.activate(-25.687));
        assertEquals(expected.activate(0.0), af.activate(0.0));
        assertEquals(expected.activate(15.923), af.activate(15.923));
        assertEquals(expected.activate(89.34), af.activate(89.34));
    }
}