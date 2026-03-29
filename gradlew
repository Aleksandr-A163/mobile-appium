#!/bin/sh

APP_HOME=$(cd "$(dirname "$0")" && pwd -P)
DEFAULT_JVM_OPTS="-Xmx64m -Xms64m"

exec java $DEFAULT_JVM_OPTS -jar "$APP_HOME/gradle/wrapper/gradle-wrapper.jar" "$@"