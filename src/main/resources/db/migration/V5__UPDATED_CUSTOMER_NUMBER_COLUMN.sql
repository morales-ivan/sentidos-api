ALTER TABLE customers
    ADD CONSTRAINT uc_customers_customernumber UNIQUE (customer_number);

ALTER TABLE customers
    ALTER COLUMN customer_number SET NOT NULL;