package com.wniemiec.cheat.logic;

import com.wniemiec.cheat.neural.Network;
import com.wniemiec.cheat.ptanks.Tank;
import com.wniemiec.cheat.ptanks.TankAccessor;

public class CheatContext {
    private Tank playerOne = new Tank(TankAccessor.PLAYER_ONE);
    private Tank playerTwo = new Tank(TankAccessor.PLAYER_TWO);

    private Network network;

    CheatContext() {
//        network = Network.builder()
//                .addInputLayer(Layer.builder()
//                        .add(new Neuron())
//                        .add(new Neuron())
//                        .add(new Neuron())
//                        .build())
//                .generateHiddenLayer()
//                .addOutputLayer(Layer.builder()
//                        .add(new Neuron())
//                        .add(new Neuron())
//                        .build())
//                .enableAutoConnection()
//                .build();
    }
}
