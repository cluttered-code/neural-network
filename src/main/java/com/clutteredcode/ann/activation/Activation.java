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
public enum Activation implements ActivationFunction {
    LINEAR(input -> input),
    SIGMOID(input -> 1 / (1 + Math.exp(-input))),
    TAN_H(Math::tanh);

    private final ActivationFunction activationFunction;

    Activation(final ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
    }

    public double evaluate(final double input) {
        return activationFunction.evaluate(input);
    }

    public static Activation random() {
        return values()[(int) (Math.random() * values().length)];
    }
}