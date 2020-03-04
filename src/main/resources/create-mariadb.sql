-- Database Table

DROP TABLE IF EXISTS dbs;
CREATE TABLE dbs (
	database_id BIGINT NOT NULL AUTO_INCREMENT,
	driver_name VARCHAR(64),
	name VARCHAR(16) NOT NULL,
	password VARCHAR(16),
	url VARCHAR(64) NOT NULL,
	user_id VARCHAR(16),
	username VARCHAR(16),
	CONSTRAINT pk_dbs PRIMARY KEY (database_id)
);

DROP INDEX IF EXISTS uk_on_database_id ON dbs;
CREATE UNIQUE INDEX uk_on_database_id ON dbs (database_id);

DROP INDEX IF EXISTS uk_on_database_name ON dbs;
CREATE UNIQUE INDEX uk_on_database_name ON dbs (name);

-- Collection Table

DROP TABLE IF EXISTS colls;
CREATE TABLE colls (
	id BIGINT NOT NULL AUTO_INCREMENT,
	database_id BIGINT,
	alias VARCHAR(32) NOT NULL,
	kind VARCHAR(16),
	name VARCHAR(32) NOT NULL,
	CONSTRAINT pk_colss PRIMARY KEY (id)
);

DROP INDEX IF EXISTS uk_on_coll_id ON colls;
CREATE UNIQUE INDEX uk_on_coll_id ON colls (id);

DROP INDEX IF EXISTS uk_on_database_id_and_alias ON colls;
CREATE UNIQUE INDEX uk_on_database_id_and_alias ON colls (database_id,alias);


