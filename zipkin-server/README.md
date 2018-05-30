#Overview

This application provides the **Zipkin Server** that provides UI for the [Zipkin distributed tracing](http://zipkin.io/).

When we enable tracing on the applications, they send the tracing data (timing, components called etc) to the Zipkin server so that we can visualize it.

##Pre-requisites

### Projects that need to be started before
* [config server](/../../blob/master/config-server/README.md) - For pulling the configuration information

### Running the application
* Build the application by running the `mvn clean package docker:build` maven command at the "zipkin-server" project root folder	on the terminal.

## External Configuration
The project derives it's external configuration from the [config server](/../config-server/README.md) service. Note that we have defined the `spring.cloud.config.uri` in the `bootstrap.yml` file and that tells the application where to pick up the external confiurations. In our case, the URL points to the running [config server](/../../blob/master/config-server/README.md) server. You also need to have the `spring-cloud-starter-config` dependency in the classpath so that the application can comsume the config server.

A Spring Cloud application operates by creating a "bootstrap" context, which is a parent context for the main application. This bootstrap context loads properties from external sources (the config-server) and decrypts the properties if required. 

The bootstrap context for external configuration is located by convention at `bootstrap.yml` whereas the internal configuration is located by convention at `application.yml`. Note that you can also have `.properties` file instead of `.yml` files.