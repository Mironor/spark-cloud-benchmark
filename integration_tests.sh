#!/usr/bin/env bash

sbt assembly

# run conversion tests
docker run --rm -it -p 4040:4040 \
 -v $(pwd)/target/scala-2.11/benchmark.jar:/benchmark.jar \
 -v $(pwd)/src/main/resources/test_integration.conf:/application.conf \
 -v $(pwd)/src/test/resources/csv/:/resources/csv \
 -v /resources/csv \
 gettyimages/spark \
 bin/spark-submit \
 --driver-java-options='-Dbenchmark.files.csv.badges=file:///resources/csv/comments.csv' \
 --class ski.bedryt.Main \
 /benchmark.jar --trivial