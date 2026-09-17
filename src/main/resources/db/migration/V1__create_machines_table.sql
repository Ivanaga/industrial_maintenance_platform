CREATE TABLE machines (id BIGSERIAL PRIMARY KEY, 
name VARCHAR(255) NOT NULL,
serial_number VARCHAR(255) NOT NULL UNIQUE,
manufacturer VARCHAR(255),
model VARCHAR(255),
installation_date DATE,
location VARCHAR(255),
status VARCHAR(50) NOT NULL);