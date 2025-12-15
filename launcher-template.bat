@echo off
:: ============================================
:: Bakery Management System - Windows Launcher
:: Professional launcher with error handling
:: ============================================

:: Hide this console window
if not defined LAUNCHED (
    set LAUNCHED=1
    start "" /B "%~f0" %*
    exit
)

:: Change to app directory
cd /d "%~dp0"

:: Check if JAR exists
if not exist "BakeryManagementSystem.jar" (
    msg "%username%" "Error: BakeryManagementSystem.jar not found! Make sure all files are in the same folder."
    exit /b 1
)

:: Check if JRE exists
if not exist "jre\bin\javaw.exe" (
    msg "%username%" "Error: Java runtime not found! Make sure 'jre' folder exists."
    exit /b 1
)

:: Check if JavaFX exists
if not exist "javafx\lib" (
    msg "%username%" "Error: JavaFX not found! Make sure 'javafx' folder exists."
    exit /b 1
)

:: Launch the application with no console window
start "" "jre\bin\javaw.exe" ^
    -Xms256m ^
    -Xmx1024m ^
    --module-path "javafx\lib" ^
    --add-modules javafx.controls,javafx.fxml ^
    --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED ^
    -jar BakeryManagementSystem.jar

:: Exit silently
exit
