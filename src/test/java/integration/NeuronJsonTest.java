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
package integration;

import com.clutteredcode.ann.activation.Activation;
import com.clutteredcode.ann.neuron.Neuron;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.SimpleLoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static mockit.Deencapsulation.getField;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * @author David Clutter
 */
public class NeuronJsonTest {

    private static final Logger LOG = LoggerFactory.getLogger(NeuronJsonTest.class);

    @Test
    public void testNeuronToJson() {
        final Neuron neuron = new Neuron(Activation.SIGMOID, 12345.0, Collections.emptyList());

        final String json = neuron.toJson();
        LOG.info(json);

        assertThat(json, containsString("\"activation\":\"" + Activation.SIGMOID + "\""));
        assertThat(json, containsString("\"bias\":" + 12345.0));
        assertThat(json, containsString("\"inputWeights\":[]"));
        assertThat(json, not(containsString("\"random\"")));
    }

    @Test
    public void testNeuronFromJson() throws URISyntaxException, IOException {
        final List<Double> expectedInputWeights = Arrays.asList(1.1, 2.2, 3.3, 4.4, 5.5);
        final URI fileURI = this.getClass().getClassLoader().getResource("neuron.json").toURI();
        final byte[] bytes = Files.readAllBytes(Paths.get(fileURI));
        final String json = new String(bytes, StandardCharsets.UTF_8);

        final Neuron neuron = Neuron.fromJson(json);

        assertEquals(Activation.LINEAR, getField(neuron, "activation"));
        assertEquals(2525.0, getField(neuron, "bias"), 0.0);
        assertEquals(expectedInputWeights, getField(neuron, "inputWeights"));
        assertNotNull(getField(neuron, "random"));
    }
}