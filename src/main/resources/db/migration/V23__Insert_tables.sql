INSERT INTO public.login(
    email, password, created_by, created, role, first_time, name)
	VALUES ('admin@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Admin');
	
INSERT INTO public.login(
    email, password, created_by, created, role, first_time, name)
	VALUES ('professor@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Professor');
	
INSERT INTO public.login(
    email, password, created_by, created, role, first_time, name)
	VALUES ('student@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Student');