//

#include <windows.h>
#include <tlhelp32.h>
#include <stdbool.h>
#include "ptanks_memory_accessor.h"

long current_tank = 0;

HANDLE processEntry;
LPVOID processBaseAddress;

long wind_offset = 0x106290;

/*private functions declaration*/
bool alreadyOpened();

typedef WINBOOL (*memory_function)(HANDLE, LPVOID, double *);

void access_memory(tank_offset tank, property_offset property, double *target, memory_function function) ;

WINBOOL ReadProcessMemoryWrapper(HANDLE hProcess, LPVOID lpBaseAddress, double *target) ;

WINBOOL WriteProcessMemoryWrapper(HANDLE hProcess, LPVOID lpBaseAddress, double *target);

void open_process() {
    if (alreadyOpened()) {
        return;
    }

    PROCESSENTRY32 entry = {.dwSize = sizeof(PROCESSENTRY32)};
    HANDLE snapProcess = CreateToolhelp32Snapshot(TH32CS_SNAPPROCESS, 0);

    if (Process32First(snapProcess, &entry) == TRUE) {
        while (Process32Next(snapProcess, &entry) == TRUE) {
            if (stricmp(entry.szExeFile, "pockettanks.exe") == 0) {
                processEntry = OpenProcess(PROCESS_ALL_ACCESS, FALSE, entry.th32ProcessID);

                MODULEENTRY32 mod = { .dwSize = sizeof( MODULEENTRY32 ) };
                HANDLE snapModule = CreateToolhelp32Snapshot(TH32CS_SNAPMODULE | TH32CS_SNAPMODULE32, entry.th32ProcessID);

                if (Module32First(snapModule, &mod)) {
                    processBaseAddress = (LPVOID)mod.modBaseAddr;
                }
                CloseHandle(snapModule);
                break;
            }
        }
    }

    CloseHandle(snapProcess);
}

bool alreadyOpened() {
    return processEntry && processBaseAddress;
}

void close_process() {
    if (processEntry) {
        CloseHandle(processEntry);
    }
}

double get_tank_property(tank_offset tank, property_offset property) {
    double result;
    access_memory(tank, property, &result, ReadProcessMemoryWrapper);

    return result;
}

void set_tank_property(tank_offset tank, property_offset property, double value) {
    access_memory(tank, property, &value, WriteProcessMemoryWrapper);
}

double get_wind_value() {
    double wind;
    ReadProcessMemory(processEntry, processBaseAddress + wind_offset, &wind, sizeof(double), NULL);

    return wind;
}

void access_memory(tank_offset tank, property_offset property, double *target, memory_function function) {

    LPVOID baseAddress = processBaseAddress + tank;
    LPVOID newAddress = 0;

    ReadProcessMemory(processEntry, (LPCVOID)baseAddress, &newAddress, 4, NULL);

    newAddress += property;

    (*function)(processEntry, newAddress, target);
}

WINBOOL ReadProcessMemoryWrapper(HANDLE hProcess, LPVOID lpBaseAddress, double *target) {
    return ReadProcessMemory(hProcess, lpBaseAddress, (LPVOID)target, sizeof(double), NULL);
}

WINBOOL WriteProcessMemoryWrapper(HANDLE hProcess, LPVOID lpBaseAddress, double *target) {
    return WriteProcessMemory(hProcess, lpBaseAddress, (LPCVOID)target, sizeof(double), NULL);
}
