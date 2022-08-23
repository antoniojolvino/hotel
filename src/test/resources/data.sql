INSERT INTO room (room_number) VALUES(1);
INSERT INTO customer (identification_document, email, name) VALUES('A123', 'john.doe@gmail.com', 'John Doe');
INSERT INTO customer (identification_document, email, name) VALUES('B123', 'jane.doe@gmail.com', 'Jane Doe');
insert into booking values (nextval('booking_seq'), CURRENT_DATE + 1, CURRENT_DATE + 3, 'B123', 1)