CREATE DATABASE sweranker
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
    

CREATE TABLE KnowledgeAreas (
    id bigserial NOT NULL PRIMARY KEY,
	image text NOT NULL
);

CREATE TABLE KnowledgeAreaTranslations (
    knowledgeAreaId bigserial NOT NULL REFERENCES KnowledgeAreas(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language text NOT NULL,
	translatedName text NOT NULL,
	translatedDescription text NOT NULL
);