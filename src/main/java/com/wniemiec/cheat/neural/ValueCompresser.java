package com.wniemiec.cheat.neural;

public interface ValueCompresser {
    Double compress(Double value);

    Double decompress(Double value);
}
