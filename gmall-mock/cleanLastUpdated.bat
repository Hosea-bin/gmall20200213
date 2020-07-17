@echo off
rem create by NettQun

rem 这里写你的仓库路径
set REPOSITORY_PATH=C:\Users\86188\.m2\repository
rem 正在搜索...
for /f "delims=" %%i in ('dir /b /s "%REPOSITORY_PATH%\*lastUpdated*"') do (
    echo %%i
    del /s /q "%%i"
)
rem 搜索完毕
pause

