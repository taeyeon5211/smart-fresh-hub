
USE wms_db;

-- 1. 유저 테이블
CREATE TABLE user_table (
                            user_id INT AUTO_INCREMENT PRIMARY KEY,
                            user_login_id VARCHAR(50) NOT NULL UNIQUE,
                            user_name VARCHAR(30) NOT NULL,
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


-- 로그인 기록 FK
ALTER TABLE login_h_table
    ADD CONSTRAINT fk_login_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;

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

