CREATE TABLE IF NOT EXISTS payment_status (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(25) NOT NULL
);

CREATE TABLE IF NOT EXISTS ticket_payment_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    external_order_id VARCHAR(50) NOT NULL ,
    route_number VARCHAR(50) NOT NULL,
    client_id VARCHAR(50) NOT NULL ,
    order_sent_time DATETIME NOT NULL ,
    payment_id varchar(50) NOT NULL,
    status_id BIGINT NOT NULL,
    created TIMESTAMP NOT NULL default now(),
    updated TIMESTAMP NOT NULL default now(),
    FOREIGN KEY  (status_id) REFERENCES payment_status (id),
    UNIQUE KEY unique_payment_id (payment_id),
    UNIQUE KEY unique_external_order_id (external_order_id)
);
