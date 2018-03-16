--
-- Initial database/table creation for LifeTracker
--

CREATE TABLE users (
  id                       BIGSERIAL PRIMARY KEY,
  email                    VARCHAR(512) UNIQUE NOT NULL,
  first_name               VARCHAR(512),
  last_name                VARCHAR(512),
  password_hash            VARCHAR(200),
  email_confirmed          BOOLEAN DEFAULT FALSE,
  email_confirmation_token VARCHAR(300),
  password_reset_token     VARCHAR(300)
);

CREATE TABLE metric_families (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR(300) UNIQUE NOT NULL
);

CREATE TABLE metric_themes (
  id   BIGSERIAL PRIMARY KEY,
  name VARCHAR(300) UNIQUE NOT NULL
);

-- Many-to-many joining table for themes and families
CREATE TABLE metric_themes_families(
  id BIGSERIAL PRIMARY KEY,
  family_id BIGINT REFERENCES metric_families(id),
  theme_id BIGINT REFERENCES metric_themes(id)
);

CREATE TABLE metrics (
  id        BIGSERIAL PRIMARY KEY,
  name      VARCHAR(300) UNIQUE NOT NULL,
  units     VARCHAR(100),
  family_id BIGINT REFERENCES metric_families(id)
);

CREATE TABLE sample_groups (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(300) NOT NULL,
  date TIMESTAMP WITH TIME ZONE
);

CREATE TABLE samples (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT REFERENCES users(id) NOT NULL,
  metric_id BIGINT REFERENCES metrics(id) NOT NULL,
  amount DOUBLE PRECISION NOT NULL,
  group_id BIGINT REFERENCES sample_groups(id)
);
