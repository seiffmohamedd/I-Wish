CREATE USER iwish IDENTIFIED BY iwish;

GRANT CREATE MATERIALIZED VIEW,
      CREATE PROCEDURE,
      CREATE SEQUENCE,
      CREATE SESSION,
      CREATE SYNONYM,
      CREATE TABLE,
      CREATE TRIGGER,
      CREATE TYPE,
      CREATE VIEW
  TO iwish;

-- connect as sysdba and grant to the iwish user the dba priv
grant dba to iwish;

ALTER USER iwish QUOTA UNLIMITED ON USERS;
