package com.wniemiec.cheat.ptanks;

public class PocketTanksAccessor {

    static {
        System.loadLibrary("libptanks_access_library");
        openProcess();
    }

    private static native void openProcess();

    public static native void closeProcess();

    public static native double getWindValue();

    static native void accessPlayerOne();

    static native void accessPlayerTwo();

    static native double getTankPositionX();

    static native double getTankPositionY();

    static native double getTankPower();

    static native double getTankAngle();

    static native void setTankPositionX(double value);

    static native void setTankPositionY(double value);

    static native void setTankPower(double value);

    static native void setTankAngle(double value);
}
