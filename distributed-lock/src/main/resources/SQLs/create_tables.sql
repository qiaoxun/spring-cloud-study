create table resource_lock(
    id int(11) unsigned not null auto_increment,
    resource_name varchar(128) not null default '' comment '资源名称',
	node_info varchar(128) default null comment '机器信息',
	count int(11) not null default 0 comment '锁的次数，统计可重入锁',
	description varchar(128) default null comment '额外描述信息',
	update_time timestamp null default null,
	create_time timestamp null default null,
	primary key(id),
	unique key unq_resource(resource_name)
) engine=InnoDB default CHARSET=utf8mb4;