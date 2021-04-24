create table login(
	login varchar(32) not null,
	password varchar(32) not null,
	login_criacao varchar(32) not null,
	login_atualizacao varchar(32) not null,
	criacao timestamp not null,
	atualizacao timestamp not null,
	primary key (login)
);

create table log(
	id integer not null,
	login varchar(32) not null,
	login_criacao varchar(32) not null,
	login_atualizacao varchar(32) not null,
	criacao timestamp not null,
	atualizacao timestamp not null,
	primary key (id),
	foreign key (login) references login(login)
);