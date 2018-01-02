package com.wniemiec.cheat.neural;

import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.BooleanUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class Network implements Serializable {

    private static final long serialVersionUID = -7792455019136293876L;

    private Layer<Inputable> inputLayer;
    private LinkedList<Layer<Neuron>> hiddenLayers;
    private Layer<Neuron> outputLayer;

    public Layer<Inputable> getInputLayer() {
        return inputLayer;
    }

    public Layer<Neuron> getOutputLayer() {
        return outputLayer;
    }

    public void doPropagation() {
        hiddenLayers.forEach(this::doPropagation);
        doPropagation(outputLayer);
    }

    public void doBackPropagationForOutput(Double error) {

    }

    public void doBackPropagation() {
        Iterator<Layer<Neuron>> descendingIterator = hiddenLayers.descendingIterator();

        while (descendingIterator.hasNext()) {
            Layer<Neuron> next = descendingIterator.next();
            next.getElements().forEach(Neuron::doBackPropagation);
        }
    }
    public void updateNeuronInputWeights() {
        hiddenLayers.forEach(this::updateNeuronInputWeights);
        updateNeuronInputWeights(outputLayer);
    }

    private void doPropagation(Layer<Neuron> neuronLayer) {
        neuronLayer.getElements()
                .forEach(Neuron::doPropagation);
    }

    private void updateNeuronInputWeights(Layer<Neuron> neuronLayer) {
        neuronLayer.getElements()
                .forEach(Neuron::updateInputWeights);
    }

    public static Builder builder() {
        return new Builder();
    }

    @Log4j
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
                prepareConnections(network);
            }

            return network;
        }

        private LinkedList<Layer<Neuron>> getHiddenLayers() {
            if (useGeneratedHiddenLayer) {
                if (BooleanUtils.isFalse(hiddenLayers.isEmpty())) {
                    log.warn("Prepared hidden layers would be overridden by generated single layer");
                }

                return new LinkedList<>(Collections.singletonList(
                        HiddenLayerGenerator.generate(inputLayer, outputLayer)));
            } else {
                return hiddenLayers;
            }
        }

        private void prepareConnections(Network network) {
            DefaultConnector connector = new DefaultConnector();
            connector.connect(network.inputLayer, network.outputLayer, network.hiddenLayers);
        }
    }
}
