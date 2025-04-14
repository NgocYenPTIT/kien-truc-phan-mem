#!/bin/bash
#user-service
cd ./user-service
gradlew bootRun

#auth-service
cd ..
cd ./auth-service
gradlew bootRun