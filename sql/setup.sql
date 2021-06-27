CREATE USER framework LOGIN SUPERUSER PASSWORD 'framework';
CREATE USER mlflow PASSWORD 'mlflow';

CREATE DATABASE framework OWNER  framework;
CREATE DATABASE mlflow OWNER mlflow;