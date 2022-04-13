-- # Set up MySQL command:
-- # docker run --name socialzrdb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
DROP DATABASE IF EXISTS socialzr_dev;
-- # Create Database
CREATE DATABASE socialzr_dev;

-- # Create database service account
CREATE USER 'socialzr_dev_user'@'localhost' IDENTIFIED BY 'szymek';
CREATE USER 'socialzr_dev_user'@'%' IDENTIFIED BY 'szymek';

-- # Database grants
GRANT ALL ON socialzr_dev.* TO 'socialzr_dev_user'@'%';
GRANT ALL ON socialzr_dev.* TO 'socialzr_dev_user'@'localhost';
