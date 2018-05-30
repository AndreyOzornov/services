#!/bin/sh

cd api-gateway; mvn clean; cd ..
cd auth-server; mvn clean; cd ..
cd config-server; mvn clean; cd ..
cd webservice-registry; mvn clean; cd ..
cd zipkin-server; mvn clean; cd ..
