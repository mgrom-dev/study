@echo off
chcp 65001
Set work=work6_3
docker stop skill-sql
docker run --rm --name skill-sql -e MYSQL_ROOT_PASSWORD=test -e MYSQL_DATABASE=skillbox -d mysql
timeout /t 18
docker exec -i skill-sql mysql -ptest skillbox < "%CD%\skillbox_sql_dump.sql" 2> warning.log
timeout /t 3
type %work%.sql
echo.
docker exec -i skill-sql mysql -ptest < "%CD%\%work%.sql" > %work%.log 2> warning.log
type %work%.log
REM docker exec -it skill-sql mysql -ptest
pause