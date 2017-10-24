CREATE TABLE User(   #创建用户表
    id varchar(15) primary key,
    password integer(6),
    username varchar(10),
    age varchar(1)
);
#插入四条数据
INSERT User VALUES('lecolifei',123456,'leco','20');
INSERT User VALUES('admin',11112222,'admin','20');
INSERT User VALUES('lecolimeng',123123,'limeng','20');
INSERT User VALUES('root',888888,'root','22');
INSERT User VALUES('test',666666,'test','99');
#查询
SELECT * FROM User;
