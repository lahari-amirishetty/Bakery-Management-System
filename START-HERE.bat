@echo off
title Bakery Management System - Quick Start
color 0E
cls

echo.
echo     ========================================================
echo                  BAKERY MANAGEMENT SYSTEM
echo                      QUICK START LAUNCHER
echo     ========================================================
echo.
echo.
echo     [1] Launch Application (Normal Mode)
echo.
echo     [2] Launch Application (Debug Mode - See Errors)
echo.
echo     [3] Rebuild Application
echo.
echo     [4] Read Fix Documentation
echo.
echo     [5] Exit
echo.
echo     ========================================================
echo.
set /p choice="     Enter your choice (1-5): "

if "%choice%"=="1" goto normal
if "%choice%"=="2" goto debug
if "%choice%"=="3" goto rebuild
if "%choice%"=="4" goto docs
if "%choice%"=="5" goto end

:normal
cls
echo.
echo     Starting Bakery Management System...
echo     Login: admin / admin123
echo.
call RUN-APPLICATION.bat
goto end

:debug
cls
echo.
echo     Starting in Debug Mode...
echo     Console will stay open to show messages
echo.
call run-debug.bat
goto end

:rebuild
cls
echo.
echo     Rebuilding Application...
echo.
set JAVA_HOME=C:\Program Files\Java\jdk-25
call build.bat
pause
goto end

:docs
cls
type FIXED-SOLUTION.txt
pause
goto end

:end
exit
