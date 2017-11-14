//

#include <jni.h>
#include "ptanks_memory_accessor.h"

const int FULL_ANGLE = 360;
tank_offset tank;

//
// Created by Wojtek on 2017-10-12.
jdouble correct_angle(jdouble angle);

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_openProcess(JNIEnv *env, jclass class) {
    open_process();
}

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_closeProcess(JNIEnv *env, jclass class) {
    close_process();
}

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_accessPlayerOne(JNIEnv *env, jclass class) {
    tank = PLAYER_ONE;
}

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_accessPlayerTwo(JNIEnv *env, jclass class) {
    tank = PLAYER_TWO;
}

jdouble JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_getTankPositionX(JNIEnv *env, jclass class) {
    return get_tank_property(tank, COORD_X);
}

jdouble JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_getTankPositionY(JNIEnv *env, jclass class) {
    return get_tank_property(tank, COORD_Y);
}

jdouble JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_getTankPower(JNIEnv *env, jclass class) {
    return get_tank_property(tank, POWER);
}

jdouble JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_getTankAngle(JNIEnv *env, jclass class) {
    return correct_angle(get_tank_property(tank, ANGLE));
}

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_setTankPositionX(JNIEnv *env, jclass class, jdouble value) {
    set_tank_property(tank, COORD_X, value);
}

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_setTankPositionY(JNIEnv *env, jclass class, jdouble value) {
    set_tank_property(tank, COORD_Y, value);
}

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_setTankPower(JNIEnv *env, jclass class, jdouble value) {
    set_tank_property(tank, POWER, value);
}

void JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_setTankAngle(JNIEnv *env, jclass class, jdouble value) {
    set_tank_property(tank, ANGLE, correct_angle(value));
}

jdouble JNICALL Java_com_wniemiec_cheat_ptanks_PocketTanksAccessor_getWindValue(JNIEnv *env, jclass class) {
    return get_wind_value();
}

jdouble correct_angle(jdouble angle) {
    return FULL_ANGLE - angle;
}
