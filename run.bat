@echo off
setlocal
set ROOT=%~dp0
if not exist "%ROOT%out\quiz\Main.class" call "%ROOT%build.bat"
if errorlevel 1 exit /b 1
java -Dfile.encoding=UTF-8 -cp "%ROOT%out;%ROOT%lib\gson-2.11.0.jar" quiz.Main %*
