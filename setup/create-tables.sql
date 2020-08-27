CREATE DATABASE sweranker
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
    

CREATE TYPE language_type AS ENUM (
	'PT_PT',
    'EN_UK'
);   

CREATE SEQUENCE knowledgearea_id_seq INCREMENT 1;    
CREATE TABLE KnowledgeAreas (
    id SMALLINT NOT NULL PRIMARY KEY DEFAULT nextval('knowledgearea_id_seq'),
	image text NOT NULL
);

CREATE TABLE KnowledgeAreaTranslations (
    knowledgeAreaId SMALLINT NOT NULL REFERENCES KnowledgeAreas(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL
);


CREATE SEQUENCE topic_id_seq INCREMENT 1;    
CREATE TABLE Topics(
	 id SMALLINT NOT NULL PRIMARY KEY DEFAULT nextval('topic_id_seq')
);

CREATE TABLE TopicTranslations (
    topicId SMALLINT NOT NULL REFERENCES Topics(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL
);