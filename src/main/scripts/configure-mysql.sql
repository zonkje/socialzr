-- # Set up MySQL command:
-- # docker run --name socialzrdb -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

-- # Create Database
CREATE DATABASE socialzr_dev;

-- # Create database service account
CREATE USER 'socialzr_dev_user'@'localhost' IDENTIFIED BY 'szymek';
CREATE USER 'socialzr_dev_user'@'%' IDENTIFIED BY 'szymek';

-- # Database grants
GRANT SELECT ON socialzr_dev.* TO 'socialzr_dev_user'@'localhost';
GRANT INSERT ON socialzr_dev.* TO 'socialzr_dev_user'@'localhost';
GRANT DELETE ON socialzr_dev.* TO 'socialzr_dev_user'@'localhost';
GRANT UPDATE ON socialzr_dev.* TO 'socialzr_dev_user'@'localhost';
GRANT SELECT ON socialzr_dev.* TO 'socialzr_dev_user'@'%';
GRANT INSERT ON socialzr_dev.* TO 'socialzr_dev_user'@'%';
GRANT DELETE ON socialzr_dev.* TO 'socialzr_dev_user'@'%';
GRANT UPDATE ON socialzr_dev.* TO 'socialzr_dev_user'@'%';