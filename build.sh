#!/bin/bash

# Stop on first error
set -e

echo "Building JavaFX application..."

# Clean and build using Maven
mvn clean package

echo "Build completed! The JAR file is in the target/ directory."
