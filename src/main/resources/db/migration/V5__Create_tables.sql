DROP TABLE  IF EXISTS  log;
DROP TABLE  IF EXISTS  login;

create table login(
	email varchar(32) not null,
	password varchar not null,
	created_by varchar(32) not null,
	updated_by varchar(32),
	created timestamp not null,
	updated timestamp,
	role integer not null,
	name varchar(64) not null,
	primary key (email)
);

create table log(
	id integer not null,
	email varchar(32) not null,
	created_by varchar(32) not null,
	updated_by varchar(32) not null,
	created timestamp not null,
	updated timestamp not null,
	primary key (id),
	foreign key (email) references login(email)
);