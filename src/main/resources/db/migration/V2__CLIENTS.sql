CREATE TABLE clients
(
    id         VARCHAR(36) NOT NULL,
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    address    VARCHAR(255),
    deleted    BOOLEAN,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_clients PRIMARY KEY (id)
);