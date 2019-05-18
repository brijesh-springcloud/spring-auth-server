

create table oauth_client_details 
(
	client_id varchar(255) not null primary key,
	client_secret varchar(255) not null,
	resource_ids varchar(255) default null,
	scope varchar(255) default null,
	authorized_grant_types varchar(255) default null,
	web_server_redirect_uri varchar(255) default null,
	authorities varchar(255) default null,
	access_token_validity int(11) default null,
	refresh_token_validity int(11) default null,
	additional_information varchar(4096) default null,
	autoapprove varchar(255) default null
);
 
 
create table permission 
(
	id int primary key auto_increment,
	name varchar(60) unique key
);


create table role 
(
	id int primary key auto_increment, 
	name varchar(60) unique key
);


create table permission_role
(
	permission_id int,
	foreign key(permission_id) references permission(id),
	role_id int,
	foreign key(role_id) references role(id)
);
    

create table user 
(
	id int primary key auto_increment,
	username varchar(24) unique key not null,
	password varchar(255) not null,
	email varchar(255) not null,
	enabled bit(1) not null,
	account_expired bit(1) not null,
	credentials_expired bit(1) not null,
	account_locked bit(1) not null
);
    
    
create table role_user
(
	role_id int,foreign key(role_id) references role(id),
	user_id int, foreign key(user_id) references user(id)
);
    
    
 commit;   