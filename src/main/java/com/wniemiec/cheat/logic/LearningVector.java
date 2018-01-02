package com.wniemiec.cheat.logic;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
class LearningVector implements Serializable {
    private static final long serialVersionUID = -234142455333639038L;

    private Double horizontalDistance;
    private Double verticalDistance;
    private Double windSpeed;

    private Double countedPower;
    private Double countedAngle;

    private transient boolean isLearned;

    LearningVector(CheatContext context, Double power, Double angle) {
        horizontalDistance = context.getHorizontalDistance();
        verticalDistance = context.getVerticalDistance();
        windSpeed = context.getWindSpeed();

        countedPower = power;
        countedAngle = angle;
    }
}
