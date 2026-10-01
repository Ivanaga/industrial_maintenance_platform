CREATE TABLE sensors (
    id BIGSERIAL PRIMARY KEY,
    name varchar(255) NOT NULL,
    type varchar(50) NOT NULL,
    unit varchar(50) NOT NULL,
    machine_id BIGINT NOT NULL,
    active BOOLEAN NOT NULL,

    CONSTRAINT fk_sensors_machine
    FOREIGN KEY (machine_id)
    REFERENCES machines(id));