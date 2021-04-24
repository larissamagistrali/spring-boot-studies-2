INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('admin@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Admin');
	
INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('professor@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Professor de Teste 1');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('professor2@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Professor de Teste 2');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('professor3@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Professor de Teste 3');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('student@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Estudante de Teste 1');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('student2@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Estudante de Teste 2');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('student3@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Estudante de Teste 3');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('student4@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Estudante de Teste 4');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('student5@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'Estudante de Teste 5');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('professorteste@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'kong professor');

INSERT INTO public.login(email, password, created_by, created, role, first_time, name)
	VALUES ('studentteste@teste.com', '$2a$10$w9Q6OOIFfRk.FvkHGMYA5eeUIm7j.kyKK0eq2pUbA3G6bfdeqf5Hy', 'teste', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 0, false, 'kong student');

INSERT INTO public.professor(login_id, registration)
    VALUES (2, '251478121212');

INSERT INTO public.professor(login_id, registration)
    VALUES (3, '248188121452');

INSERT INTO public.professor(login_id, registration)
    VALUES (4, '514754121224');

INSERT INTO public.student(login_id, registration)
    VALUES (5, '181478121212');

INSERT INTO public.student(login_id, registration)
    VALUES (6, '182188121452');

INSERT INTO public.student(login_id, registration)
    VALUES (7, '191754121224');

INSERT INTO public.student(login_id, registration)
    VALUES (8, '162754121224');

INSERT INTO public.student(login_id, registration)
    VALUES (9, '201754121224');

INSERT INTO public.discipline(code, name, created_time, created_by)
    VALUES ('142151', 'Disciplina de Teste 1', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 'teste');

INSERT INTO public.discipline(code, name, created_time, created_by)
    VALUES ('123151', 'Disciplina de Teste 2', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 'teste');

INSERT INTO public.discipline(code, name, created_time, created_by)
    VALUES ('253151', 'Disciplina de Teste 3', to_timestamp('16-05-2011 15:36:38', 'dd-mm-yyyy hh24:mi:ss'), 'teste');

INSERT INTO public.classes(code, professor_id, discipline_id)
    VALUES ('12343', 2, 1);

INSERT INTO public.classes(code, professor_id, discipline_id)
    VALUES ('12344', 3, 2);

INSERT INTO public.classes(code, professor_id, discipline_id)
    VALUES ('54321', 4, 3);

INSERT INTO public.task_professor(status, title, details, task_time)
    VALUES (FALSE, 'Tarefa de Teste 1', 'Esta é uma tarefa que não contém absolutamente nada e serve apenas testar a view do sistema. Muito obrigado e uma boa tarde amigos do zap.', to_timestamp('01-04-2021 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));

INSERT INTO public.task_professor(status, title, details, task_time)
    VALUES (TRUE, 'Tarefa de Teste 2', 'Esta é uma tarefa que não contém absolutamente nada e serve apenas testar a view do sistema. Muito obrigado e uma boa tarde amigos do zap.', to_timestamp('01-04-2021 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));

INSERT INTO public.task_professor(status, title, details, task_time)
    VALUES (FALSE, 'Tarefa de Teste 2', 'Esta é uma tarefa que não contém absolutamente nada e serve apenas testar a view do sistema. Muito obrigado e uma boa tarde amigos do zap.', to_timestamp('01-04-2021 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));

INSERT INTO public.task_professor(status, title, details, task_time)
    VALUES (TRUE, 'Tarefa de Teste 3', 'Esta é uma tarefa que não contém absolutamente nada e serve apenas testar a view do sistema. Muito obrigado e uma boa tarde amigos do zap.', to_timestamp('01-04-2021 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));

INSERT INTO public.task_professor(status, title, details, task_time)
    VALUES (FALSE, 'Tarefa de Teste 4', 'Esta é uma tarefa que não contém absolutamente nada e serve apenas testar a view do sistema. Muito obrigado e uma boa tarde amigos do zap.', to_timestamp('01-04-2021 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));

INSERT INTO public.task_professor(status, title, details, task_time)
    VALUES (TRUE, 'Tarefa de Teste 5', 'Esta é uma tarefa que não contém absolutamente nada e serve apenas testar a view do sistema. Muito obrigado e uma boa tarde amigos do zap.', to_timestamp('01-04-2021 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));

INSERT INTO public.classes_task_professor(task_professor_id, classes_id)
    VALUES (1,1);

INSERT INTO public.classes_task_professor(task_professor_id, classes_id)
    VALUES (2,1);

INSERT INTO public.classes_task_professor(task_professor_id, classes_id)
    VALUES (3,2);

INSERT INTO public.classes_task_professor(task_professor_id, classes_id)
    VALUES (4,2);

INSERT INTO public.classes_task_professor(task_professor_id, classes_id)
    VALUES (5,3);

INSERT INTO public.classes_task_professor(task_professor_id, classes_id)
    VALUES (6,3);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (5,1);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (6,1);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (7,1);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (6,2);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (7,2);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (8,2);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (7,3);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (8,3);

INSERT INTO public.classes_student(student_login_id, classes_id)
    VALUES (9,3);


