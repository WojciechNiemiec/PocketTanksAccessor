package com.wniemiec.cheat.logic;

import lombok.Data;

import java.io.Serializable;

@Data
class LearningVector implements Serializable {
    private Double horizontalDistance;
    private Double verticalDistance;
    private Double windSpeed;

    private Double countedPower;
    private Double countedAngle;
}
