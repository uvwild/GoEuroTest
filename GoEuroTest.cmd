@ECHO OFF
ECHO Listing Entries for %*
set ARTIFACT=GoEuroTest
set DEBUGPORT=8989
set DEBUGSHMEM=-agentlib:jdwp=transport=dt_shmem,server=n,address=javadebug,suspend=y
set DEBUGSOCKET=-Xdebug -agentlib:jdwp=transport=dt_socket,address=localhost:%DEBUGPORT%,suspend=y,server=y
REM ,onthrow=<FQ exception class name>,onuncaught=y
set DEBUG=
set COMMAND=java  %DEBUG%  -jar target/%ARTIFACT%-jar-with-dependencies.jar %*
echo %COMMAND%
%COMMAND%
