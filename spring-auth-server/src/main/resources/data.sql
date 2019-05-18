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
	'USER_CLIENT_RESOURCE,USER_ADMIN_RESOURCE',
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