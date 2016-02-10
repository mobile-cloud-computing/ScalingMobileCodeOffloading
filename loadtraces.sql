CREATE DATABASE qoe;

USE qoe;

CREATE USER huberuser IDENTIFIED BY 'huber'; 

grant usage on *.* to huberuser@localhost identified by 'huber'; 
grant all privileges on feedback.* to huberuser@localhost; 

CREATE TABLE loadtraces(
	id INT NOT NULL AUTO_INCREMENT,
	PRIMARY KEY(id),
	timestamp  REAL,
 	device    TEXT,
  	acceleration INTEGER,
	rtt   REAL,
	energy   REAL,
	responsetime REAL);

