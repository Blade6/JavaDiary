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

--
-- 好友表
-- 注意：user1,user2和user2,user1是相同的记录，不要重复存进数据库
--
CREATE TABLE friends
(id int NOT NULL AUTO_INCREMENT,
userid char(6) NOT NULL COMMENT '用户帐号',
freid char(6) NOT NULL COMMENT '好友帐号',
PRIMARY KEY (id),
FOREIGN KEY (userid) REFERENCES user(userid),
FOREIGN KEY (freid) REFERENCES user(userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 聊天表
-- 例子:A发送信息给B，当捕获到点击发送按钮，内容合法则插入数据库
-- 对于插入的这条数据，在A和B的手机上应有两种显示，
-- 需要判断当前用户是否发送者来决定该信息显示在左栏还是右栏
--
CREATE TABLE chat
(id int NOT NULL AUTO_INCREMENT,
fromid char(6) NOT NULL COMMENT '发送者',
toid char(6) NOT NULL COMMENT '接收者',
content text NOT NULL COMMENT '内容',
time timestamp DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id),
FOREIGN KEY (fromid) REFERENCES user(userid),
FOREIGN KEY (toid) REFERENCES user(userid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 动态表
-- 文字和图片不能同时为NULL
--
CREATE TABLE moment
(id int NOT NULL AUTO_INCREMENT,
userid char(6) NOT NULL COMMENT '动态发送者',
words text COMMENT '动态包含的文字',
image varchar(100) COMMENT '动态包含的图片的路径',
upvote int DEFAULT 0 COMMENT '点赞数',
time timestamp DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id),
FOREIGN KEY (userid) REFERENCES user(userid)
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 评论表
--
CREATE TABLE comment
(id int NOT NULL AUTO_INCREMENT,
commenter char(6) NOT NULL COMMENT '评论者',
momentid int NOT NULL COMMENT '被评论的动态',
content text NOT NULL COMMENT '评论内容',
time timestamp DEFAULT CURRENT_TIMESTAMP,
PRIMARY KEY (id),
FOREIGN KEY (commenter) REFERENCES user(userid),
FOREIGN KEY (momentid) REFERENCES moment(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

