package com.wniemiec.cheat.neural;

import java.util.Collection;

public interface Connector<N extends Neuron, P extends Inputable> {
    void connect(Layer<P> input, Layer<N> output, Collection<Layer<N>> hidden);
}
