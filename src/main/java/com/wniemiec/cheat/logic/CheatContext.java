package com.wniemiec.cheat.logic;

import com.wniemiec.cheat.neural.InputProperty;
import com.wniemiec.cheat.neural.Layer;
import com.wniemiec.cheat.neural.Network;
import com.wniemiec.cheat.neural.Neuron;
import com.wniemiec.cheat.ptanks.Tank;
import com.wniemiec.cheat.ptanks.TankAccessor;

public class CheatContext {
    private Tank playerOne = new Tank(TankAccessor.PLAYER_ONE);
    private Tank playerTwo = new Tank(TankAccessor.PLAYER_TWO);

    private Network network;

    CheatContext(InputProperty wind) {
//        buildNetwork();
    }

//    private void buildNetwork() {
//        network = Network.builder()
//                .addInputLayer(Layer.<InputProperty>builder()
//                        .add(new SomeInput(3.0))
//                        .add(new SomeInput(3.0))
//                        .add(new SomeInput(3.0))
//                        .alias("Input Layer")
//                        .build())
//                .generateHiddenLayer()
//                .addOutputLayer(Layer.<Neuron>builder()
//                        .add(new Neuron())
//                        .add(new Neuron())
//                        .alias("Output Layer")
//                        .build())
//                .enableAutoConnection()
//                .build();
//    }
}
