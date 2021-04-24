ALTER TABLE login DROP firstTime;
ALTER TABLE login ADD first_time BIT not null;