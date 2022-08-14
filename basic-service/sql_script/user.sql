create table user
(
    id            bigint                                not null comment '主键'
        primary key,
    user_name     varchar(20) default ''                not null comment '姓名',
    creator_id    bigint      default 0                 not null comment '创建人ID',
    creator_name  varchar(20) default ''                not null comment '创建人姓名',
    created_time  datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    modifier_id   bigint      default 0                 not null comment '修改人ID',
    modifier_name varchar(20) default ''                not null comment '修改人姓名',
    modified_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改人时间'
) comment '用户表' default charset = utf8mb4;