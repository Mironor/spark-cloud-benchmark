#!/usr/bin/env bash

sbt assembly

# run conversion tests
docker run --rm -it -p 4040:4040 \
 -v $(pwd)/target/scala-2.11/benchmark.jar:/benchmark.jar \
 -v $(pwd)/src/main/resources/test_integration.conf:/application.conf \
 -v $(pwd)/../spark/data/:/resources/data \
 -v /resources/csv \
 gettyimages/spark \
 bin/spark-submit \
 --class ski.bedryt.Main \
 --driver-java-options='-Dconfig.file=/application.conf' \
 /benchmark.jar --trivial