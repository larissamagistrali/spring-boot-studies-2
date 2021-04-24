DROP TABLE  IF EXISTS  classes_task_Professor;
DROP TABLE  IF EXISTS  classes_student;
DROP TABLE  IF EXISTS  admin;
DROP TABLE  IF EXISTS  classes CASCADE;
DROP TABLE  IF EXISTS  professor CASCADE;
DROP TABLE  IF EXISTS  student CASCADE;
DROP TABLE  IF EXISTS  log;
DROP TABLE  IF EXISTS  login;
DROP TABLE  IF EXISTS  discipline;
DROP TABLE  IF EXISTS  task_Professor CASCADE;
DROP TABLE  IF EXISTS  assignments;
DROP TABLE  IF EXISTS  task_Student;
DROP TABLE  IF EXISTS  taskprofessor CASCADE;
DROP TABLE  IF EXISTS  taskstudent;
DROP TABLE  IF EXISTS  classes_task;
DROP TABLE  IF EXISTS  classes_taskprofessor;


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


CREATE TABLE task_professor(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status boolean NOT null,
    title varchar(64) NOT null,
    details varchar(2048) NOT null,
    files bytea,
    task_time timestamp NOT null
);

CREATE TABLE classes_task_professor(
    task_professor_id INTEGER,
	classes_id INTEGER,
    foreign key (task_professor_id) REFERENCES task_professor(id),
    foreign key (classes_id) REFERENCES classes(id),
	PRIMARY KEY (task_professor_id, classes_id)
);

CREATE TABLE classes_student(
    student_login_id INTEGER,
	classes_id INTEGER,
	foreign key (student_login_id) REFERENCES student(login_id),
    foreign key (classes_id) REFERENCES classes(id),
	PRIMARY KEY (student_login_id, classes_id)
);

CREATE TABLE task_student(
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    status boolean NOT null,
   	comment varchar(2048),
    files varchar(32768),
    task_professor_id INTEGER NOT NULL,
	student_login_id INTEGER NOT NULL,
    foreign key (task_professor_id) REFERENCES task_professor(id),
    foreign key (student_login_id) REFERENCES student(login_id)
);