#!/bin/bash

export GRADLE_OPTS=-Xmx1024m

./gradlew build --stacktrace
