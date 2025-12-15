@echo off
title Bakery Management System
cls
echo.
echo     ================================================
echo              BAKERY MANAGEMENT SYSTEM
echo                  Professional Edition
echo     ================================================
echo.
echo              Starting application...
echo.
echo              Login: admin / admin123
echo.
echo     ================================================
echo.

set JAVA_HOME=C:\Program Files\Java\jdk-25
set JAVAFX_HOME=%~dp0javafx-sdk-21

start "Bakery Management System" /B "%JAVA_HOME%\bin\javaw.exe" --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar "%~dp0dist\BakeryManagementSystem.jar"

timeout /t 2 >nul
exit
