-- Administrator
INSERT INTO users (email, password)
VALUES ('admin', 'admin');

-- Default bank account for service fees withdraws
INSERT INTO accounts (default) VALUES ( true );
