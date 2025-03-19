
-- 샘플 user_table 데이터

INSERT INTO user_table (user_login_id, user_name, user_password, user_address, user_email, user_phone, user_birth_date, user_type)
VALUES
    ('admin001', 'John Smith', 'hashed_password_123', '123 Admin St, New York, NY', 'john.smith@admin.com', '123-456-7890', '1985-03-15', 'admin'),
    ('admin002', 'Sarah Johnson', 'hashed_password_456', '456 Manager Rd, Los Angeles, CA', 'sarah.johnson@admin.com', '987-654-3210', '1988-07-21', 'admin'),

    ('client001', 'Emily Davis', 'hashed_password_789', '789 Client Ave, Chicago, IL', 'emily.davis@email.com', '555-123-4567', '1992-05-30', 'client'),
    ('client002', 'Michael Brown', 'hashed_password_234', '101 Customer St, Houston, TX', 'michael.brown@email.com', '555-987-6543', '1990-11-12', 'client'),
    ('client003', 'David Wilson', 'hashed_password_567', '222 User Ln, San Francisco, CA', 'david.wilson@email.com', '444-555-6666', '1995-09-25', 'client'),
    ('client004', 'Jessica Taylor', 'hashed_password_890', '333 Consumer Rd, Seattle, WA', 'jessica.taylor@email.com', '777-888-9999', '1987-02-14', 'client'),
    ('client005', 'Daniel Martinez', 'hashed_password_111', '444 Subscriber Dr, Denver, CO', 'daniel.martinez@email.com', '666-777-8888', '1993-08-07', 'client');
