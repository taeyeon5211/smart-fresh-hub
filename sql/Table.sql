SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS revenue_history_table;
DROP TABLE IF EXISTS revenue_table;
DROP TABLE IF EXISTS outbound_table;
DROP TABLE IF EXISTS inbound_table;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS c_mid_level;
DROP TABLE IF EXISTS category_main;
DROP TABLE IF EXISTS business_table;
DROP TABLE IF EXISTS admin_table;
DROP TABLE IF EXISTS login_h_table;
DROP TABLE IF EXISTS user_table;
DROP TABLE IF EXISTS area_table;
DROP TABLE IF EXISTS warehouse_table;
DROP TABLE IF EXISTS storage_condition;

SET FOREIGN_KEY_CHECKS = 1;


USE wms_db;

-- 1. 유저 테이블
CREATE TABLE user_table (
                            user_id INT AUTO_INCREMENT PRIMARY KEY,
                            user_login_id VARCHAR(50) NOT NULL UNIQUE,
                            user_password VARCHAR(255) NOT NULL,
                            user_address VARCHAR(255),
                            user_email VARCHAR(100) UNIQUE,
                            user_phone VARCHAR(20),
                            user_birth_date DATE,
                            user_created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                            user_type ENUM('admin', 'client') NOT NULL
);

-- 2. 로그인 기록 테이블
CREATE TABLE login_h_table (
                               login_h_id INT AUTO_INCREMENT PRIMARY KEY,
                               login_h_time DATETIME DEFAULT CURRENT_TIMESTAMP,
                               login_h_logout_time DATETIME,
                               user_id INT NOT NULL
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
                            area_code VARCHAR(50) NOT NULL,
                            area_price INT NOT NULL,
                            warehouse_id INT NOT NULL,
                            storage_id INT
);

alter table area_table modify column area_code varchar(50) not null;

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


-- revenue_table에 목업 데이터 삽입 (각 area_id에 맞춰서 제품의 크기 합이 초과하지 않도록 조정)
INSERT INTO revenue_table (revenue_amount, product_id, area_id)
VALUES
    (10, 1, 1),  -- Warehouse A, Area A: Product 1 (size 5) * 10 = 50 (총합 50, area_space 200)
    (10, 2, 1),  -- Warehouse A, Area B: Product 2 (size 3) * 10 = 30 (총합 80, area_space 200)
    (10, 3, 1),  -- Warehouse A, Area C: Product 3 (size 4) * 10 = 40 (총합 120, area_space 200)

    (10, 4, 2),  -- Warehouse B, Area A: Product 4 (size 7) * 10 = 70 (총합 70, area_space 300)
    (10, 5, 2),  -- Warehouse B, Area B: Product 5 (size 8) * 10 = 80 (총합 150, area_space 300)
    (10, 6, 2),  -- Warehouse B, Area C: Product 6 (size 6) * 10 = 60 (총합 210, area_space 300)

    (10, 7, 3),  -- Warehouse C, Area A: Product 7 (size 4) * 10 = 40 (총합 40, area_space 250)
    (10, 8, 3),  -- Warehouse C, Area B: Product 8 (size 3) * 10 = 30 (총합 70, area_space 250)
    (10, 9, 3),  -- Warehouse C, Area C: Product 9 (size 2) * 10 = 20 (총합 90, area_space 250)

    (10, 1, 4),  -- Warehouse D, Area A: Product 1 (size 5) * 10 = 50 (총합 50, area_space 400)
    (10, 2, 4),  -- Warehouse D, Area B: Product 2 (size 3) * 10 = 30 (총합 80, area_space 400)
    (10, 3, 4),  -- Warehouse D, Area C: Product 3 (size 4) * 10 = 40 (총합 120, area_space 400)

    (10, 4, 5),  -- Warehouse E, Area A: Product 4 (size 7) * 10 = 70 (총합 70, area_space 300)
    (10, 5, 5),  -- Warehouse E, Area B: Product 5 (size 8) * 10 = 80 (총합 150, area_space 300)
    (10, 6, 5),  -- Warehouse E, Area C: Product 6 (size 6) * 10 = 60 (총합 210, area_space 300)

    (10, 7, 6),  -- Warehouse F, Area A: Product 7 (size 4) * 10 = 40 (총합 40, area_space 200)
    (10, 8, 6),  -- Warehouse F, Area B: Product 8 (size 3) * 10 = 30 (총합 70, area_space 200)
    (10, 9, 6),  -- Warehouse F, Area C: Product 9 (size 2) * 10 = 20 (총합 90, area_space 200)

    (10, 1, 7),  -- Warehouse G, Area A: Product 1 (size 5) * 10 = 50 (총합 50, area_space 300)
    (10, 2, 7),  -- Warehouse G, Area B: Product 2 (size 3) * 10 = 30 (총합 80, area_space 300)
    (10, 3, 7),  -- Warehouse G, Area C: Product 3 (size 4) * 10 = 40 (총합 120, area_space 300)

    (10, 4, 8),  -- Warehouse H, Area A: Product 4 (size 7) * 10 = 70 (총합 70, area_space 250)
    (10, 5, 8),  -- Warehouse H, Area B: Product 5 (size 8) * 10 = 80 (총합 150, area_space 250)
    (10, 6, 8),  -- Warehouse H, Area C: Product 6 (size 6) * 10 = 60 (총합 210, area_space 250)

    (10, 7, 9),  -- Warehouse I, Area A: Product 7 (size 4) * 10 = 40 (총합 40, area_space 300)
    (10, 8, 9),  -- Warehouse I, Area B: Product 8 (size 3) * 10 = 30 (총합 70, area_space 300)
    (10, 9, 9),  -- Warehouse I, Area C: Product 9 (size 2) * 10 = 20 (총합 90, area_space 300)

    (10, 1, 10), -- Warehouse J, Area A: Product 1 (size 5) * 10 = 50 (총합 50, area_space 200)
    (10, 2, 10), -- Warehouse J, Area B: Product 2 (size 3) * 10 = 30 (총합 80, area_space 200)
    (10, 3, 10); -- Warehouse J, Area C: Product 3 (size 4) * 10 = 40 (총합 120, area_space 200)

