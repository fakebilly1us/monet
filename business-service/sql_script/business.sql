create table business
(
    id            bigint      not null primary key comment '主键',
    business_name varchar(20) not null default '' comment '业务名称',
    creator_id    bigint      not null default 0 comment '创建人ID',
    creator_name  varchar(20) not null default '' comment '创建人姓名',
    created_time  datetime    not null default CURRENT_TIMESTAMP comment '创建时间',
    modifier_id   bigint      not null default 0 comment '修改人ID',
    modifier_name varchar(20) not null default '' comment '修改人姓名',
    modified_time datetime    not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '修改人时间'
) comment '业务表' default charset = utf8mb4;