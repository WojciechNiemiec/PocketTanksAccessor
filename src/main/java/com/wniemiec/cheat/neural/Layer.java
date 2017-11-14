package com.wniemiec.cheat.neural;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Layer implements Serializable {

    private static final long serialVersionUID = -8678713589097858309L;

    private List<Neuron> neurons = new ArrayList<>();
    private Layer previous;
    private Layer next;

    public Layer() { }

    public Layer(Neuron... neurons) {
        add(neurons);
    }

    public void add(Neuron neuron) {
        neurons.add(neuron);
    }

    public void add(Neuron... neurons) {
        this.neurons.addAll(Arrays.asList(neurons));
    }

    public List<Neuron> getNeurons() {
        return neurons;
    }

    public Layer connect(Layer layer) {
        next = layer;
        next.previous = this;
        next.getNeurons().forEach(neuron -> neuron.connect(this.getNeurons()));

        return next;
    }

    public Layer getNext() {
        return next;
    }
}
