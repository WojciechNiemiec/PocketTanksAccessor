package com.wniemiec.cheat;

import com.wniemiec.cheat.neural.*;
import com.wniemiec.cheat.ptanks.PocketTanksAccessor;
import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.Tank;
import com.wniemiec.cheat.ptanks.TankAccessor;
import lombok.extern.log4j.Log4j;

@Log4j
public class App {
    public static void main(String[] args) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println();


        Double[][] matrix = {{-1.0,-1.0,-1.0},
                            {-1.0,1.0,1.0},
                            {1.0,-1.0,1.0},
                            {1.0,1.0,-1.0}};

        Input a = new Input();
        Input b = new Input();

        Network network = net(a, b);

        for (int i = 0; i < 100; i++) {
            if (i == 95) {
                i++;
            }

            for (Double[] vector: matrix) {
                a.set(vector[0]);
                b.set(vector[1]);

                network.doPropagation();
                Neuron result = getOutputNeuron(network);

                Double res = result.get();
                Double exp = vector[2];
                Double err = exp - res;
                result.setError(err);

                network.doBackPropagation();
                network.updateNeuronInputWeights();
            }
        }


//        Network network = Network.builder()
//                .addInputLayer(Layer.builder()
//                        .add((Inputable) () -> 1.0)
//                        .add((Inputable) () -> 1.0)
//                        .add((Inputable) () -> 1.0)
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
//
//        network.doPropagation();
//        network.getOutputLayer().getElements().forEach(neuron -> System.out.println(neuron.getOutputNeuron() + ", "));


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

class SomeInput extends InputProperty {

    private static final long serialVersionUID = -3478919047247569847L;
    private Double val;

    SomeInput(Double val) {
        super(new BipolarValueCompresser(300.0));
        this.val = val;
    }

    @Override
    protected Double getActualValue() {
        return val;
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