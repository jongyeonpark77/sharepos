#!/bin/bash

# Generate keystore for Android app signing
keytool -genkey -v -keystore app/keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias sharepos -storepass sharepos123 -keypass sharepos123 -dname "CN=SharePos, OU=Development, O=SharePos, L=Seoul, S=Seoul, C=KR"

echo "Keystore generated successfully!" 