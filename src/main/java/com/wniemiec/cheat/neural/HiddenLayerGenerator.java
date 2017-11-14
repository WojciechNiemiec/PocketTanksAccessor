package com.wniemiec.cheat.neural;

class HiddenLayerGenerator {

    Layer generate(Layer inputLayer, Layer outputLayer) {
        Layer layer = new Layer();
        for (int i = 0; i < countNeuronsNeeded(inputLayer, outputLayer); i++) {
            layer.add(new Neuron());
        }
        return layer;
    }

    private int countNeuronsNeeded(Layer inputLayer, Layer outputLayer) {
        int inputSize = inputLayer.getNeurons().size();
        int outputSize = outputLayer.getNeurons().size();

        return (int) Math.sqrt(inputSize * outputSize);
    }
}