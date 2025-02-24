#!/bin/bash

# Stop on first error
set -e

echo "Running JavaFX application..."

./mvnw javafx:run
