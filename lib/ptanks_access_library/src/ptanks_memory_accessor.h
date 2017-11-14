//
// Created by Wojtek on 2017-11-04.
//

#ifndef PTANKS_ACCESS_LIBRARY_MEMORY_ACCESSOR_H
#define PTANKS_ACCESS_LIBRARY_MEMORY_ACCESSOR_H

typedef enum {
    PLAYER_ONE = 0x316B24,
    PLAYER_TWO = 0x316B28
} tank_offset;

typedef enum {
    COORD_X = 0x10,
    COORD_Y = 0x18,
    ANGLE = 0x38,
    POWER = 0x40
} property_offset;

void open_process();

void close_process();

double get_tank_property(tank_offset, property_offset);

void set_tank_property(tank_offset, property_offset, double);

double get_wind_value();

#endif
