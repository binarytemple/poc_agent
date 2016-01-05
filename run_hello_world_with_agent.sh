#!/bin/bash

java -Djava.system.class.loader=ie.hunt.poc_agent.CustomClassLoader -javaagent:./target/poc_agent-0.1-SNAPSHOT.jar -jar ./target/poc_agent-0.1-SNAPSHOT.jar
