package com.wniemiec.cheat.neural;

public interface ValueCompressor {
    Double compress(Double value);

    Double decompress(Double value);
}
