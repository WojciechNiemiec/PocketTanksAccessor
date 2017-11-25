package com.wniemiec.cheat.neural;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Network implements Serializable {

    private static final long serialVersionUID = -7792455019136293876L;

    private Layer<Inputable> inputLayer;
    private LinkedList<Layer<Neuron>> hiddenLayers;
    private Layer<Neuron> outputLayer;

    public Layer getInputLayer() {
        return inputLayer;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public void doPropagation() {
        hiddenLayers.forEach(neuronLayer -> neuronLayer.getInputs()
                .forEach(Neuron::doPropagation));
    }

    //TODO: Init errors for output layer firs
    public void doBackPropagation() {
        Iterator<Layer<Neuron>> descendingIterator = hiddenLayers.descendingIterator();

        while (descendingIterator.hasNext()) {
            Layer<Neuron> next = descendingIterator.next();
            next.getInputs().forEach(Neuron::doBackPropagation);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Layer<Inputable> inputLayer;
        private LinkedList<Layer<Neuron>> hiddenLayers = new LinkedList<>();
        private Layer<Neuron> outputLayer;
        private boolean autoConnectionEnabled;
        private boolean useGeneratedHiddenLayer;

        public Builder addInputLayer(Layer<Inputable> layer) {
            inputLayer = layer;
            return this;
        }

        public Builder addHiddenLayer(Layer<Neuron> layer) {
            hiddenLayers.add(layer);
            return this;
        }

        public Builder generateHiddenLayer() {
            useGeneratedHiddenLayer = true;
            return this;
        }

        public Builder addOutputLayer(Layer<Neuron> layer) {
            outputLayer = layer;
            return this;
        }

        public Builder enableAutoConnection() {
            this.autoConnectionEnabled = true;
            return this;
        }

        public Network build() {
            Network network = new Network();
            network.inputLayer = inputLayer;
            network.hiddenLayers = getHiddenLayers();
            network.outputLayer = outputLayer;

            if (autoConnectionEnabled) {
                prepareConnections();
            }

            return network;
        }

        private LinkedList<Layer<Neuron>> getHiddenLayers() {
            if (useGeneratedHiddenLayer) {
                return new LinkedList<>(Collections.singletonList(
                        HiddenLayerGenerator.generate(inputLayer, outputLayer)));
            } else {
                return hiddenLayers;
            }
        }

        private void prepareConnections() {
            DefaultConnector connector = new DefaultConnector();
            connector.connect(inputLayer, outputLayer, hiddenLayers);
        }
    }
}
