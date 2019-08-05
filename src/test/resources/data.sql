DROP TABLE IF EXISTS account;

DROP TABLE IF EXISTS account_transaction;

CREATE TABLE account (
  account_number DECIMAL(10)  NOT NULL PRIMARY KEY,
  account_name VARCHAR(100) NOT NULL,
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
