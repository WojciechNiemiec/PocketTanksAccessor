package com.wniemiec.cheat.neural;

import java.util.Collection;

public class DefaultConnector implements Connector {

    @Override
    public void connect(Layer input, Layer output, Collection<Layer> hidden) {
        Layer last = input;

        for (Layer layer: hidden) {
            last = last.connect(layer);
        }

        last.connect(output);
    }
}
