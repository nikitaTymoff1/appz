INSERT INTO public.buses (id, capacity, name, status)
VALUES (1, 50, 'B23', 'ACTIVE');
INSERT INTO public.routes (id, departure, departure_date, destination, destination_date, bus_id)
VALUES (1, 'Kyiv', '2021-03-28 15:39:49', 'Wroclaw', '2021-03-29 15:39:58', 1);
INSERT INTO public.routes (id, departure, departure_date, destination, destination_date, bus_id)
VALUES (2, 'Paris', '2021-03-28 17:35:32', 'Tokyo', '2021-03-30 17:35:41', 1);
INSERT INTO public.routes (id, departure, departure_date, destination, destination_date, bus_id)
VALUES (3, 'Dubai', '2021-03-29 17:36:01', 'Barcelona', '2021-04-28 17:36:13', 1);