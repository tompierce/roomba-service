CREATE SCHEMA IF NOT EXISTS roomba_service;

CREATE TABLE IF NOT EXISTS roomba_service.simulations (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  instructions text,
  start_x int(11) DEFAULT NULL,
  start_y int(11) DEFAULT NULL,
  finish_x int(11) DEFAULT NULL,
  finish_y int(11) DEFAULT NULL,
  room_size_x int(11) DEFAULT NULL,
  room_size_y int(11) DEFAULT NULL,
  num_clean_patches int(11) DEFAULT NULL,
  dirt_patches text,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
