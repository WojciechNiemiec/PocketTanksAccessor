package com.wniemiec.cheat;

import com.wniemiec.cheat.neural.Layer;
import com.wniemiec.cheat.neural.Network;
import com.wniemiec.cheat.neural.Neuron;
import com.wniemiec.cheat.ptanks.PocketTanksAccessor;
import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.TankAccessor;
import com.wniemiec.cheat.ptanks.Tank;

public class App {
    public static void main( String[] args ) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println();

        try {
            getProperties();

            Network tankNetwork = Network.builder()
                    .addInputLayer(new Layer(new Neuron(), new Neuron(), new Neuron()))
                    .generateHiddenLayer()
                    .addOutputLayer(new Layer(new Neuron(), new Neuron(), new Neuron()))
                    .enableAutoConnection()
                    .build();

            tankNetwork.doPropagation();
            Layer outputLayer = tankNetwork.getOutputLayer();


        } finally {
            PocketTanksAccessor.closeProcess();
        }
    }

    private static void getProperties() {
        Tank player = new Tank(TankAccessor.PLAYER_ONE);
        Tank cpu = new Tank(TankAccessor.PLAYER_TWO);

        System.out.println("Wind: " + PocketTanksAccessor.getWindValue());

        System.out.println(player);
        System.out.println(cpu);

        player.setAngle(70);
        player.setPosition(new Position(10,10));

        System.out.println(player);
    }
}
