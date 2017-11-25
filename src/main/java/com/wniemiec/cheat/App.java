package com.wniemiec.cheat;

import com.wniemiec.cheat.neural.*;
import com.wniemiec.cheat.ptanks.PocketTanksAccessor;
import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.Tank;
import com.wniemiec.cheat.ptanks.TankAccessor;
import lombok.extern.log4j.Log4j;

@Log4j
public class App {
    public static void main( String[] args ) {
        System.out.println(System.getProperty("java.library.path"));
        System.out.println();

        Network network = Network.builder()
                .addInputLayer(Layer.builder()
//                        .add(new SomeInput(3.0))
//                        .add(new SomeInput(3.0))
//                        .add(new SomeInput(3.0))
                        .alias("Input Layer")
                        .build())
                .generateHiddenLayer()
                .addOutputLayer(Layer.<Neuron>builder()
                        .add(new Neuron())
                        .add(new Neuron())
                        .alias("Output Layer")
                        .build())
                .enableAutoConnection()
                .build();

        network.doPropagation();
        network.getOutputLayer();


//        try {
//            getProperties();
//        } finally {
//            PocketTanksAccessor.closeProcess();
//        }
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

//class SomeInput extends InputProperty {
//
//    private static final long serialVersionUID = -3478919047247569847L;
//    private Double val;
//
//    SomeInput(Double val) {
//        super(new BipolarValueCompresser(300.0));
//        this.val = val;
//    }
//
//    @Override
//    protected Double getActualValue() {
//        return val;
//    }
//}