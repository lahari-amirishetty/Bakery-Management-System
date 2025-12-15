# 🍰 Bakery Management System

A modern, JavaFX-based desktop application for managing bakery operations including inventory, orders, and sales tracking.

## ✨ Features

- **User Authentication**: Secure login system (default: admin/admin123)
- **Order Management**: Easy-to-use interface for placing customer orders
- **Product Management**: Add, update, and delete bakery products
- **Sales Reporting**: View total sales and products sold
- **File-Based Storage**: Data persists between sessions (no database required)
- **Modern UI**: Clean, bakery-themed interface with smooth navigation

## 🛠️ Prerequisites

### Required Software

1. **Java Development Kit (JDK) 17 or higher**
   - Download from: https://www.oracle.com/java/technologies/downloads/
   - Or use OpenJDK: https://adoptium.net/

2. **JavaFX SDK 21**
   - Download from: https://gluonhq.com/products/javafx/
   - Choose: JavaFX Windows SDK

## 📦 Setup Instructions

### Step 1: Download JavaFX SDK

1. Go to https://gluonhq.com/products/javafx/
2. Download **JavaFX Windows SDK** (version 21 or compatible)
3. Extract the downloaded zip file
4. Rename the extracted folder to `javafx-sdk-21`
5. Place it in the project root directory:
   ```
   BakeryManagementSystem/
   ├── javafx-sdk-21/
   │   └── lib/
   ├── src/
   ├── resources/
   └── ...
   ```

### Step 2: Set JAVA_HOME

1. Open System Properties → Advanced → Environment Variables
2. Add a new system variable:
   - Variable name: `JAVA_HOME`
   - Variable value: Path to your JDK (e.g., `C:\Program Files\Java\jdk-17`)

### Step 3: Build the Application

1. Open Command Prompt or PowerShell
2. Navigate to the project directory:
   ```
   cd C:\Users\lahar\OneDrive\Desktop\Java\BakeryManagementSystem
   ```
3. Run the build script:
   ```
   build.bat
   ```

### Step 4: Run the Application

**Option 1: Using the run script (Recommended)**
```
run.bat
```

**Option 2: Manual run**
```
java --module-path javafx-sdk-21\lib --add-modules javafx.controls,javafx.fxml -jar dist\BakeryManagementSystem.jar
```

## 🎯 Creating Windows EXE (Optional)

To create a standalone Windows executable:

1. Ensure JDK 14+ is installed
2. Run:
   ```
   create-exe.bat
   ```
3. The installer will be created in the `package` folder
4. Install and run the application like any Windows program

## 📖 User Guide

### Login Screen
- **Username**: `admin`
- **Password**: `admin123`

### Main Menu
Four main options:
1. **Order Now**: Place customer orders
2. **Manage Products**: Add, update, or delete products
3. **Sales Report**: View sales statistics
4. **Exit**: Close the application

### Order Now
1. View available products in the table
2. Enter Product ID and Quantity
3. Click "Place Order"
4. Confirm the order summary
5. Stock is automatically updated

### Manage Products
1. **Add**: Fill the form and click "Add Product"
2. **Update**: Select a product from table, modify fields, click "Update Product"
3. **Delete**: Select a product and click "Delete Product"

### Sales Report
- View total sales revenue
- View total products sold
- View average sale per product

## 📁 Project Structure

```
BakeryManagementSystem/
├── src/
│   ├── com/bakery/app/
│   │   └── BakeryApp.java          # Main application class
│   ├── com/bakery/controller/
│   │   ├── LoginController.java    # Login screen
│   │   ├── HomeController.java     # Home/Dashboard
│   │   ├── OrderController.java    # Order management
│   │   ├── ProductController.java  # Product management
│   │   └── SalesController.java    # Sales reports
│   ├── com/bakery/model/
│   │   ├── Product.java            # Product data model
│   │   └── DataStore.java          # File-based storage
│   └── module-info.java
├── resources/
│   └── styles.css                  # UI styling
├── build.bat                       # Build script
├── run.bat                         # Run script
├── create-exe.bat                  # EXE creation script
└── README.md                       # This file
```

## 💾 Data Storage

The application uses file-based storage:
- **products.dat**: Stores all product information
- **sales.dat**: Stores sales statistics

Files are created automatically in the application directory.

## 🎨 Default Products

The system comes pre-loaded with sample products:
- Chocolate Cake - $24.99
- Black Forest Cake - $29.99
- Vanilla Cupcake - $2.99
- Croissant - $3.99
- Danish Pastry - $4.99
- Wheat Bread - $5.99
- Baguette - $4.50
- Cinnamon Roll - $3.50

## 🔧 Troubleshooting

### "JavaFX SDK not found"
- Ensure `javafx-sdk-21` folder is in the project root
- Check that it contains a `lib` subfolder

### "JAVA_HOME is not set"
- Set JAVA_HOME environment variable to your JDK path
- Restart Command Prompt after setting

### "javafx cannot be resolved"
- These are IDE warnings - they won't affect compilation
- The build script handles JavaFX modules correctly

### Application won't start
- Verify JDK 17+ is installed
- Check JavaFX SDK is properly extracted
- Run `build.bat` before `run.bat`

## 🚀 Quick Start (TL;DR)

```batch
# 1. Download and extract JavaFX SDK to: BakeryManagementSystem/javafx-sdk-21/
# 2. Set JAVA_HOME environment variable
# 3. Build and run:
build.bat
run.bat
```

## 📝 Notes for College Project

- **No Database**: Uses file-based storage (no MySQL/SQLite setup needed)
- **Easy to Demo**: Just double-click run.bat
- **Professional UI**: Modern, clean interface with bakery theme
- **Full Features**: Complete CRUD operations and reporting
- **Well-Commented**: Code includes helpful comments

## 👨‍💻 Author

College Project - Bakery Management System
Java + JavaFX

## 📄 License

This is a college project for educational purposes.

---

**Enjoy your Bakery Management System! 🍰**
