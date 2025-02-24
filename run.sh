#!/bin/bash

# Stop on first error
set -e

echo "Running JavaFX application..."

# Find the JAR file in the target directory
JAR_FILE=$(find target -name "*.jar" | head -n 1)

if [[ -z "$JAR_FILE" ]]; then
  echo "Error: No JAR file found! Did you run build.sh?"
  exit 1
fi

# Run the JavaFX application
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar "$JAR_FILE"
