@echo off
echo ================================================
echo   Bakery Management System - Build Script
echo ================================================
echo.

REM Check if JAVA_HOME is set
if "%JAVA_HOME%"=="" (
    echo ERROR: JAVA_HOME is not set!
    echo Please set JAVA_HOME to your JDK 17+ installation directory
    echo Example: set JAVA_HOME=C:\Program Files\Java\jdk-17
    pause
    exit /b 1
)

REM Set JavaFX SDK path (user needs to download JavaFX)
set JAVAFX_HOME=%~dp0javafx-sdk-21

if not exist "%JAVAFX_HOME%\lib" (
    echo.
    echo ================================================
    echo   JavaFX SDK NOT FOUND!
    echo ================================================
    echo Please download JavaFX SDK from:
    echo https://gluonhq.com/products/javafx/
    echo.
    echo Extract it and place the 'javafx-sdk-21' folder in:
    echo %~dp0
    echo.
    pause
    exit /b 1
)

echo Step 1: Cleaning previous build...
if exist bin rmdir /s /q bin
if exist dist rmdir /s /q dist
mkdir bin
mkdir dist

echo.
echo Step 2: Compiling Java files...
"%JAVA_HOME%\bin\javac" --module-path "%JAVAFX_HOME%\lib" ^
    --add-modules javafx.controls,javafx.fxml ^
    -d bin ^
    src\module-info.java ^
    src\com\bakery\model\*.java ^
    src\com\bakery\controller\*.java ^
    src\com\bakery\app\*.java

if errorlevel 1 (
    echo.
    echo ERROR: Compilation failed!
    pause
    exit /b 1
)

echo.
echo Step 3: Copying resources...
xcopy /E /I /Y resources bin\resources

echo.
echo Step 4: Creating JAR file...
"%JAVA_HOME%\bin\jar" --create ^
    --file dist\BakeryManagementSystem.jar ^
    --main-class com.bakery.app.BakeryApp ^
    -C bin .

echo.
echo ================================================
echo   BUILD SUCCESSFUL!
echo ================================================
echo JAR file created: dist\BakeryManagementSystem.jar
echo.
echo To run the application, use: run.bat
echo.
pause
