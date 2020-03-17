-- Administrator
INSERT INTO users (email, password)
VALUES ('admin', 'admin');

-- Test account fees
INSERT INTO bank_account_fees (fee_frequency, card_system, value)
VALUES ('MONTHLY', 'MASTER_CARD', 100),
       ('YEARLY', 'MASTER_CARD', 1000),
       ('MONTHLY', 'VISA', 200),
       ('YEARLY', 'VISA', 2000);

-- Default bank account for service fees withdraws
INSERT INTO bank_accounts (is_default_account, name)
VALUES (true, 'System_Account');

-- Test bank account
INSERT INTO bank_accounts (card_issuer_id, card_number, card_expiration_date_time, card_cvv_number, card_type, currency,
                           balance, card_system, is_default_account, registration_date, fee_frequency,
                           bank_account_fee_id, user_id)
VALUES (1, 111122223334444, '2020-01-12', 555, 'DEBIT', 'USD', 100, 'MASTER_CARD', false, '2020-01-13',
        'MONTHLY', 1, 1),
       (1, 5555666677778888, '2020-01-12', 555, 'CREDIT', 'USD', 100, 'MASTER_CARD', false, '2020-01-13',
        'YEARLY', 2, 1);

-- Test payment categories
INSERT INTO payment_categories (name)
VALUES ('FeeWithdraw'),
       ('GiftFund'),
       ('Shop');

-- Test gifts
INSERT INTO gifts (gift_type, value)
VALUES ('BIRTHDAY', 100);

-- Test operations
INSERT INTO operations (withdraw_service_name, payment_category_id, payment_amount, payment_date_time, bank_account_id,
                        operation_type, operation_status, user_id)
VALUES ('shop', 1, 50, '2020-04-12T14:45:33.701', 2, 'WITHDRAW', 'PROCESSED', 1),
       ('shop', 1, 150, '2020-04-12T13:45:33.701', 3, 'WITHDRAW', 'REJECTED', 1),
       ('shop', 1, 150, '2020-04-12T12:45:33.701', 2, 'WITHDRAW', 'PROCESSED', 1);

-- Test notifications
INSERT INTO notifications (name)
VALUES ('WITHDRAW'),
       ('FUND'),
       ('NEGATIVE_BALANCE');

INSERT INTO roles(name)
VALUES ('USER');
