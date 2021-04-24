ALTER TABLE admin DROP id;
ALTER TABLE professor DROP id;
ALTER TABLE student DROP id;

ALTER TABLE admin ADD PRIMARY KEY (email);
ALTER TABLE professor ADD PRIMARY KEY (email);
ALTER TABLE student ADD PRIMARY KEY (email);