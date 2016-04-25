CREATE USER 'roomba'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON roomba_service.* TO 'roomba'@'%';
