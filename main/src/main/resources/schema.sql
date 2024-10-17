CREATE TABLE IF NOT EXISTS users (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  email VARCHAR NOT NULL,
  name VARCHAR NOT NULL,
  CONSTRAINT pk_users PRIMARY KEY (id),
  CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  name VARCHAR NOT NULL,
  CONSTRAINT pk_categories PRIMARY KEY (id),
  CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS locations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  lat FLOAT NOT NULL,
  lon FLOAT NOT NULL,
  CONSTRAINT pk_locations PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  annotation VARCHAR NOT NULL,
  category_id BIGINT REFERENCES categories(id) NOT NULL,
  confirmed_requests BIGINT,
  created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  description VARCHAR NOT NULL,
  event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  initiator_id BIGINT REFERENCES users(id) NOT NULL,
  location_id BIGINT REFERENCES locations(id) NOT NULL,
  paid BOOLEAN,
  participant_limit BIGINT NOT NULL,
  published_on TIMESTAMP WITHOUT TIME ZONE,
  request_moderation BOOLEAN,
  title VARCHAR NOT NULL,
  state VARCHAR NOT NULL,
  CONSTRAINT pk_events PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS requests (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  event_id BIGINT REFERENCES events(id) NOT NULL,
  requester_id BIGINT REFERENCES users(id) NOT NULL,
  status VARCHAR NOT NULL,
  CONSTRAINT pk_requests PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilations (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  pinned BOOLEAN,
  title VARCHAR NOT NULL,
  CONSTRAINT pk_compilations PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS compilation_event (
  compilation_id INTEGER NOT NULL REFERENCES compilations(id),
  event_id INTEGER NOT NULL REFERENCES events(id),
  PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE IF NOT EXISTS comments (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  poster_id BIGINT REFERENCES users(id) NOT NULL,
  text VARCHAR NOT NULL,
  posted TIMESTAMP WITHOUT TIME ZONE NOT NULL,
  CONSTRAINT pk_comments PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS event_comment (
  event_id INTEGER NOT NULL REFERENCES events(id),
  comment_id INTEGER NOT NULL REFERENCES comments(id),
  PRIMARY KEY (event_id, comment_id)
);
