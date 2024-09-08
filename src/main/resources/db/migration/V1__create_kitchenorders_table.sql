-- Tabela de Customers
CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL
);
-- Adicionar um Customer na migração
INSERT INTO customers (email, nome) VALUES ('test@example.com', 'Test Customer');


-- Tabela de Orders
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    price NUMERIC NOT NULL,
    customer_id INT,
    CONSTRAINT fk_customer
      FOREIGN KEY (customer_id) 
      REFERENCES customers(id)
      ON DELETE SET NULL
      ON UPDATE CASCADE
);
