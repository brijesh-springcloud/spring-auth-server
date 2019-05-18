CREATE DATABASE oauth2_dev;

USE oauth2_dev;


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

  insert into oauth_client_details (
	client_id,client_secret,
	resource_ids,
	scope,
	authorized_grant_types,
	web_server_redirect_uri,authorities,
	access_token_validity,refresh_token_validity,
	additional_information,autoapprove)
	values(
    'USER_CLIENT_APP','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
	'user_client_resource,user_admin_resource',
	'role_admin,role_user',
	'authorization_code,password,refresh_token,implicit',
	null,'role_admin',
	900,3600,
	'{}',null);



insert into permission (name) values 
('can_create_user'),
('can_update_user'),
('can_read_user'),
('can_delete_user');



insert into role (name) values 
('role_admin'),('role_user');  


insert into permission_role (permission_id, role_id) values 
(1,1), -- can_create_user assigned to role_admin 
(2,1), -- can_update_user assigned to role_admin 
(3,1), -- can_read_user assigned to role_admin 
(4,1), -- can_delete_user assigned to role_admin 
(3,2);  -- can_read_user assigned to role_user 


insert into user (
username,password,
email,enabled,account_expired,credentials_expired,account_locked) values (
'admin','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
'admin@gmail.com',1,0,0,0),
('user','{bcrypt}$2a$10$EOs8VROb14e7ZnydvXECA.4LoIhPOoFHKvVF/iBZ/ker17Eocz4Vi',
'user@gmail.com',1,0,0,0);


insert into role_user (role_id, user_id)
    values 
    (1, 1), -- role_admin assigned to admin user  
    (2, 2); -- role_user assigned to user user  

commit;