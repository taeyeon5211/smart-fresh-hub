--  WMS 데이터베이스 생성
CREATE DATABASE wms_db;

-- 사용자 'wms_user' 생성 (localhost에서만 접근 가능)
CREATE USER 'wms_user'@'localhost' IDENTIFIED BY '1234';

-- 'wms_user'에게 'wms_db'에 대한 모든 권한 부여
GRANT ALL PRIVILEGES ON wms_db.* TO 'wms_user'@'localhost';

-- 4️⃣ 변경 사항 즉시 적용 (MySQL의 권한 테이블 갱신)
FLUSH PRIVILEGES;