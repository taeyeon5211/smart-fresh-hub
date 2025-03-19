
USE wms_db;

-- 1. 유저 테이블
CREATE TABLE user_table
(

    user_id         INT AUTO_INCREMENT PRIMARY KEY,
    user_login_id   VARCHAR(50)              NOT NULL UNIQUE,
    user_name       VARCHAR(30)              NOT NULL,
    user_password   VARCHAR(255)             NOT NULL,
    user_address    VARCHAR(255),
    user_email      VARCHAR(100) UNIQUE,
    user_phone      VARCHAR(20),
    user_birth_date DATE,
    user_created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    user_type       ENUM ('admin', 'client') NOT NULL
);

-- 3. 관리자 테이블
CREATE TABLE admin_table (
                             admin_id INT AUTO_INCREMENT PRIMARY KEY,
                             admin_hire_date DATE NOT NULL,
                             admin_position VARCHAR(20),
                             user_id INT NOT NULL UNIQUE
);

-- 4. 사업체 테이블
CREATE TABLE business_table (
                                business_id INT AUTO_INCREMENT PRIMARY KEY,
                                business_regist_num VARCHAR(20) NOT NULL UNIQUE,
                                business_name VARCHAR(255) NOT NULL,
                                business_address VARCHAR(255) NOT NULL,
                                user_id INT NOT NULL
);

-- 5. 카테고리 대분류 테이블
CREATE TABLE category_main (
                               category_id INT AUTO_INCREMENT PRIMARY KEY,
                               category_name VARCHAR(50) NOT NULL UNIQUE
);

-- 6. 카테고리 중분류 테이블
CREATE TABLE c_mid_level (
                             category_mid_id INT AUTO_INCREMENT PRIMARY KEY,
                             category_name VARCHAR(50) NOT NULL,
                             category_main_id INT NOT NULL
);

-- 7. 제품 테이블
CREATE TABLE product (
                         product_id INT AUTO_INCREMENT PRIMARY KEY,
                         product_size INT NOT NULL,
                         product_name VARCHAR(50) NOT NULL,
                         category_mid_id INT NOT NULL,
                         storage_temperature INT,
                         expiration_date DATE,
                         business_id INT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 8. 입고 테이블
CREATE TABLE inbound_table (
                               inbound_id INT AUTO_INCREMENT PRIMARY KEY,
                               inbound_date TIMESTAMP,
                               inbound_request_date TIMESTAMP NOT NULL,
                               inbound_status ENUM('승인', '취소', '대기') NOT NULL,
                               admin_id INT NOT NULL,
                               inbound_amount INT NOT NULL,
                               product_id INT NOT NULL
);

-- 9. 출고 테이블
CREATE TABLE outbound_table (
                                outbound_id INT AUTO_INCREMENT PRIMARY KEY,
                                outbound_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                outbound_request_date TIMESTAMP,
                                outbound_status ENUM('승인', '대기', '취소') NOT NULL,
                                admin_id INT NOT NULL,
                                outbound_amount INT NOT NULL,
                                product_id INT NOT NULL
);

-- 10. 창고 테이블
CREATE TABLE warehouse_table (
                                 warehouse_id INT AUTO_INCREMENT PRIMARY KEY,
                                 warehouse_name VARCHAR(20) NOT NULL,
                                 warehouse_space INT NOT NULL,
                                 warehouse_address VARCHAR(200) NOT NULL,
                                 warehouse_amount INT NOT NULL DEFAULT 0
);

-- 11. 구역 테이블
CREATE TABLE area_table (
                            area_id INT AUTO_INCREMENT PRIMARY KEY,
                            area_space INT NOT NULL,
                            area_code CHAR(1) NOT NULL,
                            area_price INT NOT NULL,
                            warehouse_id INT NOT NULL,
                            storage_id INT
);

-- 12. 보관 상태 테이블
CREATE TABLE storage_condition (
                                   storage_id INT AUTO_INCREMENT PRIMARY KEY,
                                   storage_name ENUM(
                                       'Ultra-Low_Temp', 'Frozen_Storage', 'Refrigerated_Storage',
                                       'Cool_Storage', 'Room_Temperature', 'Heated_Storage'
                                       ) NOT NULL,
                                   min_temp INT NOT NULL,
                                   max_temp INT NOT NULL,
                                   description TEXT
);

-- 13. 재고 테이블
CREATE TABLE revenue_table (
                               revenue_id INT AUTO_INCREMENT PRIMARY KEY,
                               revenue_amount INT NOT NULL,
                               product_id INT NOT NULL,
                               area_id INT NOT NULL
);

-- 14. 재고 히스토리 테이블
CREATE TABLE revenue_history_table (
                                       revenue_id INT NOT NULL,
                                       change_date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                       revenue_quantity INT NOT NULL,
                                       change_type ENUM('입고', '출고', '폐기', '조정') NOT NULL,
                                       PRIMARY KEY (revenue_id, change_date)
);

-- 15. 회원 백업용 테이블

CREATE TABLE user_backup_table (
                                   backup_id INT AUTO_INCREMENT PRIMARY KEY, -- 백업 데이터의 고유 ID
                                   user_id INT, -- 삭제된 사용자 ID
                                   user_login_id VARCHAR(50) NOT NULL,
                                   user_name VARCHAR(100),
                                   user_password VARCHAR(255),
                                   user_address VARCHAR(255),
                                   user_email VARCHAR(100),
                                   user_phone VARCHAR(20),
                                   user_birth_date DATE,
                                   user_created_at DATETIME,
                                   user_type ENUM('admin', 'client') NOT NULL,
                                   deleted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 삭제된 시간 기록
);

-- 회원 백업용 FK
ALTER TABLE user_backup_table
    ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;

-- 관리자 FK
ALTER TABLE admin_table
    ADD CONSTRAINT fk_admin_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;

-- 사업체 FK
ALTER TABLE business_table
    ADD CONSTRAINT fk_business_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;

-- 카테고리 FK
ALTER TABLE c_mid_level
    ADD CONSTRAINT fk_category_mid_main FOREIGN KEY (category_main_id) REFERENCES category_main(category_id) ON DELETE CASCADE;

-- 제품 FK
ALTER TABLE product
    ADD CONSTRAINT fk_product_category FOREIGN KEY (category_mid_id) REFERENCES c_mid_level(category_mid_id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_product_business FOREIGN KEY (business_id) REFERENCES business_table(business_id) ON DELETE CASCADE;

-- 입고 FK
ALTER TABLE inbound_table
    ADD CONSTRAINT fk_inbound_admin FOREIGN KEY (admin_id) REFERENCES admin_table(admin_id),
    ADD CONSTRAINT fk_inbound_product FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE;

-- 출고 FK
ALTER TABLE outbound_table
    ADD CONSTRAINT fk_outbound_admin FOREIGN KEY (admin_id) REFERENCES admin_table(admin_id),
    ADD CONSTRAINT fk_outbound_product FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE;

-- 창고 FK
ALTER TABLE area_table
    ADD CONSTRAINT fk_area_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse_table(warehouse_id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_area_storage FOREIGN KEY (storage_id) REFERENCES storage_condition(storage_id) ON DELETE SET NULL;

-- 재고 FK
ALTER TABLE revenue_table
    ADD CONSTRAINT fk_revenue_product FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_revenue_area FOREIGN KEY (area_id) REFERENCES area_table(area_id) ON DELETE CASCADE;

-- 재고 히스토리 FK
ALTER TABLE revenue_history_table
    ADD CONSTRAINT fk_revenue_history_inventory FOREIGN KEY (revenue_id) REFERENCES revenue_table(revenue_id) ON DELETE CASCADE;





use wms_db;
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

INSERT INTO business_table (business_regist_num, business_name, business_address, user_id)
VALUES
    ('123-45-67890', 'Tech Innovators', '4567 Tech Park, Silicon Valley, USA', 1),
    ('234-56-78901', 'Green Solutions', '7890 Green Street, City, Country', 2),
    ('345-67-89012', 'HealthCare Pro', '1234 Wellness Road, City, Country', 3),
    ('456-78-90123', 'Foodie Haven', '5678 Culinary Blvd, City, Country', 4),
    ('567-89-01234', 'Home Essentials', '9102 Home St, City, Country', 5);

INSERT INTO category_main (category_name)
VALUES
    ('Fruits'),
    ('Vegetables');

INSERT INTO c_mid_level (category_name, category_main_id)
VALUES
    ('Citrus', 1),    -- 'Fruits' 대분류의 중분류
    ('Berries', 1),   -- 'Fruits' 대분류의 중분류
    ('Tropical Fruits', 1),  -- 'Fruits' 대분류의 중분류
    ('Leafy Greens', 2),     -- 'Vegetables' 대분류의 중분류
    ('Root Vegetables', 2);  -- 'Vegetables' 대분류의 중분류

INSERT INTO product (product_size, product_name, category_mid_id, storage_temperature, expiration_date, business_id)
VALUES
    (5, 'Orange', 1, 5, '2025-12-31', 1),  -- 'Citrus' 중분류, 사업체 1번
    (8, 'Strawberry', 2, 4, '2025-06-30', 2),  -- 'Berries' 중분류, 사업체 2번
    (7, 'Mango', 3, 10, '2025-08-20', 3),  -- 'Tropical Fruits' 중분류, 사업체 3번
    (6, 'Spinach', 4, 2, '2025-07-15', 4),  -- 'Leafy Greens' 중분류, 사업체 4번
    (9, 'Carrot', 5, 0, '2025-11-10', 5);  -- 'Root Vegetables' 중분류, 사업체 5번

select * from area_table;
select * from product;

-- autoincrement 에 맞게 productid, area_id 수정,
insert into revenue_table (revenue_amount, product_id, area_id) VALUES
(5,6,10),
(10,8,11),
(5,8,13),
(5,9,13);

INSERT INTO user_table (user_login_id, user_password, user_address, user_email, user_phone, user_birth_date, user_type)
VALUES
    ('admin1', 'password1', 'Seoul', 'admin1@example.com', '010-1111-1111', '1985-01-01', 'admin'),
    ('admin2', 'password2', 'Busan', 'admin2@example.com', '010-2222-2222', '1986-02-02', 'admin'),
    ('admin3', 'password3', 'Incheon', 'admin3@example.com', '010-3333-3333', '1987-03-03', 'admin'),
    ('admin4', 'password4', 'Daegu', 'admin4@example.com', '010-4444-4444', '1988-04-04', 'admin'),
    ('admin5', 'password5', 'Daejeon', 'admin5@example.com', '010-5555-5555', '1989-05-05', 'admin');

INSERT INTO admin_table (admin_hire_date, admin_position, user_id)
VALUES
    ('2020-01-01', 'Manager', 6),
    ('2020-02-01', 'Senior Manager', 7),
    ('2020-03-01', 'Supervisor', 8),
    ('2020-04-01', 'Assistant', 9),
    ('2020-05-01', 'Clerk', 10);

INSERT INTO inbound_table (inbound_date, inbound_request_date, inbound_status, admin_id, inbound_amount, product_id)
VALUES
    (NOW(), NOW(), '대기', 1, 100, 6),
    (NOW(), NOW(), '승인', 2, 150, 7),
    (NOW(), NOW(), '취소', 3, 200, 8),
    (NOW(), NOW(), '대기', 4, 250, 9),
    (NOW(), NOW(), '승인', 5, 300, 6),
    (NOW(), NOW(), '취소', 1, 120, 7),
    (NOW(), NOW(), '대기', 2, 180, 8),
    (NOW(), NOW(), '승인', 3, 220, 9),
    (NOW(), NOW(), '취소', 4, 260, 6),
    (NOW(), NOW(), '대기', 5, 310, 7);