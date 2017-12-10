package com.wniemiec.cheat;

import com.wniemiec.cheat.logic.PocketTanksCheater;
import com.wniemiec.cheat.neural.*;
import com.wniemiec.cheat.ptanks.PocketTanksAccessor;
import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.Tank;
import com.wniemiec.cheat.ptanks.TankAccessor;
import javafx.application.Application;
import lombok.extern.log4j.Log4j;

@Log4j
public class App {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println();

        Application.launch(PocketTanksCheater.class);

        Double[][] matrix = {{-1.0,-1.0,-1.0},
                            {-1.0,1.0,1.0},
                            {1.0,1.0,-1.0},
                            {1.0,-1.0,1.0}};

        Input a = new Input();
        Input b = new Input();

        Network network = net(a, b);

        for (int i = 0; i < 50000; i++){

            int vec = (int)Math.round(Math.random() * 3);

            Double[] vector = matrix[vec];
            Double err;

            a.set(vector[0]);
            b.set(vector[1]);

            network.doPropagation();
            Neuron result = getOutputNeuron(network);

            Double res = result.get();
            Double exp = vector[2];
            err = exp - res;

            result.doBackPropagation(err);

            network.doBackPropagation();
            network.updateNeuronInputWeights();
            b.set(vector[1]);
        }

        Double xor;

        a.set(-1.0);
        b.set(-1.0);
        network.doPropagation();
        xor = getOutputNeuron(network).get();

        System.out.println("-1 xor -1 = " + xor);

        a.set(-1.0);
        b.set(1.0);
        network.doPropagation();
        xor = getOutputNeuron(network).get();

        System.out.println("-1 xor 1 = " + xor);

        a.set(1.0);
        b.set(-1.0);
        network.doPropagation();
        xor = getOutputNeuron(network).get();

        System.out.println("1 xor -1 = " + xor);

        a.set(1.0);
        b.set(1.0);
        network.doPropagation();
        xor = getOutputNeuron(network).get();

        System.out.println("1 xor 1 = " + xor);

//        try {
//            getProperties();
//        } finally {
//            PocketTanksAccessor.closeProcess();
//        }
    }

    private static Network net(Inputable a, Inputable b) {
        return Network.builder()
                .addInputLayer(Layer.builder()
                        .add(a)
                        .add(b)
                        .alias("input")
                        .build())
                .addHiddenLayer(Layer.<Neuron>builder()
                        .add(new Neuron())
                        .add(new Neuron())
                        .alias("hidden")
                        .build())
                .addOutputLayer(Layer.<Neuron>builder()
                        .add(new Neuron())
                        .alias("output")
                        .build())
                .enableAutoConnection()
                .build();
    }

    private static Neuron getOutputNeuron(Network network) {
        return network.getOutputLayer().getElements().stream().findAny().orElseThrow(IllegalStateException::new);
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

class Input implements Inputable {

    private Double d;

    public void set(Double d) {
        this.d = d;
    }

    @Override
    public Double get() {
        return d;
    }
}