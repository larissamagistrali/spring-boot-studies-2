DROP TABLE  IF EXISTS  admin;
DROP TABLE  IF EXISTS  professor;
DROP TABLE  IF EXISTS  student;
DROP TABLE  IF EXISTS  log;
DROP TABLE  IF EXISTS  login;
DROP TABLE  IF EXISTS  discipline;

create table login(
	id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	email varchar(32) not null,
	password varchar not null,
	created_by varchar(32) not null,
	updated_by varchar(32),
	created timestamp not null,
	updated timestamp,
	role integer not null,
	first_time boolean not null,
	name varchar(64) not null
);

create table log(
	id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	login_id integer not null,
	created_by varchar(32) not null,
	updated_by varchar(32),
	created timestamp not null,
	updated timestamp,
	foreign key (login_id) references login(id)
);

create table admin(
	login_id integer not null,
	registration varchar(12) not null,
	primary key (login_id),
	foreign key (login_id) references login(id) 	
);

create table professor(
	login_id integer not null,
	registration varchar(12) not null,
	primary key (login_id),
	foreign key (login_id) references login(id) 
);

create table student(
	login_id integer not null,
	registration varchar(12) not null,
	primary key (login_id),
	foreign key (login_id) references login(id) 
);

create table discipline(
  	discipline_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  	code varchar(16) not null,
  	name varchar(64) not null,
  	observation varchar(2048),
  	created_time timestamp not null,
 	created_by varchar(32) not null,
  	updated_time timestamp,
  	updated_by varchar(32)
);