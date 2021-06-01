INSERT INTO public.buses (id, capacity, name, status)
VALUES (1, 50, 'B23', 'ACTIVE');
INSERT INTO public.routes (id, departure, departure_date, destination, destination_date, bus_id)
VALUES (1, 'Kyiv', '2021-03-28 15:39:49.000000', 'Wroclaw', '2021-03-29 15:39:58.000000', 1);
INSERT INTO public.passengers (id, email, name, patronymic, surname)
VALUES (1, 'abc@gmail.com', 'Oleg', null, 'Kucherenko');
INSERT INTO public.passengers (id, email, name, patronymic, surname)
VALUES (2, 'adc@gmail.com', 'Dima', null, 'Andriichuk');
INSERT INTO public.passengers (id, email, name, patronymic, surname)
VALUES (3, 'azx@gmail.com', 'Andrii', null, 'Batuiev');
INSERT INTO public.passengers (id, email, name, patronymic, surname)
VALUES (4, 'zzz@gmail.com', 'Serhii', null, 'Kachmarskiy');
INSERT INTO public.tickets (id, price, passenger_id, route_id)
VALUES (1, 1337, 1, 1);
INSERT INTO public.tickets (id, price, passenger_id, route_id)
VALUES (2, 1337, 2, 1);
INSERT INTO public.tickets (id, price, passenger_id, route_id)
VALUES (3, 1337, 3, 1);
INSERT INTO public.tickets (id, price, passenger_id, route_id)
VALUES (4, 1337, 4, 1);