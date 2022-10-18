create table hab.user
(
    user_id     int auto_increment comment '用户id'
        primary key,
    user_status varchar(255) null comment '用户身份',
    password    varchar(255) null comment '密码',
    salt        varchar(255) null comment '盐值',
    user_name   varchar(255) null comment '用户姓名',
    email       varchar(255) null comment '邮箱'
)
    charset = utf8;

