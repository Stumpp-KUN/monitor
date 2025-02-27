
CREATE TABLE sensor_type (
    name VARCHAR(20) PRIMARY KEY
);

CREATE TABLE unit (
    name VARCHAR(10) PRIMARY KEY
);

CREATE TABLE sensor (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(30) NOT NULL CHECK (LENGTH(name) >= 3),
                        model VARCHAR(15) NOT NULL,
                        range_from INT NOT NULL CHECK (range_from > 0),
                        range_to INT NOT NULL CHECK (range_to > 0),
                        type_name VARCHAR(20) NOT NULL REFERENCES sensor_type(name),
                        unit_name VARCHAR(10) REFERENCES unit(name),
                        location VARCHAR(40),
                        description VARCHAR(200),
                        created_time TIMESTAMP
);

INSERT INTO sensor_type (name) VALUES
                                   ('Pressure'),
                                   ('Temperature'),
                                   ('Humidity'),
                                   ('Voltage');

INSERT INTO unit (name) VALUES
                            ('bar'),
                            ('°C'),
                            ('%'),
                            ('voltage');