--
-- 用户表
-- 
CREATE TABLE user
(id int NOT NULL AUTO_INCREMENT,
userid char(6) NOT NULL COMMENT '用户帐号',
username varchar(20) NOT NULL COMMENT '用户名',
password char(32) NOT NULL COMMENT '密码',
pic varchar(100) DEFAULT '' COMMENT '头像路径',
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `user` ADD UNIQUE(`userid`);
ALTER TABLE `user` ADD UNIQUE(`username`);