CREATE SEQUENCE hibernate_sequence START 1;
CREATE TABLE app_user (
  id SERIAL PRIMARY KEY,
  username TEXT,
  password TEXT,
  fhir_id TEXT
);

CREATE TABLE comment (
  id SERIAL PRIMARY KEY ,
  comment TEXT,
  app_user_id BIGINT
);

ALTER TABLE comment ADD CONSTRAINT comment_app_user_fk FOREIGN KEY (app_user_id) references app_user(id);

CREATE TABLE medication (
  id SERIAL PRIMARY KEY,
  name TEXT
);

CREATE TABLE user_medication (
  id SERIAL PRIMARY KEY,
  app_user_id BIGINT,
  medication_id BIGINT
);

ALTER TABLE user_medication ADD CONSTRAINT user_medication_user_fk FOREIGN KEY (app_user_id) references app_user(id);
ALTER TABLE user_medication ADD CONSTRAINT user_medication_medication_fk
FOREIGN KEY (app_user_id) references medication(id);

CREATE TABLE alarm (
  id SERIAL PRIMARY KEY,
  user_medication_id BIGINT,
  time TEXT,
  day_of_week TEXT
);

ALTER TABLE alarm ADD CONSTRAINT  alarm_user_medication_fk
FOREIGN KEY (user_medication_id) REFERENCES user_medication (id);

CREATE TABLE side_effect (
  id SERIAL PRIMARY KEY,
  user_medication_id BIGINT,
  description TEXT
);

ALTER TABLE side_effect ADD CONSTRAINT  side_effect_user_medication_fk
FOREIGN KEY (user_medication_id) REFERENCES user_medication (id);

INSERT INTO app_user (username, password) VALUES ('martha', 'password');
INSERT INTO medication (name) VALUES ('viagra');
INSERT INTO medication (name) VALUES ('prozac');

INSERT INTO user_medication (app_user_id, medication_id) (
    SELECT app_user.id, medication.id FROM app_user, medication
);