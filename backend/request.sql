drop table request;
CREATE TABLE `request` (
                id tinyint not null auto_increment,
               `from` varchar(500) NOT NULL,
                `to` varchar(500) NOT NULL,
                type boolean,
                amount double,
                price double,
                createTime datetime,
                updateTime datetime,
                status int
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;