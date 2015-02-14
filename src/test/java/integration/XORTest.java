package integration;

import com.clutteredcode.ann.InputNeuron;
import com.clutteredcode.ann.Neuron;
import com.clutteredcode.ann.activation.ActivationType;
import com.clutteredcode.ann.feedforward.InputLayer;
import com.clutteredcode.ann.feedforward.Layer;
import com.clutteredcode.ann.feedforward.NeuralNetwork;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author David Clutter
 */
public class XORTest {

    @Test
    public void xorTest() {
        // Input Layer
        final InputNeuron  inputNeuron1 = new InputNeuron();
        final InputNeuron inputNeuron2 = new InputNeuron();
        final List<InputNeuron> inputNeuronList = Arrays.asList(inputNeuron1, inputNeuron2);
        final InputLayer inputLayer = new InputLayer(inputNeuronList);

        // Hidden Layer
        final Neuron hiddenNeuron1 = new Neuron(ActivationType.SIGMOID, 90.0, Arrays.asList(60.0, 60.0));
        final Neuron hiddenNeuron2 = new Neuron(ActivationType.SIGMOID, 40.0, Arrays.asList(80.0, 80.0));
        final List<Neuron> hiddenNeuronList = Arrays.asList(hiddenNeuron1, hiddenNeuron2);
        final Layer hiddenLayer = new Layer(hiddenNeuronList);

        // Output Layer
        final Neuron outputNeuron = new Neuron(ActivationType.SIGMOID, 30, Arrays.asList(-60.0, 60.0));
        final List<Neuron> outputNeuronList = Arrays.asList(outputNeuron);
        final Layer outputLayer = new Layer(outputNeuronList);

        // Network
        final List<Layer> layerList = Arrays.asList(inputLayer, hiddenLayer, outputLayer);
        final NeuralNetwork neuralNetwork = new NeuralNetwork(layerList);

        // Results
        final double r1 = neuralNetwork.fire(Arrays.asList(0.0, 0.0)).getLast().get(0); // 0.0000000000000936
        final double r2 = neuralNetwork.fire(Arrays.asList(1.0, 0.0)).getLast().get(0); // 0.9999999999999065
        final double r3 = neuralNetwork.fire(Arrays.asList(0.0, 1.0)).getLast().get(0); // 0.9999999999999065
        final double r4 = neuralNetwork.fire(Arrays.asList(1.0, 1.0)).getLast().get(0); // 0.0000000000000936

        // Validations
        assertEquals(0, Math.round(r1));
        assertEquals(1, Math.round(r2));
        assertEquals(1, Math.round(r3));
        assertEquals(0, Math.round(r4));
    }
}