DROP TABLE  IF EXISTS  admin;
DROP TABLE  IF EXISTS  classes;
DROP TABLE  IF EXISTS  professor CASCADE;
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
  	id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  	code varchar(16) not null,
  	name varchar(64) not null,
  	observation varchar(2048),
  	created_time timestamp not null,
 	created_by varchar(32) not null,
  	updated_time timestamp,
  	updated_by varchar(32)
);

create table classes(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    observation varchar(2048),
    code varchar(5) not null,
	professor_id INTEGER NOT null,
	discipline_id INTEGER NOT null,
    foreign key (discipline_id) references discipline(id),
	foreign key (professor_id) references professor(login_id)
);


CREATE TABLE task(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title varchar(64),
    details varchar(2048),
    files bytea,
    task_time timestamp
);

CREATE TABLE classes_task(
    task_id INTEGER,
	classes_id INTEGER,
    foreign key (task_id) REFERENCES task(id),
    foreign key (classes_id) REFERENCES classes(id),
	PRIMARY KEY (task_id, classes_id)
);

CREATE TABLE classes_student(
    student_id INTEGER,
	classes_id INTEGER,
    foreign key (student_id) REFERENCES student(login_id),
    foreign key (classes_id) REFERENCES classes(id),
	PRIMARY KEY (student_id, classes_id)
);