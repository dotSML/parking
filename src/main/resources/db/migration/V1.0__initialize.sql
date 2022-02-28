CREATE TABLE property
(
    id   bigint PRIMARY KEY AUTO_INCREMENT,
    name varchar(255)
);

CREATE TABLE customer_tier
(
    id                bigint PRIMARY KEY AUTO_INCREMENT,
    code              varchar(255) NOT NULL,
    name              varchar(255) NOT NULL,
    base_fee          decimal      NOT NULL,
    parking_day_fee   decimal      NOT NULL,
    parking_night_fee decimal      NOT NULL
);

CREATE TABLE customer
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    email      varchar(255) NOT NULL UNIQUE,
    first_name varchar(255),
    last_name  varchar(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE parking
(
    id          bigint PRIMARY KEY AUTO_INCREMENT,
    start_ts    TIMESTAMP NOT NULL,
    end_ts      TIMESTAMP,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    customer_id integer,
    property_id integer,
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES property (id) ON DELETE CASCADE
);


CREATE TABLE customer_tier_subscription
(
    id               bigint PRIMARY KEY AUTO_INCREMENT,
    customer_id      bigint NOT NULL,
    customer_tier_id bigint NOT NULL,
    start_ts         TIMESTAMP,
    end_ts           TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    FOREIGN KEY (customer_tier_id) REFERENCES customer (id) ON DELETE CASCADE
);


CREATE TABLE invoice
(
    id           bigint PRIMARY KEY AUTO_INCREMENT,
    customer_id  bigint    NOT NULL,
    property_id  bigint    NOT NULL,
    period_start TIMESTAMP NOT NULL,
    period_end   TIMESTAMP NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE,
    FOREIGN KEY (property_id) REFERENCES property (id) ON DELETE CASCADE
);

CREATE TABLE invoice_row
(
    id         bigint PRIMARY KEY AUTO_INCREMENT,
    name       varchar(255) NOT NULL,
    item_price decimal      NOT NULL,
    quantity   decimal      NOT NULL,
    invoice_id bigint       NOT NULL,
    type       varchar(255),
    FOREIGN KEY (invoice_id) REFERENCES invoice (id) ON DELETE CASCADE
)
