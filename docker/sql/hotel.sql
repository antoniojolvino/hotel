CREATE TABLE public.room (
	"number" int4 NOT NULL,
	CONSTRAINT room_pkey PRIMARY KEY (number)
);

CREATE TABLE public.customer (
	identification_document varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	"name" varchar(50) NOT NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (identification_document),
	CONSTRAINT uk_dwk6cx0afu8bs9o4t536v1j5v UNIQUE (email)
);

CREATE TABLE public.booking (
	id int8 NOT NULL,
	end_date date NOT NULL,
	start_date date NOT NULL,
	customer_identification_document varchar(50) NOT NULL,
	room_number int4 NOT NULL,
	CONSTRAINT booking_pkey PRIMARY KEY (id)
);

CREATE SEQUENCE public.booking_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9999999999
	START 1
	CACHE 1
	NO CYCLE;

ALTER TABLE public.booking ADD CONSTRAINT booking_customer_fk FOREIGN KEY (customer_identification_document) REFERENCES public.customer(identification_document);
ALTER TABLE public.booking ADD CONSTRAINT booking_room_fk FOREIGN KEY (room_number) REFERENCES public.room("number");

INSERT INTO public.room ("number") VALUES(1);
INSERT INTO public.customer (identification_document, email, "name") VALUES('A123', 'john.doe@gmail.com', 'John Doe');
INSERT INTO public.customer (identification_document, email, "name") VALUES('B123', 'jane.doe@gmail.com', 'Jane Doe');