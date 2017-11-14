package com.wniemiec.cheat.neural;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Network implements Serializable {

    private static final long serialVersionUID = -7792455019136293876L;

    private Layer inputLayer;

    private Layer outputLayer;

    public Layer getInputLayer() {
        return inputLayer;
    }

    public Layer getOutputLayer() {
        return outputLayer;
    }

    public void doPropagation() {

    }

    public void doBackPropagation() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Layer inputLayer;
        private List<Layer> hiddenLayers = new LinkedList<>();
        private Layer outputLayer;
        private boolean autoConnectionEnabled;
        private boolean useGeneratedHiddenLayer;

        public Builder addInputLayer(Layer layer) {
            inputLayer = layer;
            return this;
        }

        public Builder addHiddenLayer(Layer layer) {
            hiddenLayers.add(layer);
            return this;
        }

        public Builder generateHiddenLayer() {
            useGeneratedHiddenLayer = true;
            return this;
        }

        public Builder addOutputLayer(Layer layer) {
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
