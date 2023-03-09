create table users (
	id serial primary key,
	username varchar(128) unique not null,
	first_name varchar(128) not null,
	last_name varchar(128) not null,
	password_hash varchar(60) not null
);

grant all priv
