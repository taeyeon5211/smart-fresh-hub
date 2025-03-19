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
Drop TABLE IF EXISTS user_backup_table;

SET FOREIGN_KEY_CHECKS = 1;


USE wms_db;
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
                                outbound_date TIMESTAMP,
                                outbound_request_date TIMESTAMP default current_timestamp,
                                outbound_status ENUM('승인', '대기', '취소') default '대기',
                                admin_id INT,
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
