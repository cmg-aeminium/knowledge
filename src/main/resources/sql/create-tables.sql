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


CREATE TABLE Topics(
	id SMALLINT NOT NULL PRIMARY KEY,
	knowledgeareaid SMALLINT NOT NULL REFERENCES KnowledgeAreas(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE TopicTranslations (
    topicId SMALLINT NOT NULL REFERENCES Topics(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL,
	PRIMARY KEY(topicId, language)
);

CREATE TYPE university_type AS ENUM(
	'U_PORTO',
	'U_MINHO',
	'U_COIMBRA',
	'IST',
	'U_AVEIRO'
);

CREATE TABLE Degrees (
	id SMALLINT NOT NULL PRIMARY KEY,
	acronym text NOT NULL,
	image text NOT NULL,
	university university_type NOT NULL,
	year smallint NOT NULL
);

CREATE TABLE DegreeTranslations (
	degreeId SMALLINT NOT NULL REFERENCES Degrees(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL,
	PRIMARY KEY(degreeId, language)
);