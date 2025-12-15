@echo off
echo ================================================
echo   Bakery Management System - Maven Build
echo ================================================
echo.
echo This is an alternative build method using Maven
echo Maven will automatically download JavaFX dependencies
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if errorlevel 1 (
    echo ERROR: Maven is not installed or not in PATH!
    echo.
    echo Please download and install Maven from:
    echo https://maven.apache.org/download.cgi
    echo.
    echo After installation, add Maven bin folder to PATH
    pause
    exit /b 1
)

echo Maven found! Building project...
echo.

REM Clean and build
mvn clean package

if errorlevel 1 (
    echo.
    echo ERROR: Build failed!
    pause
    exit /b 1
)

echo.
echo ================================================
echo   BUILD SUCCESSFUL!
echo ================================================
echo.
echo JAR file created: target\BakeryManagementSystem.jar
echo.
echo To run the application:
echo mvn javafx:run
echo.
echo Or use: run-maven.bat
echo.
pause
