@echo off

rem 启动 .exe 程序
start "" ".\Redis-x64-5.0.14.1\redis-server.exe"

rem 等待 .exe 程序完全启动（可根据需要调整等待时间）
timeout /t 5 /nobreak


rem 启动 .jar 文件
start "" java -jar ".\rsm\target\RSM-0.0.1-SNAPSHOT.jar"

