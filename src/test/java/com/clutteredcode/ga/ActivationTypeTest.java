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
package com.clutteredcode.ga;

import com.clutteredcode.ann.activation.ActivationType;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class ActivationTypeTest {

    @Test
    public void testRandom() throws NoSuchFieldException, IllegalAccessException {
        final Random testRandom = new Random(12345);

        Field field = Math.class.getDeclaredField("randomNumberGenerator");
        field.setAccessible(true);
        field.set(null, testRandom);

        assertEquals(null, ActivationType.random());
    }

}
