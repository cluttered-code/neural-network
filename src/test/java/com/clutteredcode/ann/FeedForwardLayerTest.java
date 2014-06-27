package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationType;
import junit.framework.Assert;
import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

/**
 * @author cluttered.code@gmail.com
 */
public class FeedForwardLayerTest {

    @Test
    public void testConstructor() {
        final List<Neuron> neurons = new ArrayList<>();
        neurons.add(new Neuron(ActivationType.SIGMOID, 34.3, new double[0]));

        final FeedForwardLayer layer = new FeedForwardLayer(neurons);

        assertEquals(neurons, layer.neurons);
    }

    @Test
    public void testFire() {
        final List<Neuron> neurons = new ArrayList<>();
        neurons.add(new Neuron(ActivationType.SIGMOID, 34.3, new double[0]));
        neurons.add(new Neuron(ActivationType.LINEAR, 67.8, new double[0]));
        neurons.add(new Neuron(ActivationType.TAN_H, 11.67, new double[0]));

        final FeedForwardLayer layer = new FeedForwardLayer(neurons);

        // Mock Neuron dotProduct
        new MockUp<Neuron>() {

            int count = 0;

            @Mock(invocations = 3)
            public double fire(final double[] inputs) {
                return ++count;
            }
        };

        final double[] outputs = layer.fire(new double[0]);

        for(int i = 0; i <neurons.size(); ++i) {
            assertEquals(i+1.0, outputs[i]);
        }
    }
}