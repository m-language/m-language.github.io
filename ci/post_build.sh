#!/bin/bash

# Exit on first failed command
set -ev

mkdir www
cp -r static/public/* www/
cp build/js/compiled/app.min.js www/js/app.min.js
