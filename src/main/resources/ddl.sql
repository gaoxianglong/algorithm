CREATE TABLE IF NOT EXISTS Person
(
    `PersonId`  INT NOT NULL,
    `FirstName` VARCHAR(32),
    `LastName`  VARCHAR(32),
    primary key (`PersonId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Address
(
    `AddressId` INT NOT NULL,
    `PersonId`  INT NOT NULL,
    `City`      VARCHAR(32),
    `State`     VARCHAR(32),
    PRIMARY KEY (`AddressId`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS World
(
    `name`       VARCHAR(32) NOT NULL,
    `continent`  VARCHAR(32),
    `area`       BIGINT,
    `population` BIGINT,
    `gdp`        BIGINT,
    PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Products
(
    `product_id` INT NOT NULL,
    `low_fats`   ENUM ('Y','N'),
    `recyclable` ENUM ('Y','N'),
    PRIMARY KEY (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS customer
(
    `id`         INT NOT NULL,
    `name`       VARCHAR(32),
    `referee_id` INT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Customers
(
    `Id`   INT NOT NULL,
    `Name` VARCHAR(32),
    PRIMARY KEY (`Id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Orders
(
    `Id`         INT NOT NULL,
    `CustomerId` INT,
    PRIMARY KEY (`Id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Employees
(
    `employee_id` INT NOT NULL,
    `name`        VARCHAR(32),
    `salary`      INT,
    PRIMARY KEY (`employee_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS Salary
(
    `id`     INT NOT NULL,
    `name`   VARCHAR(32),
    `SEX`    ENUM ('m','f'),
    `salary` INT,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS Person;
CREATE TABLE IF NOT EXISTS Person
(
    `id`    INT NOT NULL,
    `email` VARCHAR(32),
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;