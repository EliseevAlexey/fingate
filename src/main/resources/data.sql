-- Administrator
INSERT INTO users (email, password)
VALUES ('admin', 'admin');

-- Default bank account for service fees withdraws
INSERT INTO accounts (default) VALUES ( true );

-- Default account fees for testing
INSERT INTO account_fees (fee_frequency, system, value)
VALUES
('MONTHLY', 'MASTER_CARD', 100),
('YEARLY', 'MASTER_CARD', 1000),
('MONTHLY', 'VISA', 200),
('YEARLY', 'VISA', 2000);
