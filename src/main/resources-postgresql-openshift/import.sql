--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the 'License');
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an 'AS IS' BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- Category 1
insert into Category (name, description) values ('Hot Drinks', 'Try our delicious Coffees and Teas')
-- Category 2
insert into Category (name, description) values ('Soft Drinks', 'All the product lines form Coke and Pepsi')
-- Category 3
insert into Category (name, description) values ('Favorites', 'Bestseller products')
-- Category 4
insert into Category (name, description) values ('Pastry', 'Coffees and Teas')
-- Category 5
insert into Category (name, description) values ('Cakes and Pies', 'Cookies, Pies, cakes and other mind twisting pastrys')

-- Supply 1
insert into Supply (name, price, unity, stock) values ('Coffee Beans', '40.0', 'kg', '100')
-- Supply 2
insert into Supply (name, price, unity) values ('Milk', '2.00', 'l')
-- Supply 3
insert into Supply (name, price, unity, stock) values ('Suggar', '3', 'kg', '100')
-- Supply 4
insert into Supply (name, price, unity, stock) values ('Chocolate Powder', '25.20', 'kg', '100')

-- Product 1
insert into Product (name, imageUrl) values ('Black Coffee', '/img/black_coffee.jpg');

-- Ingredient 1
insert into Product_Supply (product_id, supply_id) values (1, 1);

-- Ingredient 2
insert into Product_Supply (product_id, supply_id) values (1, 3);

-- RetailOption 1
insert into RetailOption (name, shortName, description, price, product_id) values ('Large', 'L', 'Large 300ml Coffee', 4.5, 1);

-- Composition 1
insert into Composition (quantity, retailOption_id, supply_id) values (0.000005, 1, 3);

-- Composition 2
insert into Composition (quantity, retailOption_id, supply_id) values (0.00000003, 1, 1);

-- Category 1
insert into Product_Category (product_id, category_id) values (1, 1);

-- Category 2
insert into Product_Category (product_id, category_id) values (1, 3);
