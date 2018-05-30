#!/bin/sh

cd api-gateway; mvn clean package docker:build; cd ..
cd auth-server; mvn clean package docker:build; cd ..
cd config-server; mvn clean package docker:build; cd ..
cd webservice-registry; mvn clean package docker:build; cd ..
cd zipkin-server; mvn clean package docker:build; cd ..
