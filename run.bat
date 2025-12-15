@echo off
echo ================================================
echo   Bakery Management System - Run Application
echo ================================================
echo.

REM Check if JAVA_HOME is set
if "%JAVA_HOME%"=="" (
    echo ERROR: JAVA_HOME is not set!
    echo Please set JAVA_HOME to your JDK 17+ installation directory
    pause
    exit /b 1
)

REM Set JavaFX SDK path
set JAVAFX_HOME=%~dp0javafx-sdk-21

if not exist "%JAVAFX_HOME%\lib" (
    echo ERROR: JavaFX SDK not found!
    echo Please run build.bat first
    pause
    exit /b 1
)

REM Check if JAR exists
if not exist dist\BakeryManagementSystem.jar (
    echo ERROR: BakeryManagementSystem.jar not found!
    echo Please run build.bat first
    pause
    exit /b 1
)

echo Starting Bakery Management System...
echo.
echo Login credentials:
echo Username: admin
echo Password: admin123
echo.

"%JAVA_HOME%\bin\java" --module-path "%JAVAFX_HOME%\lib" ^
    --add-modules javafx.controls,javafx.fxml ^
    -jar dist\BakeryManagementSystem.jar

pause
