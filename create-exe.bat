@echo off
setlocal enabledelayedexpansion

:: ============================================
:: Bakery Management System - EXE Creator
:: Using jpackage for professional deployment
:: ============================================

echo.
echo ╔═══════════════════════════════════════════════════════════╗
echo ║   🍰 BAKERY MANAGEMENT SYSTEM - EXE CREATOR 🍰          ║
echo ╚═══════════════════════════════════════════════════════════╝
echo.

:: Set Java Home
set "JAVA_HOME=C:\Program Files\Java\jdk-25"
set "JAVAFX_HOME=%~dp0javafx-sdk-21"
set "APP_VERSION=1.0"
set "OUTPUT_DIR=%~dp0BakeryApp-Windows"

echo [Step 1/6] Setting up environment...
echo ✓ Java: %JAVA_HOME%
echo ✓ JavaFX: %JAVAFX_HOME%
echo.

:: Clean previous build
if exist "%OUTPUT_DIR%" (
    echo [Step 2/6] Cleaning previous build...
    rmdir /S /Q "%OUTPUT_DIR%" 2>nul
    echo ✓ Previous build removed
) else (
    echo [Step 2/6] No previous build found
)
echo.

:: Create runtime image with jlink (smaller JRE)
echo [Step 3/6] Creating custom Java runtime (this may take 2-3 minutes)...
set "JRE_DIR=%~dp0custom-jre"
if exist "%JRE_DIR%" (
    rmdir /S /Q "%JRE_DIR%" 2>nul
)

"%JAVA_HOME%\bin\jlink.exe" ^
    --module-path "%JAVA_HOME%\jmods;%JAVAFX_HOME%\lib" ^
    --add-modules java.base,java.desktop,java.logging,java.prefs,java.xml,javafx.controls,javafx.fxml,javafx.graphics,javafx.base ^
    --output "%JRE_DIR%" ^
    --strip-debug ^
    --no-header-files ^
    --no-man-pages ^
    --compress=2

if errorlevel 1 (
    echo ❌ Failed to create custom runtime!
    pause
    exit /b 1
)
echo ✓ Custom runtime created successfully
echo.

:: Create application icon (placeholder - you can replace with actual icon)
echo [Step 4/6] Preparing application icon...
if not exist resources\bakery-icon.ico (
    echo ℹ No custom icon found, using default
) else (
    echo ✓ Custom icon found
)
echo.

:: Run jpackage to create Windows EXE
echo [Step 5/6] Building Windows executable...
echo This will create a professional installer-free EXE
echo.

"%JAVA_HOME%\bin\jpackage.exe" ^
    --type app-image ^
    --name "BakeryApp" ^
    --app-version "%APP_VERSION%" ^
    --input dist ^
    --main-jar BakeryManagementSystem.jar ^
    --main-class com.bakery.app.BakeryApp ^
    --runtime-image "%JRE_DIR%" ^
    --dest . ^
    --win-console ^
    --java-options "--add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED" ^
    --java-options "-Xms256m" ^
    --java-options "-Xmx1024m"

if errorlevel 1 (
    echo.
    echo ❌ jpackage failed!
    echo.
    echo Trying alternative method...
    
    :: Alternative: Create portable structure manually
    mkdir "%OUTPUT_DIR%" 2>nul
    mkdir "%OUTPUT_DIR%\jre" 2>nul
    mkdir "%OUTPUT_DIR%\javafx" 2>nul
    
    xcopy /E /I /Y "%JRE_DIR%" "%OUTPUT_DIR%\jre\"
    xcopy /E /I /Y "%JAVAFX_HOME%\lib" "%OUTPUT_DIR%\javafx\lib\"
    copy /Y "dist\BakeryManagementSystem.jar" "%OUTPUT_DIR%\"
    
    :: Create launcher BAT
    echo @echo off > "%OUTPUT_DIR%\BakeryApp.bat"
    echo cd /d "%%~dp0" >> "%OUTPUT_DIR%\BakeryApp.bat"
    echo start "" "jre\bin\javaw.exe" -Xms256m -Xmx1024m --module-path "javafx\lib" --add-modules javafx.controls,javafx.fxml --add-exports javafx.graphics/com.sun.javafx.application=ALL-UNNAMED -jar BakeryManagementSystem.jar >> "%OUTPUT_DIR%\BakeryApp.bat"
    
    echo ✓ Portable package created (using BAT launcher)
) else (
    echo ✓ Windows executable created successfully
    
    :: Rename output folder
    if exist "BakeryApp" (
        if exist "%OUTPUT_DIR%" rmdir /S /Q "%OUTPUT_DIR%"
        move "BakeryApp" "%OUTPUT_DIR%"
    )
)
echo.

:: Create README
echo [Step 6/6] Creating documentation...
(
echo ╔═══════════════════════════════════════════════════════════════╗
echo ║         🍰 BAKERY MANAGEMENT SYSTEM - PORTABLE APP 🍰        ║
echo ╚═══════════════════════════════════════════════════════════════╝
echo.
echo 🚀 HOW TO RUN:
echo    • Double-click: BakeryApp.exe (or BakeryApp.bat)
echo    • Login with: admin / admin123
echo.
echo 📋 SYSTEM REQUIREMENTS:
echo    ✓ Windows 7/8/10/11 (64-bit)
echo    ✓ 256 MB RAM minimum
echo    ✓ 150 MB disk space
echo    ✓ NO Java installation required (bundled)
echo.
echo 📱 FEATURES:
echo    ✓ User Login System
echo    ✓ Product Management (Add/Update/Delete)
echo    ✓ Order Processing with Auto-Bill
echo    ✓ Sales Reporting ^& Analytics
echo    ✓ Stock Management
echo    ✓ File-based Data Storage
echo.
echo 🔐 DEFAULT LOGIN:
echo    Username: admin
echo    Password: admin123
echo.
echo 💾 DATA STORAGE:
echo    All data is saved automatically in:
echo    - products.dat (Product information)
echo    - sales.dat (Sales records)
echo.
echo ⚠️ IMPORTANT NOTES:
echo    • Keep all files together in the same folder
echo    • Do NOT delete 'jre' or 'app' folder
echo    • App works completely offline
echo    • Portable - copy entire folder to any Windows PC
echo.
echo 🆘 TROUBLESHOOTING:
echo    Problem: App doesn't start
echo    Solution: Right-click BakeryApp.exe → Run as Administrator
echo.
echo    Problem: GUI doesn't appear
echo    Solution: Check if antivirus is blocking the app
echo.
echo    Problem: "Missing JavaFX" error
echo    Solution: Make sure 'javafx' folder exists alongside the EXE
echo.
echo 📧 DEVELOPED BY: Your Name
echo 📅 VERSION: 1.0
echo 🏷️ BUILD DATE: December 2025
echo.
echo ═══════════════════════════════════════════════════════════════
echo          Thank you for using Bakery Management System!
echo ═══════════════════════════════════════════════════════════════
) > "%OUTPUT_DIR%\README.txt"

echo ✓ README.txt created
echo.

:: Cleanup
echo Cleaning up temporary files...
if exist "%JRE_DIR%" rmdir /S /Q "%JRE_DIR%"
echo ✓ Cleanup complete
echo.

echo ╔═══════════════════════════════════════════════════════════╗
echo ║                  ✅ BUILD SUCCESSFUL! ✅                  ║
echo ╚═══════════════════════════════════════════════════════════╝
echo.
echo 📦 Output Location: %OUTPUT_DIR%
echo.
echo 🎯 Next Steps:
echo    1. Test the app by running BakeryApp.exe
echo    2. Copy entire '%OUTPUT_DIR%' folder to test on another PC
echo    3. Share via WhatsApp (ZIP the folder first)
echo.
echo 💡 To create ZIP for sharing, run: create-zip.bat
echo.

pause
