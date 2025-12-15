@echo off
setlocal

echo.
echo 📦 Creating shareable ZIP file for WhatsApp...
echo.

set "SOURCE_DIR=%~dp0BakeryApp-Windows"
set "ZIP_FILE=%~dp0BakeryApp-Windows.zip"

if not exist "%SOURCE_DIR%" (
    echo ❌ Error: BakeryApp-Windows folder not found!
    echo    Please run create-exe.bat first
    pause
    exit /b 1
)

:: Remove old ZIP if exists
if exist "%ZIP_FILE%" (
    del /F /Q "%ZIP_FILE%"
)

:: Create ZIP using PowerShell
echo Creating ZIP file...
powershell -Command "Compress-Archive -Path '%SOURCE_DIR%\*' -DestinationPath '%ZIP_FILE%' -CompressionLevel Optimal"

if exist "%ZIP_FILE%" (
    echo.
    echo ✅ ZIP file created successfully!
    echo.
    
    :: Get file size
    for %%A in ("%ZIP_FILE%") do set "SIZE=%%~zA"
    set /a "SIZE_MB=!SIZE! / 1048576"
    
    echo 📦 File: BakeryApp-Windows.zip
    echo 📊 Size: !SIZE_MB! MB
    echo 📍 Location: %ZIP_FILE%
    echo.
    echo 🎯 Ready to share via WhatsApp!
    echo.
    
    :: Open folder
    explorer /select,"%ZIP_FILE%"
) else (
    echo ❌ Failed to create ZIP file
)

pause
