
create table admin(
	id integer not null,
	registration varchar(12) not null,
	email varchar(32) not null,
	foreign key (email) references login(email) 
	
);

create table professor(
	id integer not null,
	registration varchar(12) not null,
	email varchar(32) not null,
	foreign key (email) references login(email) 
);

create table student(
	id integer not null,
	registration varchar(12) not null,
	email varchar(32) not null,
	foreign key (email) references login(email) 

);