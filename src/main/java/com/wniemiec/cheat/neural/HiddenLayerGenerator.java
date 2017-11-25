package com.wniemiec.cheat.neural;

class HiddenLayerGenerator {

    Layer<Neuron> generate(Layer inputLayer, Layer outputLayer) {
        Layer.Builder<Neuron> builder = Layer.builder();
        for (int i = 0; i < countNeuronsNeeded(inputLayer, outputLayer); i++) {
            builder.add(new Neuron());
        }
        return builder.alias("Generated Hidden Layer").build();
    }

    private int countNeuronsNeeded(Layer inputLayer, Layer outputLayer) {
        int inputSize = inputLayer.getInputs().size();
        int outputSize = outputLayer.getInputs().size();

        return (int) Math.sqrt(inputSize * outputSize);
    }
}