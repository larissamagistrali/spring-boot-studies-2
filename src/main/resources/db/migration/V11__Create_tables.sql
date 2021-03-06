DROP TABLE  IF EXISTS  admin;
DROP TABLE  IF EXISTS  professor;
DROP TABLE  IF EXISTS  student;
DROP TABLE  IF EXISTS  log;
DROP TABLE  IF EXISTS  login;

create table login(
	id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	login varchar(32) not null,
	password varchar(32) not null,
	login_criacao varchar(32) not null,
	login_atualizacao varchar(32) not null,
	criacao timestamp not null,
	atualizacao timestamp not null,
	perfilId integer not null,
	nome varchar(64) not null,
	email varchar(32) not null
);

create table log(
	id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
	id_login integer not null,
	login varchar(32) not null,
	login_criacao varchar(32) not null,
	login_atualizacao varchar(32) not null,
	criacao timestamp not null,
	atualizacao timestamp not null,
	foreign key (id_login) references login(id)
);

create table admin(
	id integer not null,
	registration varchar(12) not null,
	primary key (id),
	foreign key (id) references login(id) 
	
);

create table professor(
	id integer not null,
	registration varchar(12) not null,
	primary key (id),
	foreign key (id) references login(id) 
);

create table student(
	id integer not null,
	registration varchar(12) not null,
	primary key (id),
	foreign key (id) references login(id) 
);

	