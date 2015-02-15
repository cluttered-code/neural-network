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

/**
 * @author David Clutter
 */
public enum ActivationType {
    LINEAR,
    SIGMOID,
    TAN_H;

    private static final ActivationFunction LINEAR_FUNCTION = input -> input;
    private static final ActivationFunction SIGMOID_FUNCTION = input -> 1 / (1 + Math.exp(-input));
    private static final ActivationFunction TAN_H_FUNCTION = Math::tanh;

    public ActivationFunction getActivationFunction() {
        if (this == TAN_H)
            return TAN_H_FUNCTION;

        if (this == SIGMOID)
            return SIGMOID_FUNCTION;

        return LINEAR_FUNCTION;
    }

    public static ActivationType random() {
        return values()[(int) (Math.random() * values().length)];
    }
}