create table client(
	name char(30),
	password varchar(128),
	rsa_p numeric,
	rsa_q numeric,
	rsa_e numeric,
	id serial
)