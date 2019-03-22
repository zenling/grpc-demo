DROP TABLE IF EXISTS  t_point;

CREATE TABLE t_point (
    id BIGINT(20) NOT NULL auto_increment PRIMARY  KEY ,
    amount int(11) default 0 comment '获得积分',
    user_id int comment '用户id'
);