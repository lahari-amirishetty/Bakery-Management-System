@echo off
echo ================================================
echo   Bakery Management System - Debug Mode
echo ================================================
echo.

set JAVA_HOME=C:\Program Files\Java\jdk-25
set JAVAFX_HOME=%~dp0javafx-sdk-21

echo Starting application in debug mode...
echo Console will stay open to show errors
echo.
echo Login: admin / admin123
echo.

"%JAVA_HOME%\bin\java" ^
    --module-path "%JAVAFX_HOME%\lib" ^
    --add-modules javafx.controls,javafx.fxml ^
    --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED ^
    -jar dist\BakeryManagementSystem.jar

echo.
echo ================================================
if errorlevel 1 (
    echo Application exited with error code: %errorlevel%
) else (
    echo Application closed normally
)
echo ================================================
pause
