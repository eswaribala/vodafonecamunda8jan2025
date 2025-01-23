#!/bin/bash
export CAMUNDA_CONNECTORS_ENABLED=true
export CAMUNDA_CONNECTOR_REST_ENABLED=true
docker run --rm --name=connectors -v $PWD/target/connector-template-0.1.0-SNAPSHOT.jar:/opt/app/connector.jar --env-file env.txt camunda/connectors:0.2.0