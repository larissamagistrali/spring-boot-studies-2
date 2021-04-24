ALTER TABLE login DROP first_time;
ALTER TABLE login ADD first_time boolean not null;