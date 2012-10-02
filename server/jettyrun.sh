#!/bin/sh

if [ -z "$JREBEL_HOME" ] ; then
    echo "you do not have JREBEL installed.  You need to define JREBEL_HOME"
    exit -1
fi

# JREBEL_OPTS="-noverify -javaagent:$JREBEL_HOME/jrebel.jar"
DEBUG="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5010"
MEMORY="-XX:MaxPermSize=256m -Xmx1024M -server"
TRUST="-Djavax.net.ssl.trustStore=`pwd`/keystore.jks -Djavax.net.ssl.trustStorePassword=password"
export MAVEN_OPTS="$TRUST $JREBEL_OPTS $DEBUG $OVERRIDES $MEMORY $DIRS -Dfile.encoding=UTF8"

mvn jetty:run $@