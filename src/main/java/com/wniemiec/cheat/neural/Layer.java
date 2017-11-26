package com.wniemiec.cheat.neural;

import java.io.Serializable;
import java.util.*;

public class Layer<T extends Inputable> implements Serializable {

    private static final long serialVersionUID = -8678713589097858309L;

    private List<T> elements;
    private String alias;

    Layer(Collection<T> elements) {
        this.elements = new ArrayList<>(elements);
    }

    public List<T> getElements() {
        return elements;
    }

    Layer<Neuron> connect(Layer<Neuron> layer) {
        layer.getElements().forEach(neuron -> neuron.connect(this.getElements()));
        return layer;
    }

    public static <T extends Inputable> Builder<T> builder() {
        return new Builder<>();
    }

    public static class Builder<T extends Inputable> {
        private List<T> inputs = new ArrayList<>();
        private String alias;

        public Builder<T> add(T input) {
            inputs.add(input);
            return this;
        }

        public Builder<T> alias(String alias) {
            this.alias = alias;
            return this;
        }

        public Layer<T> build() {
            Layer<T> layer = new Layer<>(inputs);
            layer.alias = alias;
            return layer;
        }
    }
}
