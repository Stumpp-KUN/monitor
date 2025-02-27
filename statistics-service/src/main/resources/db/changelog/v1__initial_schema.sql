CREATE TABLE statistic (
                           id BIGSERIAL PRIMARY KEY,
                           date DATE NOT NULL,
                           total_sensors INT NOT NULL
);

CREATE TABLE statistic_type_counts (
                                       statistic_id BIGINT NOT NULL,
                                       type_counts_key VARCHAR(255) NOT NULL,
                                       type_counts INT NOT NULL,
                                       PRIMARY KEY (statistic_id, type_counts_key),
                                       FOREIGN KEY (statistic_id) REFERENCES statistic(id) ON DELETE CASCADE
);