package com.wniemiec.cheat.neural;

import java.util.Collection;
import java.util.LinkedList;

public class DefaultConnector implements Connector<Neuron, Inputable> {

    @Override
    public void connect(Layer<Inputable> input, Layer<Neuron> output, Collection<Layer<Neuron>> hidden) {
        LinkedList<Layer<Neuron>> neuronLayers = new LinkedList<>(hidden);
        neuronLayers.addLast(output);

        Layer<Neuron> last = input.connect(neuronLayers.pollFirst());

        for (Layer<Neuron> layer: neuronLayers) {
            last = last.connect(layer);
        }
    }
}
