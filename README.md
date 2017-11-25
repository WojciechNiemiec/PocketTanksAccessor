To setup this on your PC:

1. Install Java JDK (minimum version 1.8)
2. Install Maven or IDE having Maven bundled
3. Install MinGW
4. Add JAVA_HOME and JAVA_INCLUDE_PATH to environmental variables
5. Run Cmake on ptanks_access_library
6. Execute mvn clean install on project
7. Copy libptanks_access_library.dll to eg. JAVA_HOME/bin or other places where from where JVM reads dll files
8. Run Pockettanks.exe (version 1.6)
9. Open application