@echo off
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"
set APP_HOME=%~dp0
java %DEFAULT_JVM_OPTS% -jar "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" %*
