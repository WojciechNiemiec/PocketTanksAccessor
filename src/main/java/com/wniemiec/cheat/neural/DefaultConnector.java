package com.wniemiec.cheat.neural;

import java.util.Collection;
import java.util.LinkedList;

public class DefaultConnector implements Connector<Neuron, Inputable> {

    @Override
    public void connect(Layer<Inputable> input, Layer<Neuron> output, Collection<Layer<Neuron>> hidden) {
        LinkedList<Layer<Neuron>> hiddenLayers = new LinkedList<>(hidden);

        Layer<Neuron> last = input.connect(hiddenLayers.pollFirst());

        for (Layer<Neuron> layer: hiddenLayers) {
            last = last.connect(layer);
        }

        last.connect(output);
    }
}
