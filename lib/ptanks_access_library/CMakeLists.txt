cmake_minimum_required(VERSION 3.6)
project(ptanks_access_library)

set(CMAKE_CXX_STANDARD 17)

set(-DCMAKE_GENERATOR_PLATFORM=x64)

find_package(JNI)
include_directories(${JNI_INCLUDE_DIRS})

set(SOURCE_FILES src/com_wniemiec_cheat_ptanks_PocketTanksAccessor.c src/com_wniemiec_cheat_ptanks_PocketTanksAccessor.h src/ptanks_memory_accessor.c src/ptanks_memory_accessor.h)

add_library(ptanks_access_library SHARED ${SOURCE_FILES})