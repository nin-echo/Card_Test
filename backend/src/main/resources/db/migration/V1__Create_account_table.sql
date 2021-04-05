CREATE table accounts (
                          id serial not null primary key,
                          name char(128) not null,
                          description char(256) not null,
                          create_at timestamptz not null default now()
);