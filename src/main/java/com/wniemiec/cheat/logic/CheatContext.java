package com.wniemiec.cheat.logic;

import com.wniemiec.cheat.neural.*;
import com.wniemiec.cheat.ptanks.PocketTanksAccessor;
import com.wniemiec.cheat.ptanks.Position;
import com.wniemiec.cheat.ptanks.Tank;
import com.wniemiec.cheat.ptanks.TankAccessor;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.apache.commons.lang3.BooleanUtils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;

@Log4j
class CheatContext {
    private static final Double MAX_HORIZONTAL_DISTANCE = 800.0;
    private static final Double MAX_VERTICAL_DISTANCE = 350.0;
    private static final Double MAX_WIND = 100.0;
    private static final Double MAX_POWER = 100.0;
    private static final Double MAX_ANGLE = 180.0;

    private static CheatContext instance;

    static CheatContext getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CheatContext();
        }
        return instance;
    }

    @Getter
    private Tank playerOne = new Tank(TankAccessor.PLAYER_ONE);
    @Getter
    private Tank playerTwo = new Tank(TankAccessor.PLAYER_TWO);

    private Neuron powerNeuron = new Neuron();
    private Neuron angleNeuron = new Neuron();

    private Network network;
    private LinkedList<LearningVector> learningVectors = new LinkedList<>();

    private Serializer<LinkedList<LearningVector>> serializer = new Serializer<>();

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

    private ValueCompressor horizontalCompressor = new SimpleValueCompressor(MAX_HORIZONTAL_DISTANCE);
    private ValueCompressor verticalCompressor = new SimpleValueCompressor(MAX_VERTICAL_DISTANCE);
    private ValueCompressor windCompressor = new SimpleValueCompressor(MAX_WIND);
    private ValueCompressor powerCompressor = new PositiveToBipolarValueCompressor(MAX_POWER);
    private ValueCompressor angleCompressor = new PositiveToBipolarValueCompressor(MAX_ANGLE);

    private CheatContext() {
        Inputable horizontalDistanceInput = () -> horizontalCompressor.compress(horizontalDistance);
        Inputable verticalDistanceInput = () -> verticalCompressor.compress(verticalDistance);
        Inputable windInput = () -> windCompressor.compress(windSpeed);

        network = Network.builder()
                .addInputLayer(Layer.builder()
                        .add(horizontalDistanceInput)
                        .add(verticalDistanceInput)
                        .add(windInput)
                        .build())
                .addHiddenLayer(HiddenLayerGenerator.generate(5))
                .addHiddenLayer(HiddenLayerGenerator.generate(5))
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

    void learn() {
        double powerError;
        double angleError;
        int i = 0;

        log.debug("Learning with parameters: " + learningVectors);

        do {
            LearningVector vector = learningVectors.get(randomIndex());

            horizontalDistance = vector.getHorizontalDistance();
            verticalDistance = vector.getVerticalDistance();
            windSpeed = vector.getWindSpeed();

            network.doPropagation();

            powerError = powerCompressor.compress(vector.getCountedPower()) - powerNeuron.get();
            angleError = angleCompressor.compress(vector.getCountedAngle()) - angleNeuron.get();
            vector.setLearned(errorsAreAcceptable(powerError, angleError));

            log.debug("Iteration number: " + i++);
            log.debug("Propagated error for power is: " + powerError + ", and for angle is : " + angleError);

            powerNeuron.doBackPropagation(powerError);
            angleNeuron.doBackPropagation(angleError);

            network.doBackPropagation();
            network.updateNeuronInputWeights();
        } while (unlearnedVectorPresent());

    }

    void refreshProperties() {
        updateInputs();
        network.doPropagation();
        updateOutputs();
    }

    void saveLearningVectors(File file) throws IOException {
        serializer.serialize(learningVectors, file);
    }

    void loadLearningVectors(File file) throws IOException, ClassNotFoundException {
        learningVectors = serializer.deserialize(file);
    }

    private int randomIndex() {
        return (int) Math.round(Math.random() * (learningVectors.size() - 1));
    }

    private boolean unlearnedVectorPresent() {
        return learningVectors.stream().anyMatch(vector -> BooleanUtils.isFalse(vector.isLearned()));
    }

    private boolean errorsAreAcceptable(double powerError, double angleError) {
        return Math.abs(powerError) < 0.01 && Math.abs(angleError) < 0.01;
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

    void undoUpdate() {
        learningVectors.pollLast();
    }


}
