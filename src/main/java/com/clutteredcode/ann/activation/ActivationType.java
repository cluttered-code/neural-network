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

/**
 * @author cluttered.code@gmail.com
 */
public enum ActivationType {
    LINEAR,
    SIGMOID,
    TAN_H;

    // Singleton lambda ActivationFunction objects
    private static final ActivationFunction LINEAR_FUNC = input -> input;
    private static final ActivationFunction SIGMOID_FUNC = input -> 1 / (1 + Math.exp(-input));
    private static final ActivationFunction TAN_H_FUNC = Math::tanh;

    /**
     * Returns the {@link com.clutteredcode.ann.activation.ActivationFunction} associated with this {@code ActivationType}.
     *
     * @return The {@link com.clutteredcode.ann.activation.ActivationFunction}.
     */
    public ActivationFunction getFunction() {
        if (this == TAN_H)
            return TAN_H_FUNC;

        if (this == SIGMOID)
            return SIGMOID_FUNC;

        return LINEAR_FUNC;
    }
}