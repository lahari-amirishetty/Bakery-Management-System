@echo off
echo ================================================
echo   Bakery Management System - Run (Maven)
echo ================================================
echo.

where mvn >nul 2>nul
if errorlevel 1 (
    echo ERROR: Maven is not installed!
    pause
    exit /b 1
)

echo Starting Bakery Management System...
echo.
echo Login credentials:
echo Username: admin
echo Password: admin123
echo.

mvn javafx:run

pause
