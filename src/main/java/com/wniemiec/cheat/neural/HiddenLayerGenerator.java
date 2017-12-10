package com.wniemiec.cheat.neural;

class HiddenLayerGenerator {

    private HiddenLayerGenerator() {}

    static Layer<Neuron> generate(int neuronsNeeded) {
        Layer.Builder<Neuron> builder = Layer.builder();
        for (int i = 0; i < neuronsNeeded; i++) {
            builder.add(new Neuron());
        }
        return builder.alias("Generated Hidden Layer").build();
    }

    static Layer<Neuron> generate(Layer inputLayer, Layer outputLayer) {
        return generate(countNeuronsNeeded(inputLayer, outputLayer));
    }

    private static int countNeuronsNeeded(Layer inputLayer, Layer outputLayer) {
        int inputSize = inputLayer.getElements().size();
        int outputSize = outputLayer.getElements().size();

        return (int) Math.sqrt(inputSize * outputSize);
    }
}