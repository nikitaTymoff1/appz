create table buses
(
    id       serial  not null
        constraint buses_pkey
            primary key,
    capacity integer not null,
    name     varchar(255),
    status   varchar(255)
);

create table passengers
(
    id         serial not null
        constraint passengers_pkey
            primary key,
    email      varchar(255),
    name       varchar(255),
    patronymic varchar(255),
    surname    varchar(255)
);

create table luggage
(
    id           serial  not null
        constraint luggage_pkey
            primary key,
    luggage_type varchar(255),
    name         varchar(255),
    weight       integer not null,
    passenger_id serial
        constraint fkevi0rh6432rbs52qac92tqmjm
            references passengers
);

create table routes
(
    id               serial not null
        constraint routes_pkey
            primary key,
    departure        varchar(255),
    departure_date   timestamp,
    destination      varchar(255),
    destination_date timestamp,
    bus_id           serial
        constraint fkfifby6x0g0k9rmd26mdmef3vt
            references buses
);

create table tickets
(
    id           serial  not null
        constraint tickets_pkey
            primary key,
    price        integer not null,
    passenger_id serial
        constraint fk1ds262xq345nkvs5o9ptnftwr
            references passengers,
    route_id     serial
        constraint fkjndmi4mev27gnbw3m61y1hb8q
            references routes
);

