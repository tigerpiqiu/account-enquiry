DROP TABLE IF EXISTS account;

CREATE TABLE account (
  account_number DECIMAL(10)  NOT NULL PRIMARY KEY,
  account_name VARCHAR(100) NOT NULL,
  account_type VARCHAR(3) DEFAULT NULL,
  balance_date DATE NOT NULL,
  currency VARCHAR(3) NOT NULL,
  opening_available_balance DECIMAL(15,2) NOT NULL
);

