ALTER TABLE accounts
RENAME COLUMN name TO email;

ALTER TABLE accounts
    RENAME COLUMN description TO password;