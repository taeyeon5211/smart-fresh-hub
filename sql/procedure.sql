use wms_db;
DELIMITER $$

CREATE PROCEDURE GetPendingInboundRequests()
BEGIN
    --  "대기" 상태의 입고 요청을 조회하는 SQL
    SELECT i.inbound_id,
           i.inbound_request_date,
           i.inbound_date,
           i.inbound_status,
           i.inbound_amount,
           p.product_name,
           b.business_name,
           a.admin_id
    FROM inbound_table i
             --  제품 테이블과 조인하여 제품명 가져오기
             JOIN product p ON i.product_id = p.product_id
        -- 사업체 테이블과 조인하여 사업체명 가져오기
             JOIN business_table b ON p.business_id = b.business_id
        -- 관리자 테이블과 조인하여 담당 관리자 ID 가져오기
             JOIN admin_table a ON i.admin_id = a.admin_id
    WHERE i.inbound_status = '대기'  -- "대기" 상태인 입고 요청만 조회
    ORDER BY i.inbound_request_date DESC; -- 요청 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE GetAllInboundHistory()
BEGIN
    --  모든 입고 내역을 조회하는 SQL
    SELECT i.inbound_id,
           i.inbound_request_date,
           i.inbound_date,
           i.inbound_status,
           i.inbound_amount,
           p.product_name,
           b.business_name,
           a.admin_id
    FROM inbound_table i
             -- 제품 테이블과 조인하여 제품명 가져오기
             JOIN product p ON i.product_id = p.product_id
        --  사업체 테이블과 조인하여 사업체명 가져오기
             JOIN business_table b ON p.business_id = b.business_id
        --  관리자 테이블과 조인하여 담당 관리자 ID 가져오기
             JOIN admin_table a ON i.admin_id = a.admin_id
    ORDER BY i.inbound_request_date DESC; --  요청 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE GetInboundHistoryByBusiness(IN p_businessId INT)
BEGIN
    --  특정 사업체의 입고 내역을 조회하는 SQL
    SELECT i.inbound_id,
           i.inbound_request_date,
           i.inbound_date,
           i.inbound_status,
           i.inbound_amount,
           p.product_name,
           b.business_name,
           a.admin_id
    FROM inbound_table i
             --  제품 테이블과 조인하여 제품명 가져오기
             JOIN product p ON i.product_id = p.product_id
        --  사업체 테이블과 조인하여 사업체명 가져오기
             JOIN business_table b ON p.business_id = b.business_id
        --  관리자 테이블과 조인하여 담당 관리자 ID 가져오기
             JOIN admin_table a ON i.admin_id = a.admin_id
    WHERE p.business_id = p_businessId  --  특정 사업체의 입고 내역만 조회
    ORDER BY i.inbound_request_date DESC; --  요청 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;



DELIMITER $$

CREATE PROCEDURE GetAllRevenueHistory()
BEGIN
    -- 모든 재고 변경 이력을 조회하는 SQL
    SELECT r.revenue_id,
           r.change_date,
           r.revenue_quantity,
           r.change_type,
           p.product_name,
           b.business_name
    FROM revenue_history_table r
             JOIN revenue_table rt ON r.revenue_id = rt.revenue_id
             JOIN product p ON rt.product_id = p.product_id
             JOIN business_table b ON p.business_id = b.business_id
    ORDER BY r.change_date DESC; --  변경 날짜 기준으로 내림차순 정렬
END $$

DELIMITER ;

