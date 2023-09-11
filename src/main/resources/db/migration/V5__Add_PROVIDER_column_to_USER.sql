ALTER TABLE user ADD  provider ENUM( 'local', 'facebook', 'google', 'github');
ALTER TABLE user ADD  name VARCHAR(255);
