package com.wniemiec.cheat.ptanks;

import java.util.function.Consumer;
import java.util.function.Supplier;

public enum TankAccessor {
    PLAYER_ONE {
        @Override
        double get(Supplier<Double> getter) {
            PocketTanksAccessor.accessPlayerOne();
            return getter.get();
        }

        @Override
        void set(Consumer<Double> setter, Double value) {
            PocketTanksAccessor.accessPlayerOne();
            setter.accept(value);
        }
    },
    PLAYER_TWO {
        @Override
        double get(Supplier<Double> getter) {
            PocketTanksAccessor.accessPlayerTwo();
            return getter.get();
        }

        @Override
        void set(Consumer<Double> setter, Double value) {
            PocketTanksAccessor.accessPlayerTwo();
            setter.accept(value);
        }
    };

    abstract double get(Supplier<Double> getter);

    abstract void set(Consumer<Double> setter, Double value);
}
