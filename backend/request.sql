drop table request;
CREATE TABLE `request` (
                id tinyint primary key not null auto_increment,
               `from` varchar(500) NOT NULL,
                `to` varchar(500) NOT NULL,
                type boolean,
                amount double,
                price double,
                createTime int,
                updateTime int,
                status int
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;