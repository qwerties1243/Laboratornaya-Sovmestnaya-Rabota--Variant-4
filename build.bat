@echo off
setlocal
set ROOT=%~dp0
if not exist "%ROOT%out" mkdir "%ROOT%out"
javac -encoding UTF-8 -cp "%ROOT%lib\gson-2.11.0.jar" -d "%ROOT%out" %ROOT%src\main\java\quiz\*.java
if errorlevel 1 exit /b 1
echo Build OK: %ROOT%out
