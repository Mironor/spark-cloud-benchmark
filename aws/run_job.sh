#!/usr/bin/env bash


aws emr create-cluster \
--applications Name=Hadoop Name=Spark \
--ec2-attributes file://ec2_attributes.json \
--release-label emr-5.8.0 \
--log-uri 's3n://spark-cloud-benchmark-oregon/emr/logs/' \
--steps file://steps.json \
--instance-groups file://instance_groups.json \
--configurations file://configurations.json \
--auto-terminate \
--service-role EMR_DefaultRole \
--enable-debugging \
--name 'Spark benchmark' \
--scale-down-behavior TERMINATE_AT_TASK_COMPLETION \
--region us-west-2