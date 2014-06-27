package com.clutteredcode.ann;

import java.util.List;

/**
 * @author cluttered.code@gmail.com
 */
public class FeedForwardNetwork {

    /* ******** Instance Variables ******** */
    protected List<FeedForwardLayer> layers;

    /**
     * Complete Constructor creates a {@code FeedForwardNetwork} instance with the specified parameters.
     *
     * @param layers The {@link com.clutteredcode.ann.FeedForwardLayer} objects in this {@code FeedForwardNetwork}.
     */
    public FeedForwardNetwork(final List<FeedForwardLayer> layers) {
        this.layers = layers;
    }

    /**
     * Fire each {@link com.clutteredcode.ann.FeedForwardLayer}, piping the output of the previous into the next.
     *
     * @param inputs The inputs to be put into the first {@link com.clutteredcode.ann.FeedForwardLayer}.
     * @return The outputs of the last {@link com.clutteredcode.ann.FeedForwardLayer}.
     */
    public double[] fire(final double[] inputs) {
        double[] currentInputs = inputs;

        for (final FeedForwardLayer layer : layers)
            currentInputs = layer.fire(currentInputs);

        return currentInputs;
    }
}