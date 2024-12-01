-- Insert sample products
INSERT INTO product (name, description, price, quantity_in_stock) VALUES
('Product 1', 'description product 1 is ....', 1500.00, 10),
('Product 2', 'description product 2 is ....', 1000.00, 15),
('Product 3', 'description product 3 is ....', 200.00, 25),
('Product 4', 'description product 4 is ....', 80.00, 50),
('Product 5', 'description product 5 is ....', 25.00, 100);

-- Insert sample customers
INSERT INTO customer (first_name, last_name, email, contact_number) VALUES
('Customer', 'One', 'test1@mail.com', '88005553535'),
('Customer', 'Two', 'test2@mail.com', '88005553535'),
('Customer', 'Three', 'test3@mail.com', '88005553535'),
('Customer', 'Four', 'test4@mail.com', '88005553535');


INSERT INTO shop_order (customer_id, shipping_address, total_price, order_status)
VALUES
(1, 'Novigrag, Temple island, 1 ', 2500.00, 'PENDING');

INSERT INTO shop_order (customer_id, shipping_address, total_price, order_status)
VALUES
(2, 'Velen, Baron street, 1', 225.00, 'SHIPPED');

INSERT INTO order_product (order_id, product_id) VALUES
(1, 1),
(1, 2);


INSERT INTO order_product (order_id, product_id) VALUES
(2, 3),
(2, 5);
