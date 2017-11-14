package com.wniemiec.cheat.neural;

import java.util.Collection;

public interface Connector {
    void connect(Layer input, Layer output, Collection<Layer> hidden);
}
