
USE wms_db;

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

-- 유저 테이블
CREATE TABLE user_table (
                            user_id INT AUTO_INCREMENT PRIMARY KEY, -- 사용자 ID (고유값)
                            user_login_id VARCHAR(50) NOT NULL UNIQUE, -- 로그인 ID (중복 불가)
                            user_password VARCHAR(255) NOT NULL, -- 비밀번호 (암호화 필요)
                            user_address VARCHAR(255), -- 주소
                            user_email VARCHAR(100) UNIQUE, -- 이메일 (중복 불가)
                            user_phone VARCHAR(20), -- 휴대폰 번호
                            user_birth_date DATE, -- 생년월일
                            user_created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- 계정 생성일
                            user_type ENUM('admin', 'client') NOT NULL -- 사용자 유형 (관리자, 고객)
);

-- 로그인 기록 테이블
CREATE TABLE login_h_table (
                               login_h_id INT AUTO_INCREMENT PRIMARY KEY, -- 로그인 기록 ID
                               login_h_time DATETIME DEFAULT CURRENT_TIMESTAMP, -- 로그인 시간 (자동 저장)
                               login_h_logout_time DATETIME, -- 로그아웃 시간
                               user_id INT NOT NULL -- 로그인한 사용자 ID (참조)
);

-- 관리자 테이블
CREATE TABLE admin_table (
                             admin_id INT AUTO_INCREMENT PRIMARY KEY, -- 관리자 ID
                             admin_hire_date DATE NOT NULL, -- 입사일
                             admin_position VARCHAR(20), -- 직책
                             user_id INT NOT NULL UNIQUE -- 유저 ID (관리자로 등록된 사용자)
);

-- 사업체 테이블
CREATE TABLE business_table (
                                business_id INT AUTO_INCREMENT PRIMARY KEY, -- 사업체 ID
                                business_regist_num VARCHAR(20) NOT NULL UNIQUE, -- 사업자등록번호
                                business_name VARCHAR(255) NOT NULL, -- 사업체명
                                business_address VARCHAR(255) NOT NULL, -- 사업체 주소
                                user_id INT NOT NULL -- 담당 사용자 ID (참조)
);

-- 카테고리 대분류 테이블
CREATE TABLE category_main (
                               category_id INT AUTO_INCREMENT PRIMARY KEY, -- 대분류 ID
                               category_name VARCHAR(50) NOT NULL UNIQUE -- 대분류명
);

-- 카테고리 중분류 테이블
CREATE TABLE c_mid_level (
                             category_mid_id INT AUTO_INCREMENT PRIMARY KEY, -- 중분류 ID
                             category_name VARCHAR(50) NOT NULL, -- 중분류명
                             category_main_id INT NOT NULL -- 대분류 ID (참조)
);

-- 제품 테이블
CREATE TABLE product (
                         product_id int PRIMARY KEY auto_increment, -- 제품 코드 (PK, 수동 입력)
                         product_size INT NOT NULL, -- 제품 크기
                         product_name VARCHAR(50) NOT NULL, -- 제품명
                         category_mid_id INT NOT NULL, -- 중분류 ID (참조)
                         storage_temperature INT, -- 보관 온도
                         expiration_date DATE, -- 유통기한
                         business_id INT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- 제품 등록 날짜 (자동 기록)
);

-- 입고 테이블
CREATE TABLE inbound_table (
                               inbound_id INT AUTO_INCREMENT PRIMARY KEY, -- 입고 ID
                               inbound_date TIMESTAMP, -- 입고 날짜
                               inbound_request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 입고 요청 날짜
                               inbound_status enum('승인','취소','대기') default '대기', -- 입고 상태
                               admin_id INT, -- 담당 관리자 ID (참조)
                               inbound_amount INT NOT NULL, -- 입고 수량
                               product_id INT NOT NULL -- 제품 ID (참조)
);
-- 입고 날짜 null 가능, 입고 요청 날짜 default current_timestamp, 입고상태 default '대기', admin_id null 가능 으로 변경

-- 출고 테이블
CREATE TABLE outbound_table (
                                outbound_id INT AUTO_INCREMENT PRIMARY KEY, -- 출고 ID
                                outbound_date TIMESTAMP, -- 출고 날짜
                                outbound_request_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 출고 요청 날짜
                                outbound_status ENUM('승인', '대기','취소') default '대기', -- 출고 상태
                                admin_id INT, -- 담당 관리자 ID (참조)
                                outbound_amount INT NOT NULL, -- 출고 수량
                                product_id INT NOT NULL -- 제품 ID (참조)
);
-- outbound table도 동일하게 변경

-- 창고 테이블
CREATE TABLE warehouse_table (
                                 warehouse_id INT AUTO_INCREMENT PRIMARY KEY, -- 창고 ID
                                 warehouse_name VARCHAR(20) NOT NULL, -- 창고명
                                 warehouse_space INT NOT NULL, -- 창고 크기 (m²)
                                 warehouse_address VARCHAR(200) NOT NULL, -- 창고 주소
                                 warehouse_amount INT NOT NULL DEFAULT 0 -- 한달 지출 금액
);

-- 구역 테이블
CREATE TABLE area_table (
                            area_id INT AUTO_INCREMENT PRIMARY KEY, -- 구역 ID
                            area_space INT NOT NULL, -- 구역 크기 (m²)
                            area_code CHAR(1) NOT NULL, -- 구역 코드 (A, B, C, D)
                            area_price INT NOT NULL, -- 단위 크기당 가격
                            warehouse_id INT NOT NULL, -- 창고 ID (참조)
                            storage_id INT -- 보관 상태 ID (참조)
);

-- 보관 상태 테이블 (ENUM 사용)
CREATE TABLE storage_condition (
                                   storage_id INT AUTO_INCREMENT PRIMARY KEY, -- 보관 상태 ID
                                   storage_name ENUM(
                                       'Ultra-Low_Temp',      -- 초저온 보관 (-60 ~ -18℃)
                                       'Frozen_Storage',      -- 냉동 보관 (-18 ~ -10℃)
                                       'Refrigerated_Storage',-- 냉장 보관 (0 ~ 4℃)
                                       'Cool_Storage',        -- 저온 보관 (5 ~ 9℃)
                                       'Room_Temperature',    -- 상온 보관 (10 ~ 25℃)
                                       'Heated_Storage'       -- 냉온장 보관 (40 ~ 65℃)
                                       ) NOT NULL, -- 보관 구분 (ENUM으로 설정)
                                   min_temp INT NOT NULL, -- 최소 온도
                                   max_temp INT NOT NULL, -- 최대 온도
                                   description TEXT -- 보관 특성 설명
);

-- 재고 테이블
CREATE TABLE revenue_table (
                               revenue_id INT AUTO_INCREMENT PRIMARY KEY, -- 재고 ID
                               revenue_amount INT NOT NULL, -- 재고 수량
                               product_id INT NOT NULL, -- 제품 ID (참조)
                               area_id INT NOT NULL -- 구역 ID (참조)
);

-- 재고 히스토리 테블
CREATE TABLE revenue_history_table (
                                       revenue_id INT NOT NULL, -- 재고 ID (PK + FK, 식별 관계)
                                       change_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- 변경 날짜 (PK)
                                       revenue_quantity INT NOT NULL, -- 변경된 재고 수량
                                       change_type ENUM('입고', '출고', '폐기', '조정') NOT NULL, -- 변경 유형
                                       PRIMARY KEY (revenue_id, change_date) -- 식별 관계이므로 복합키 사용
);