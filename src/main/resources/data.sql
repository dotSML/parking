INSERT INTO customer_tier(code, name, base_fee, parking_day_fee, parking_night_fee) VALUES ('STD', 'Standard tier', 0, 1.5, 1);
INSERT INTO customer_tier(code, name, base_fee, parking_day_fee, parking_night_fee) VALUES ('PRM', 'Premium tier', 20, 1, 0.75);

INSERT INTO customer(email) VALUES ('madis@keevitaja.ee');
INSERT INTO customer(email) VALUES ('toomas.kuld@gmail.com');

INSERT INTO property(name) VALUES ('Parking house 1');


INSERT INTO parking(start_ts, end_ts, customer_id, property_id) VALUES ('2022-02-20 19:40:00', '2022-02-20 20:35:00', 1, 1);