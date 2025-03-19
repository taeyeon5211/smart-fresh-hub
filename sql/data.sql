
-- 유저 테이블에 10개 목업 데이터 삽입
INSERT INTO user_table (user_login_id, user_password, user_address, user_email, user_phone, user_birth_date, user_type)
VALUES
    ('user1', 'password1', 'Seoul, Gangnam', 'user1@example.com', '010-1111-1111', '1990-01-01', 'client'),
    ('user2', 'password2', 'Seoul, Mapo', 'user2@example.com', '010-2222-2222', '1985-05-12', 'client'),
    ('user3', 'password3', 'Busan, Haeundae', 'user3@example.com', '010-3333-3333', '1992-03-25', 'admin'),
    ('user4', 'password4', 'Daegu, Dalseo', 'user4@example.com', '010-4444-4444', '1988-07-30', 'client'),
    ('user5', 'password5', 'Incheon, Bupyeong', 'user5@example.com', '010-5555-5555', '1994-11-11', 'client'),
    ('user6', 'password6', 'Seoul, Yongsan', 'user6@example.com', '010-6666-6666', '1982-02-20', 'admin'),
    ('user7', 'password7', 'Seoul, Jongno', 'user7@example.com', '010-7777-7777', '1990-12-14', 'client'),
    ('user8', 'password8', 'Gyeonggi, Suwon', 'user8@example.com', '010-8888-8888', '1995-09-18', 'client'),
    ('user9', 'password9', 'Busan, Seo-gu', 'user9@example.com', '010-9999-9999', '1986-06-09', 'admin'),
    ('user10', 'password10', 'Seoul, Gangdong', 'user10@example.com', '010-0000-0000', '1989-10-25', 'client');

-- 관리자 테이블에 5개 목업 데이터 삽입 (user_id는 이미 유저 테이블에 있는 값)
INSERT INTO admin_table (admin_hire_date, admin_position, user_id)
VALUES
    ('2015-01-01', 'Manager', 3),
    ('2016-03-10', 'Director', 6),
    ('2017-08-20', 'Team Lead', 9),
    ('2018-04-15', 'Supervisor', 1),
    ('2019-07-30', 'Coordinator', 10);

-- 사업체 테이블에 5개 목업 데이터 삽입 (user_id는 이미 유저 테이블에 있는 값)
INSERT INTO business_table (business_regist_num, business_name, business_address, user_id)
VALUES
    ('1234567890', 'TechCorp', 'Seoul, Gangnam', 2),
    ('2345678901', 'FoodMart', 'Busan, Haeundae', 4),
    ('3456789012', 'Global Logistics', 'Incheon, Bupyeong', 5),
    ('4567890123', 'Design Studio', 'Seoul, Yongsan', 7),
    ('5678901234', 'MediaWorks', 'Gyeonggi, Suwon', 8);

-- storage_condition 테이블에 6개 목업 데이터 삽입
INSERT INTO storage_condition (storage_name, min_temp, max_temp, description)
VALUES
    ('Ultra-Low_Temp', -80, -60, 'Used for storing materials that require ultra-low temperatures, typically for biological samples or certain chemicals.'),
    ('Frozen_Storage', -20, -10, 'For storing frozen foods and pharmaceuticals that need to remain at sub-zero temperatures.'),
    ('Refrigerated_Storage', 1, 8, 'Maintains a cold environment suitable for perishable items like dairy, meat, and some pharmaceuticals.'),
    ('Cool_Storage', 10, 15, 'Used for storing items that need a cool environment but not freezing, such as certain fruits or wine.'),
    ('Room_Temperature', 18, 22, 'Used for storing items that do not require refrigeration, such as dry goods, books, or electronics.'),
    ('Heated_Storage', 30, 40, 'Used for materials that require controlled heating, often for chemical or industrial processes.');

-- 카테고리 대분류 테이블에 데이터 삽입
INSERT INTO category_main (category_name)
VALUES
    ('음료'),
    ('가공식품'),
    ('신선식품'),
    ('냉동식품'),
    ('간식');

-- 카테고리 중분류 테이블에 데이터 삽입 (category_main_id는 대분류 테이블의 ID 값)
INSERT INTO c_mid_level (category_name, category_main_id)
VALUES
    ('탄산음료', 1),
    ('주스', 1),
    ('즉석식품', 2),
    ('냉장식품', 3),
    ('냉동피자', 4),
    ('과자', 5),
    ('초콜릿', 5),
    ('비스킷', 5);

-- 제품 테이블에 데이터 삽입 (business_id는 이미 사업체 테이블에 있는 값)
INSERT INTO product (product_size, product_name, category_mid_id, storage_temperature, expiration_date, business_id)
VALUES
    (5, '콜라', 1, 18, '2025-06-30', 2),
    (7, '오렌지 주스', 2, 1, '2025-07-15', 4),
    (3, '라면', 3, 22, '2025-05-20', 5),
    (6, '냉동피자', 4, -18, '2025-11-25', 3),
    (8, '감자칩', 5, 22, '2025-12-10', 2),
    (4, '초콜릿 바', 6, 22, '2025-08-01', 4),
    (5, '비스킷', 7, 22, '2025-09-05', 5),
    (10, '사이다', 1, 18, '2025-06-10', 3),
    (6, '과일즙', 2, 1, '2025-06-25', 2),
    (9, '피자', 4, -18, '2025-10-30', 3);

-- inbound_table에 10개 목업 데이터 삽입 (admin_id는 1~5 사이의 랜덤 값)
INSERT INTO inbound_table (inbound_date, inbound_request_date, inbound_status, admin_id, inbound_amount, product_id)
VALUES
    ('2025-03-01 10:00:00', '2025-02-28 09:00:00', '승인', 1, 5, 1),
    ('2025-03-02 12:00:00', '2025-03-01 11:00:00', '승인', 3, 3, 2),
    ('2025-03-03 14:00:00', '2025-03-02 13:00:00', '대기', 4, 7, 3),
    ('2025-03-04 16:00:00', '2025-03-03 15:00:00', '취소', 2, 2, 4),
    ('2025-03-05 18:00:00', '2025-03-04 17:00:00', '승인', 5, 4, 5),
    ('2025-03-06 20:00:00', '2025-03-05 19:00:00', '대기', 3, 6, 6),
    ('2025-03-07 22:00:00', '2025-03-06 21:00:00', '승인', 2, 9, 7),
    ('2025-03-08 08:00:00', '2025-03-07 07:00:00', '대기', 1, 3, 8),
    ('2025-03-09 10:00:00', '2025-03-08 09:00:00', '취소', 5, 5, 9),
    ('2025-03-10 12:00:00', '2025-03-09 11:00:00', '승인', 4, 8, 10);

-- outbound_table에 10개 목업 데이터 삽입 (admin_id는 1~5 사이의 랜덤 값, product_id는 기존 제품 테이블의 값)
INSERT INTO outbound_table (outbound_date, outbound_request_date, outbound_status, admin_id, outbound_amount, product_id)
VALUES
    ('2025-03-01 10:00:00', '2025-02-28 09:00:00', '승인', 2, 5, 1),
    ('2025-03-02 12:00:00', '2025-03-01 11:00:00', '대기', 4, 3, 2),
    ('2025-03-03 14:00:00', '2025-03-02 13:00:00', '승인', 3, 7, 3),
    ('2025-03-04 16:00:00', '2025-03-03 15:00:00', '취소', 5, 2, 4),
    ('2025-03-05 18:00:00', '2025-03-04 17:00:00', '승인', 1, 4, 5),
    ('2025-03-06 20:00:00', '2025-03-05 19:00:00', '대기', 3, 6, 6),
    ('2025-03-07 22:00:00', '2025-03-06 21:00:00', '승인', 2, 9, 7),
    ('2025-03-08 08:00:00', '2025-03-07 07:00:00', '대기', 1, 3, 8),
    ('2025-03-09 10:00:00', '2025-03-08 09:00:00', '취소', 4, 5, 9),
    ('2025-03-10 12:00:00', '2025-03-09 11:00:00', '승인', 5, 8, 10);

-- warehouse_table에 10개 목업 데이터 삽입 (warehouse_space는 1000~1500 사이)
INSERT INTO warehouse_table (warehouse_name, warehouse_space, warehouse_address, warehouse_amount)
VALUES
    ('Warehouse A', 1200, '123 Main St, City A', 500),
    ('Warehouse B', 1350, '456 Market St, City B', 200),
    ('Warehouse C', 1100, '789 Industrial Rd, City C', 800),
    ('Warehouse D', 1400, '101 Tech Ave, City D', 300),
    ('Warehouse E', 1250, '202 Central Blvd, City E', 600),
    ('Warehouse F', 1300, '303 Highway St, City F', 450),
    ('Warehouse G', 1150, '404 Park Ln, City G', 700),
    ('Warehouse H', 1450, '505 River Rd, City H', 100),
    ('Warehouse I', 1050, '606 Hill St, City I', 900),
    ('Warehouse J', 1500, '707 Coastline Dr, City J', 650);


-- area_table에 목업 데이터 삽입 (각 warehouse_space를 넘지 않도록 설정, storage_id도 반영)
INSERT INTO area_table (area_space, area_code, area_price, warehouse_id, storage_id)
VALUES
    (200, 'A', 100, 1, 1),  -- Warehouse A: total area_space = 200 (최대 1200), storage_id 1: Ultra-Low_Temp
    (300, 'B', 150, 1, 2),  -- Warehouse A: total area_space = 500 (최대 1200), storage_id 2: Frozen_Storage
    (400, 'C', 120, 1, 3),  -- Warehouse A: total area_space = 900 (최대 1200), storage_id 3: Refrigerated_Storage
    (100, 'A', 80, 2, 4),  -- Warehouse B: total area_space = 100 (최대 1350), storage_id 4: Cool_Storage
    (200, 'B', 100, 2, 5),  -- Warehouse B: total area_space = 300 (최대 1350), storage_id 5: Room_Temperature
    (300, 'C', 130, 2, 6),  -- Warehouse B: total area_space = 600 (최대 1350), storage_id 6: Heated_Storage
    (250, 'A', 110, 3, 1),  -- Warehouse C: total area_space = 250 (최대 1100), storage_id 1: Ultra-Low_Temp
    (300, 'B', 140, 3, 2),  -- Warehouse C: total area_space = 550 (최대 1100), storage_id 2: Frozen_Storage
    (350, 'C', 120, 3, 3),  -- Warehouse C: total area_space = 900 (최대 1100), storage_id 3: Refrigerated_Storage
    (400, 'A', 150, 4, 4), -- Warehouse D: total area_space = 400 (최대 1400), storage_id 4: Cool_Storage
    (300, 'B', 100, 4, 5), -- Warehouse D: total area_space = 700 (최대 1400), storage_id 5: Room_Temperature
    (350, 'C', 130, 4, 6), -- Warehouse D: total area_space = 1050 (최대 1400), storage_id 6: Heated_Storage
    (300, 'A', 120, 5, 1), -- Warehouse E: total area_space = 300 (최대 1250), storage_id 1: Ultra-Low_Temp
    (350, 'B', 130, 5, 2), -- Warehouse E: total area_space = 650 (최대 1250), storage_id 2: Frozen_Storage
    (400, 'C', 140, 5, 3), -- Warehouse E: total area_space = 1050 (최대 1250), storage_id 3: Refrigerated_Storage
    (200, 'A', 90, 6, 4),  -- Warehouse F: total area_space = 200 (최대 1300), storage_id 4: Cool_Storage
    (300, 'B', 110, 6, 5),  -- Warehouse F: total area_space = 500 (최대 1300), storage_id 5: Room_Temperature
    (350, 'C', 130, 6, 6),  -- Warehouse F: total area_space = 850 (최대 1300), storage_id 6: Heated_Storage
    (300, 'A', 110, 7, 1),  -- Warehouse G: total area_space = 300 (최대 1150), storage_id 1: Ultra-Low_Temp
    (350, 'B', 120, 7, 2),  -- Warehouse G: total area_space = 650 (최대 1150), storage_id 2: Frozen_Storage
    (400, 'C', 130, 7, 3),  -- Warehouse G: total area_space = 1050 (최대 1150), storage_id 3: Refrigerated_Storage
    (250, 'A', 90, 8, 4),  -- Warehouse H: total area_space = 250 (최대 1450), storage_id 4: Cool_Storage
    (350, 'B', 120, 8, 5),  -- Warehouse H: total area_space = 600 (최대 1450), storage_id 5: Room_Temperature
    (450, 'C', 140, 8, 6),  -- Warehouse H: total area_space = 1050 (최대 1450), storage_id 6: Heated_Storage
    (300, 'A', 100, 9, 1),  -- Warehouse I: total area_space = 300 (최대 1050), storage_id 1: Ultra-Low_Temp
    (350, 'B', 110, 9, 2),  -- Warehouse I: total area_space = 650 (최대 1050), storage_id 2: Frozen_Storage
    (400, 'C', 120, 9, 3),  -- Warehouse I: total area_space = 1050 (최대 1050), storage_id 3: Refrigerated_Storage
    (200, 'A', 90, 10, 4), -- Warehouse J: total area_space = 200 (최대 1500), storage_id 4: Cool_Storage
    (300, 'B', 110, 10, 5), -- Warehouse J: total area_space = 500 (최대 1500), storage_id 5: Room_Temperature
    (400, 'C', 130, 10, 6); -- Warehouse J: total area_space = 900 (최대 1500), storage_id 6: Heated_Storage

