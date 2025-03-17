
use wms_db;
-- 로그인 기록 외래 키 추가
ALTER TABLE login_h_table
    ADD CONSTRAINT fk_login_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;

-- 관리자 외래 키 추가
ALTER TABLE admin_table
    ADD CONSTRAINT fk_admin_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;

-- 사업체 외래 키 추가
ALTER TABLE business_table
    ADD CONSTRAINT fk_business_user FOREIGN KEY (user_id) REFERENCES user_table(user_id) ON DELETE CASCADE;

-- 카테고리 중분류 외래 키 추가
ALTER TABLE c_mid_level
    ADD CONSTRAINT fk_category_mid_main FOREIGN KEY (category_main_id) REFERENCES category_main(category_id) ON DELETE CASCADE;

-- 제품 테이블 외래 키 추가
ALTER TABLE product
    ADD CONSTRAINT fk_product_category FOREIGN KEY (category_mid_id) REFERENCES c_mid_level(category_mid_id) ON DELETE CASCADE;

-- 입고 테이블 외래 키 추가
ALTER TABLE inbound_table
    ADD CONSTRAINT fk_inbound_admin FOREIGN KEY (admin_id) REFERENCES admin_table(admin_id),
    ADD CONSTRAINT fk_inbound_product FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_inbound_business FOREIGN KEY (business_id) REFERENCES business_table(business_id) ON DELETE CASCADE;

-- 출고 테이블 외래 키 추가
ALTER TABLE outbound_table
    ADD CONSTRAINT fk_outbound_admin FOREIGN KEY (admin_id) REFERENCES admin_table(admin_id),
    ADD CONSTRAINT fk_outbound_product FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_outbound_business FOREIGN KEY (business_id) REFERENCES business_table(business_id) ON DELETE CASCADE;

-- 창고 내 구역 테이블 외래 키 추가
ALTER TABLE area_table
    ADD CONSTRAINT fk_area_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse_table(warehouse_id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_area_storage FOREIGN KEY (storage_id) REFERENCES storage_condition(storage_id) ON DELETE SET NULL;

-- 재고 테이블 외래 키 추가
ALTER TABLE revenue_table
    ADD CONSTRAINT fk_revenue_product FOREIGN KEY (product_id) REFERENCES product(product_id) ON DELETE CASCADE,
    ADD CONSTRAINT fk_revenue_area FOREIGN KEY (area_id) REFERENCES area_table(area_id) ON DELETE CASCADE;

-- 재고 히스토리 테이블 외래 키 추가
ALTER TABLE revenue_history_table
    ADD CONSTRAINT fk_revenue_history_inventory
        FOREIGN KEY (revenue_id) REFERENCES revenue_table(revenue_id) ON DELETE CASCADE;
