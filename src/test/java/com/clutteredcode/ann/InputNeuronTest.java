package com.clutteredcode.ann;

import com.clutteredcode.ann.activation.ActivationType;
import mockit.Tested;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static mockit.Deencapsulation.getField;

/**
 * @author David Clutter
 */
public class InputNeuronTest {

    @Tested
    @SuppressWarnings("unused")
    private InputNeuron inputNeuron;

    @Test
    public void testConstructor() {
        final ActivationType activationType = getField(inputNeuron, "activationType");
        final double bias = getField(inputNeuron, "bias");
        final List<Double> weights = getField(inputNeuron, "weights");

        assertEquals(ActivationType.LINEAR, activationType);
        assertEquals(0.0, bias);
        assertEquals(Collections.singletonList(1.0), weights);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testMutate() {
        inputNeuron.mutate(Math.PI);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testCrossover() {
        inputNeuron.crossover(inputNeuron);
    }
}