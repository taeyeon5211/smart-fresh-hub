use wms_db;

-- 1. 유저 데이터 생성
INSERT INTO user_table (user_login_id, user_password, user_address, user_email, user_phone, user_birth_date, user_type)
VALUES
    ('admin_1', 'password123', '100 Admin St', 'admin1@example.com', '010-1234-5678', '1985-01-01', 'admin'),
    ('business_1', 'password456', '200 Business Rd', 'business1@example.com', '010-5678-1234', '1990-02-02', 'client');

-- 2. 관리자 데이터 생성
INSERT INTO admin_table (admin_hire_date, admin_position, user_id)
VALUES
    ('2022-01-01', 'Manager', 1); -- 유저 ID와 연결

-- 3. 사업체 데이터 생성
INSERT INTO business_table (business_regist_num, business_name, business_address, user_id)
VALUES
    ('1234567890', 'FreshHub Inc.', '300 Company Ln', 2); -- 유저 ID와 연결

-- 4. 카테고리 생성
-- 4.1 대분류 테이블
INSERT INTO category_main (category_name)
VALUES
    ('Fruits');

-- 4.2 중분류 테이블
INSERT INTO c_mid_level (category_name, category_main_id)
VALUES
    ('Citrus', 1); -- 대분류 카테고리 참조

-- 5. 제품 데이터 생성
INSERT INTO product (product_size, product_name, category_mid_id, storage_temperature, expiration_date, business_id)
VALUES
    (10, 'Orange', 1, 5, '2023-12-31', 1), -- Citrus 카테고리 & FreshHub 사업체
    (20, 'Lemon', 1, 5, '2024-01-15', 1); -- Citrus 카테고리 & FreshHub 사업체

-- 6. 입고 데이터 생성
INSERT INTO inbound_table (inbound_date, inbound_request_date, inbound_status, admin_id, inbound_amount, product_id)
VALUES
    (NOW(), NOW(), '승인', 1, 100, 1), -- Orange 제품 입고
    (NOW(), NOW(), '대기', 1, 50, 2); -- Lemon 제품 입고

select * from inbound_table;

-- 7. 출고 테스트를 위한 데이터 생성
-- 출고 요청 데이터는 제품 ID 및 관리자 ID가 유효한 데이터에 기반해야 함.

INSERT INTO outbound_table (outbound_request_date, outbound_status, admin_id, outbound_amount, product_id)
VALUES
    (NOW(), '대기', 1, 30, 1), -- Orange 제품 출고 대기
    (NOW(), '승인', 1, 20, 2); -- Lemon 제품 출고 승인

select * from outbound_table;

-- 창고 데이터 삽입
select * from storage_condition;
INSERT INTO warehouse_table (warehouse_name, warehouse_space, warehouse_address, warehouse_amount)
VALUES
    ('Central Warehouse', 500, '123 Main St, City', 10000),
    ('East Warehouse', 800, '456 East Ave, City', 15000),
    ('West Warehouse', 600, '789 West Rd, City', 12000);

INSERT INTO storage_condition (storage_name, min_temp, max_temp, description)
VALUES
    ('Ultra-Low_Temp', -60, -18, '초저온 보관: 백신, 특수 의약품 및 일부 연구 샘플 보관'),
    ('Frozen_Storage', -18, -10, '냉동 보관: 육류, 해산물, 냉동 식품 및 장기 보관용 식품'),
    ('Refrigerated_Storage', 0, 4, '냉장 보관: 유제품, 신선 식품, 일부 의약품 및 과일'),
    ('Cool_Storage', 5, 9, '저온 보관: 일부 과일 및 채소, 와인, 특수 식품 보관'),
    ('Room_Temperature', 10, 25, '상온 보관: 일반 식품, 건조 식품, 음료 및 공산품'),
    ('Heated_Storage', 40, 65, '냉온장 보관: 따뜻한 상태 유지가 필요한 식품 및 특수 물품');

-- 구역 데이터 삽입 (창고 공간보다 작게 설정)
INSERT INTO area_table (area_space, area_code, area_price, warehouse_id, storage_id)
VALUES
-- Central Warehouse (500m² 이하)
(150, 'A', 50, 1, NULL),
(200, 'B', 60, 1, 2),
(100, 'C', 55, 1, NULL),

-- East Warehouse (800m² 이하)
(300, 'A', 70, 2, 3),
(250, 'B', 80, 2, NULL),
(150, 'C', 75, 2, 1),

-- West Warehouse (600m² 이하)
(200, 'A', 65, 3, NULL),
(250, 'B', 70, 3, 4),
(100, 'C', 60, 3, NULL);

INSERT INTO user_table (user_login_id, user_password, user_address, user_email, user_phone, user_birth_date, user_type)
VALUES
    ('johndoe', 'password123', '1234 Elm Street, City, Country', 'johndoe@example.com', '010-1234-5678', '1990-05-15', 'client'),
    ('adminuser', 'adminpassword', '5678 Oak Avenue, City, Country', 'admin@example.com', '010-2345-6789', '1985-11-25', 'admin'),
    ('alicebrown', 'alicepass2023', '7890 Pine Road, City, Country', 'alicebrown@example.com', '010-3456-7890', '1992-08-30', 'client'),
    ('bobsmith', 'bobpassword456', '1234 Maple Lane, City, Country', 'bobsmith@example.com', '010-4567-8901', '1988-03-20', 'client'),
    ('charliewhite', 'charliepass789', '5678 Birch Boulevard, City, Country', 'charliewhite@example.com', '010-5678-9012', '1995-12-10', 'client');

select * from user_table;

INSERT INTO business_table (business_regist_num, business_name, business_address, user_id)
VALUES
    ('123-45-67890', 'Tech Innovators', '4567 Tech Park, Silicon Valley, USA', 1),
    ('234-56-78901', 'Green Solutions', '7890 Green Street, City, Country', 2),
    ('345-67-89012', 'HealthCare Pro', '1234 Wellness Road, City, Country', 3),
    ('456-78-90123', 'Foodie Haven', '5678 Culinary Blvd, City, Country', 4),
    ('567-89-01234', 'Home Essentials', '9102 Home St, City, Country', 5);

select * from business_table;

INSERT INTO category_main (category_name)
VALUES
    ('Vegetables');

select * from category_main;

INSERT INTO c_mid_level (category_name, category_main_id)
VALUES
    ('Berries', 1),   -- 'Fruits' 대분류의 중분류
    ('Tropical Fruits', 1),  -- 'Fruits' 대분류의 중분류
    ('Leafy Greens', 4),     -- 'Vegetables' 대분류의 중분류
    ('Root Vegetables', 4);  -- 'Vegetables' 대분류의 중분류

select * from c_mid_level;
select * from business_table;

INSERT INTO product (product_size, product_name, category_mid_id, storage_temperature, expiration_date, business_id)
VALUES
    (5, 'Orange', 1, 5, '2025-12-31', 1),  -- 'Citrus' 중분류, 사업체 1번
    (8, 'Strawberry', 6, 4, '2025-06-30', 2),  -- 'Berries' 중분류, 사업체 2번
    (7, 'Mango', 7, 10, '2025-08-20', 3),  -- 'Tropical Fruits' 중분류, 사업체 3번
    (6, 'Spinach', 8, 2, '2025-07-15', 4),  -- 'Leafy Greens' 중분류, 사업체 4번
    (9, 'Carrot', 9, 0, '2025-11-10', 5);  -- 'Root Vegetables' 중분류, 사업체 5번

select * from product;

select * from area_table;
select * from product;

-- autoincrement 에 맞게 productid, area_id 수정,
insert into revenue_table (revenue_amount, product_id, area_id) VALUES
                                                                    (5,1,1),
                                                                    (10,2, 2),
                                                                    (5,28,3),
                                                                    (5,29,4);

select * from revenue_table;

select * from outbound_table;
select * from outbound_table where product_id in (select product_id from product where business_id = 2);

UPDATE revenue_table r
    JOIN product p ON r.product_id = p.product_id
    JOIN outbound_table o ON o.product_id = p.product_id
SET r.revenue_amount = r.revenue_amount - o.outbound_amount
WHERE o.outbound_status = '승인'
  AND r.revenue_amount >= o.outbound_amount; -- 재고 부족 시 업데이트되지 않도록 조건 추가

select * from revenue_table;
select * from revenue_history_table;
-- 승인된 출고목록
select distinct r.revenue_amount, o.outbound_id, o.outbound_amount, p.business_id from revenue_table r
    JOIN product p ON r.product_id = p.product_id
    JOIN outbound_table o ON o.product_id = p.product_id
WHERE o.outbound_status = '승인';

select * from outbound_table where outbound_status = '대기';
update outbound_table set outbound_status = '승인' where outbound_id = 1;
select * from revenue_history_table;

select * from business_table;

select * from outbound_table;
delete from outbound_table where outbound_id = 12;