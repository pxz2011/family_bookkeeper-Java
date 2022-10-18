-- auto-generated definition
create table accountbook
(
    id              int auto_increment
        primary key,
    remitter        varchar(200) not null,
    payee           varchar(200) not null,
    Remittance_Time varchar(200) not null,
    money           varchar(200) not null,
    remarks         varchar(200) not null
);

