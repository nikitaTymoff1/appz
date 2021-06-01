FROM library/postgres
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD root
ENV POSTGRES_DB bus_station
# COPY src/main/resources/db/migration/V1__Bus_Station.sql /docker-entrypoint-initdb.d/
EXPOSE 5433

# docker build -t buses .
# docker run --rm --name test -p 5433:5432 buses