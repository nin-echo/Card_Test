CREATE table cards (
                          id serial not null primary key,
                          title char(128) not null,
                          description char(256) not null,
                          create_at timestamptz not null default now()
);