CREATE TABLE users (
	id varchar(10) PRIMARY KEY,
	name varchar(20) not null,
	password varchar(20) not null,
	level smallint not null,
	login integer not null,
	recommend integer not null
	)
;

DROP TABLE users;