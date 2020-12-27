CREATE DATABASE IF NOT EXISTS ljprojectbuider;
CREATE USER 'ljprojectbuilder'@'localhost' IDENTIFIED BY 'ljprojectbuider';
GRANT ALL PRIVILEGES ON base.* To 'ljprojectbuilder'@'localhost';