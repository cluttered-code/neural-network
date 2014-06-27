package com.clutteredcode.ann;

import mockit.Mock;
import mockit.MockUp;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author cluttered.code@gmail.com
 */
public class FeedForwardNetworkTest {

    @Test
    public void testConstructor() {
        final List<FeedForwardLayer> layers = new ArrayList<>();

        final FeedForwardNetwork network = new FeedForwardNetwork(layers);

        assertEquals(layers, network.layers);
    }

    @Test
    public void testFire() {
        final double[] initial = new double[0];

        final List<FeedForwardLayer> layers = new ArrayList<>();
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));
        layers.add(new FeedForwardLayer(null));

        final FeedForwardNetwork network = new FeedForwardNetwork(layers);

        // Mock FeedForward fire response
        new MockUp<FeedForwardLayer>() {
            double[] array = initial;

            @Mock(invocations = 5)
            public double[] fire(final double[] inputs) {
                assertEquals(array, inputs);
                array = new double[array.length + 1];
                return array;
            }
        };

        final double[] outputs = network.fire(initial);

        assertEquals(5, outputs.length);
    }
}