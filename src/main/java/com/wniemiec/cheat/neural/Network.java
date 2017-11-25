package com.wniemiec.cheat.neural;

import org.apache.commons.lang3.BooleanUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Network implements Serializable {

    private static final long serialVersionUID = -7792455019136293876L;

    private Layer<InputProperty> inputLayer;
    private Layer<Neuron> outputLayer;

    public Layer getInputLayer() {
        return inputLayer;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public void doPropagation() {
        if (BooleanUtils.isFalse(inputLayer.hasNext())) {
            throw new IllegalStateException("Neural network does not contain any Neural layer");
        }

        Layer<Neuron> layer = inputLayer.next();

        while (Objects.nonNull(layer)) {
            layer.getInputs().forEach(Neuron::get);
            layer = layer.next();
        }
    }

    public void doBackPropagation() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Layer<InputProperty> inputLayer;
        private List<Layer<Neuron>> hiddenLayers = new LinkedList<>();
        private Layer<Neuron> outputLayer;
        private boolean autoConnectionEnabled;
        private boolean useGeneratedHiddenLayer;

        public Builder addInputLayer(Layer<InputProperty> layer) {
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
            network.outputLayer = outputLayer;

            if (useGeneratedHiddenLayer) {
                //TODO: log
                hiddenLayers = Collections.singletonList(new HiddenLayerGenerator().generate(inputLayer, outputLayer));
            }

            if (autoConnectionEnabled) {
                prepareConnections();
            }

            return network;
        }

        private void prepareConnections() {
            DefaultConnector connector = new DefaultConnector();
            connector.connect(inputLayer, outputLayer, hiddenLayers);
        }
    }
}
