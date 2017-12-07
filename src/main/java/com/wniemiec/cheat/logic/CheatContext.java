package com.wniemiec.cheat.logic;

import com.wniemiec.cheat.neural.*;
import com.wniemiec.cheat.ptanks.PocketTanksAccessor;
import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.Tank;
import com.wniemiec.cheat.ptanks.TankAccessor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

class CheatContext {
    private static final Double MAX_DISTANCE = 800.0;
    private static final Double MIN_WIND = -100.0;
    private static final Double MAX_WIND = 100.0;
    private static final Double MAX_POWER = 800.0;
    private static final Double MAX_ANGLE = 180.0;

    @Getter
    private Tank playerOne = new Tank(TankAccessor.PLAYER_ONE);
    @Getter
    private Tank playerTwo = new Tank(TankAccessor.PLAYER_TWO);

    private Neuron powerNeuron = new Neuron();
    private Neuron angleNeuron = new Neuron();

    private Network network;
    private List<LearningVector> learningVectors = new ArrayList<>();

    @Getter
    private Double horizontalDistance;
    @Getter
    private Double verticalDistance;
    @Getter
    private Double windSpeed;

    @Getter
    private Double countedPower;
    @Getter
    private Double countedAngle;

    private ValueCompressor distanceCompressor = new BipolarValueCompressor(MAX_DISTANCE);
    private ValueCompressor windCompressor = new BipolarValueCompressor(MIN_WIND, MAX_WIND);
    private ValueCompressor powerCompressor = new BipolarValueCompressor(MAX_POWER);
    private ValueCompressor angleCompressor = new BipolarValueCompressor(MAX_ANGLE);

    CheatContext() {
        Inputable horizontalDistanceInput = () -> distanceCompressor.compress(horizontalDistance);
        Inputable verticalDistanceInput = () -> distanceCompressor.compress(verticalDistance);
        Inputable windInput = () -> windCompressor.compress(windSpeed);

        network = Network.builder()
                .addInputLayer(Layer.builder()
                        .add(horizontalDistanceInput)
                        .add(verticalDistanceInput)
                        .add(windInput)
                        .build())
                .generateHiddenLayer()
                .addOutputLayer(Layer.<Neuron>builder()
                        .add(powerNeuron)
                        .add(angleNeuron)
                        .build())
                .enableAutoConnection()
                .build();
    }

    void addLearningVector(LearningVector vector) {
        learningVectors.add(vector);
    }

    void teach() {
        for (int i = 0; i < 50000; i++){
            int vec = (int)Math.round(Math.random() * learningVectors.size() - 1);

            LearningVector vector = learningVectors.get(vec);

            horizontalDistance = vector.getHorizontalDistance();
            verticalDistance = vector.getVerticalDistance();
            windSpeed = vector.getWindSpeed();

            network.doPropagation();

            powerNeuron.doBackPropagation(powerNeuron.get() - vector.getCountedPower());
            angleNeuron.doBackPropagation(angleNeuron.get() - vector.getCountedAngle());

            network.doBackPropagation();
            network.updateNeuronInputWeights();
        }

    }

    void refreshProperties() {
        updateInputs();
        network.doPropagation();
        updateOutputs();
    }

    private void updateInputs() {
        Position one = playerOne.getPosition();
        Position two = playerTwo.getPosition();

        horizontalDistance = one.x - two.x;
        verticalDistance = one.y - two.y;
        windSpeed = PocketTanksAccessor.getWindValue();
    }

    private void updateOutputs() {
        countedPower = powerCompressor.decompress(powerNeuron.get());
        countedAngle = angleCompressor.decompress(angleNeuron.get());
    }
}
