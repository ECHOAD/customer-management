@echo off
set ENV=%1
if "%ENV%"=="" (
    echo You must provide an environment: dev or stg
    exit /b 1
)

echo Starting deployment for environment: %ENV%

if "%ENV%"=="dev" (
    set COMPOSE_FILE=docker-compose.dev.yml
) else if "%ENV%"=="stg" (
    set COMPOSE_FILE=docker-compose.stg.yml
) else (
    echo Unknown environment. Use 'dev' or 'stg'.
    exit /b 1
)

if not exist %COMPOSE_FILE% (
    echo The file %COMPOSE_FILE% does not exist.
    exit /b 1
)

echo Compiling the project...
call mvnw.cmd clean package
if %errorlevel% neq 0 (
    echo Compilation failed
    exit /b 1
)

echo Starting containers with %COMPOSE_FILE%...
docker-compose -f %COMPOSE_FILE% up --build -d
if %errorlevel% neq 0 (
    echo Deployment failed
    exit /b 1
)

echo Deployment successful for environment %ENV%.