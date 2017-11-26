package com.wniemiec.cheat.neural;

class HiddenLayerGenerator {

    private HiddenLayerGenerator() {}

    static Layer<Neuron> generate(Layer inputLayer, Layer outputLayer) {
        Layer.Builder<Neuron> builder = Layer.builder();
        for (int i = 0; i < countNeuronsNeeded(inputLayer, outputLayer); i++) {
            builder.add(new Neuron());
        }
        return builder.alias("Generated Hidden Layer").build();
    }

    private static int countNeuronsNeeded(Layer inputLayer, Layer outputLayer) {
        int inputSize = inputLayer.getElements().size();
        int outputSize = outputLayer.getElements().size();

        return (int) Math.sqrt(inputSize * outputSize);
    }
}