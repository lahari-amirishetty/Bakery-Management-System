@echo off
echo ================================================
echo   Bakery Management System - Standalone App
echo ================================================
echo.
echo Creating standalone application (no installer needed)
echo This creates a folder with bundled Java runtime
echo.

REM Check if JAVA_HOME is set
if "%JAVA_HOME%"=="" (
    echo ERROR: JAVA_HOME is not set!
    pause
    exit /b 1
)

REM Set JavaFX SDK path
set JAVAFX_HOME=%~dp0javafx-sdk-21

if not exist "%JAVAFX_HOME%\lib" (
    echo ERROR: JavaFX SDK not found!
    pause
    exit /b 1
)

REM Check if JAR exists
if not exist dist\BakeryManagementSystem.jar (
    echo ERROR: JAR file not found! Please run build.bat first
    pause
    exit /b 1
)

echo.
echo Step 1: Creating standalone application...
echo.

REM Clean previous package
if exist BakeryApp rmdir /s /q BakeryApp

REM Create app-image (standalone folder with bundled JRE)
"%JAVA_HOME%\bin\jpackage" ^
    --type app-image ^
    --name "BakeryManagementSystem" ^
    --app-version 1.0 ^
    --vendor "BakeryApp" ^
    --description "Bakery Management System - College Project" ^
    --input dist ^
    --main-jar BakeryManagementSystem.jar ^
    --main-class com.bakery.app.BakeryApp ^
    --module-path "%JAVAFX_HOME%\lib" ^
    --add-modules javafx.controls,javafx.fxml ^
    --java-options "--module-path javafx-sdk-21/lib --add-modules javafx.controls,javafx.fxml" ^
    --dest BakeryApp

if errorlevel 1 (
    echo.
    echo ERROR: Failed to create standalone app!
    pause
    exit /b 1
)

echo.
echo ================================================
echo   SUCCESS!
echo ================================================
echo.
echo Standalone application created in: BakeryApp\
echo.
echo To run the application:
echo 1. Go to BakeryApp\BakeryManagementSystem\ folder
echo 2. Double-click BakeryManagementSystem.exe
echo.
echo You can copy the entire BakeryApp folder anywhere
echo and it will run without Java installation!
echo.
pause
