DROP TABLE IF EXISTS cards;
DROP TABLE IF EXISTS accounts;

CREATE table accounts (
                          id serial not null primary key,
                          email char(128) not null unique,
                          password char(256) not null,
                          create_at timestamptz not null default now()
);

CREATE table cards (
                       id serial not null primary key,
                       account_id int,
                       title char(128) not null unique,
                       description char(256) not null,
                       create_at timestamptz not null default now(),
                       constraint fk_account foreign key(account_id) REFERENCES accounts(id)
);

