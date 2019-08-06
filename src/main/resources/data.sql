DROP TABLE IF EXISTS account;

DROP TABLE IF EXISTS account_transaction;

CREATE TABLE account (
  account_number DECIMAL(10)  NOT NULL PRIMARY KEY,
  account_name VARCHAR(100) NOT NULL,
  user_id DECIMAL(10)  NOT NULL,
  account_type VARCHAR(3) NOT NULL,
  balance_date DATE NOT NULL,
  currency VARCHAR(3) NOT NULL,
  opening_available_balance DECIMAL(15,2) NOT NULL
);

CREATE TABLE account_transaction (
  transaction_id INT AUTO_INCREMENT  PRIMARY KEY,
  account_number DECIMAL(10)  NOT NULL,
  value_date DATE NOT NULL,
  transaction_type VARCHAR(1) NOT NULL,
  transaction_amount DECIMAL(15,2) NOT NULL,
  transaction_narrative VARCHAR(200) NOT NULL,
);

INSERT INTO account (account_number, account_name, user_id, account_type, balance_date, currency, opening_available_balance) VALUES (585309209, 'SGSavings726', 100001, 'SAV', '2019-02-09', 'AUD', 9898773.89);

INSERT INTO account (account_number, account_name, user_id, account_type, balance_date, currency, opening_available_balance) VALUES (585309210, 'SGCURRENT424', 100001, 'CUR', '2017-02-19', 'USD', 1980003333.23);



INSERT INTO account_transaction (account_number, value_date, transaction_type, transaction_amount, transaction_narrative) VALUES (585309209, '2019-06-09', 'C', 773.89, 'This is a test 1');
INSERT INTO account_transaction (account_number, value_date, transaction_type, transaction_amount, transaction_narrative) VALUES (585309209, '2019-06-10', 'D', 43.34, 'This is a test 2');

INSERT INTO account_transaction (account_number, value_date, transaction_type, transaction_amount, transaction_narrative) VALUES (585309210, '2019-07-09', 'C', 10003.89, 'This is a test 1');
INSERT INTO account_transaction (account_number, value_date, transaction_type, transaction_amount, transaction_narrative) VALUES (585309210, '2019-07-10', 'D', 300.34, 'This is a test 2');

