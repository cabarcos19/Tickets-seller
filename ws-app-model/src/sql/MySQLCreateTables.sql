-- ----------------------------------------------------------------------------
-- Model
-------------------------------------------------------------------------------
SET SQL_MODE='ALLOW_INVALID_DATES';
DROP TABLE Booking;
DROP TABLE Shows;


CREATE TABLE Shows ( id_show BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(1024) NOT NULL,
    duration INTEGER NOT NULL,
    date_time_lim  TIMESTAMP NOT NULL,
    max_tickets INTEGER NOT NULL,
    real_price  REAL NOT NULL,
    discounted_price REAL NOT NULL,
    commission_sale DOUBLE NOT NULL,
    remaining_tickets INTEGER NOT NULL,
    date_time_show TIMESTAMP NOT NULL,
    CONSTRAINT ShowPK PRIMARY KEY(id_show), 
    CONSTRAINT validDuration CHECK ( duration >= 0 AND duration <= 1000),
    CONSTRAINT validReal_Price CHECK ( real_price >= 0 AND real_price <= 1000) ) ENGINE = InnoDB;

CREATE TABLE Booking ( id_booking BIGINT NOT NULL AUTO_INCREMENT,
    id_show BIGINT NOT NULL,
    email VARCHAR(40) NOT NULL,
    date_time_book TIMESTAMP NOT NULL,
    credit_card VARCHAR(16),
    discounted_price REAL NOT NULL,
    used BIT NOT NULL,
    num_tickets INTEGER NOT NULL,
    CONSTRAINT BookingPK PRIMARY KEY(id_booking),
    CONSTRAINT BookingShowIdFK FOREIGN KEY(id_show)
	REFERENCES Shows(id_show) ON DELETE CASCADE ) ENGINE = InnoDB;
