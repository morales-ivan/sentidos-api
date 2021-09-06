CREATE TABLE customers_media
(
    customer_id VARCHAR(36) NOT NULL,
    media_id    VARCHAR(36) NOT NULL
);

CREATE TABLE media
(
    id        VARCHAR(36) NOT NULL,
    image_url VARCHAR(255),
    audio_url VARCHAR(255),
    CONSTRAINT pk_media PRIMARY KEY (id)
);

ALTER TABLE customers_media
    ADD CONSTRAINT uc_customers_media_media UNIQUE (media_id);

ALTER TABLE customers_media
    ADD CONSTRAINT fk_cusmed_on_customer FOREIGN KEY (customer_id) REFERENCES customers (id);

ALTER TABLE customers_media
    ADD CONSTRAINT fk_cusmed_on_media FOREIGN KEY (media_id) REFERENCES media (id);