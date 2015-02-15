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
package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationType;

import java.util.Collections;

/**
 * @author David Clutter
 */
public class InputNeuron extends Neuron {

    public InputNeuron() {
        super(ActivationType.LINEAR, 0.0, Collections.singletonList(1.0));
    }

    @Override
    public InputNeuron mutate(final double rate) {
        throw new UnsupportedOperationException("Can't mutate an input neuron");
    }

    @Override
    public Neuron crossover(final Neuron mate) {
        throw new UnsupportedOperationException("Can't crossover an input neuron");
    }
}