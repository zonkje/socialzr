[![CircleCI](https://circleci.com/gh/zonkje/socialzr/tree/master.svg?style=svg)](https://circleci.com/gh/zonkje/socialzr/tree/master)

Run this command to start MySQL DB
```
docker run --name socialzrdb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
```