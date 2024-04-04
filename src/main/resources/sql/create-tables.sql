--CREATE DATABASE sweranker
--    WITH 
--    OWNER = postgres
--    ENCODING = 'UTF8'
--    CONNECTION LIMIT = -1;
    

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
    knowledgeArea SMALLINT NOT NULL REFERENCES KnowledgeAreas(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL
);


CREATE TABLE Topics(
	id SMALLINT NOT NULL PRIMARY KEY,
	knowledgearea SMALLINT NOT NULL REFERENCES KnowledgeAreas(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE TopicTranslations (
    topic SMALLINT NOT NULL REFERENCES Topics(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL,
	PRIMARY KEY(topic, language)
);

CREATE SEQUENCE country_id_seq INCREMENT 1;
CREATE TABLE Countries(
	id SMALLINT NOT NULL PRIMARY KEY DEFAULT nextval('country_id_seq'),
	alpha2code text NOT NULL UNIQUE
);

CREATE TABLE CountryTranslations (
    country SMALLINT NOT NULL REFERENCES Countries(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    language language_type NOT NULL,
    name text NOT NULL,
    PRIMARY KEY (country, language)
);


CREATE SEQUENCE school_id_seq INCREMENT 1;    
CREATE TABLE Schools (
	id SMALLINT NOT NULL PRIMARY KEY DEFAULT nextval('school_id_seq'),
	name TEXT NOT NULL,
	country SMALLINT NOT NULL REFERENCES Countries(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE degree_id_seq INCREMENT 1; 
CREATE TABLE Degrees (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('degree_id_seq'),
	acronym text NOT NULL,
	image text NOT NULL,
	school SMALLINT NOT NULL REFERENCES Schools(id) ON UPDATE NO ACTION ON DELETE NO ACTION ,
	year SMALLINT NOT NULL
);

CREATE TABLE DegreeTranslations (
	degree BIGINT NOT NULL REFERENCES Degrees(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL,
	PRIMARY KEY(degree, language)
);

CREATE SEQUENCE degreeclass_id_seq INCREMENT 1;   
CREATE TABLE DegreeClasses (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('degreeclass_id_seq'),
	year SMALLINT NOT NULL,
	semester SMALLINT NULL,
	ects SMALLINT NULL,
	isOptional BOOLEAN NOT NULL DEFAULT 'FALSE',
	degree BIGINT NOT NULL REFERENCES Degrees(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE DegreeClassTranslations (
	degreeClass BIGINT NOT NULL  REFERENCES DegreeClasses(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	name text NOT NULL,
	description text NOT NULL,
	PRIMARY KEY(degreeClass, language)
);

CREATE SEQUENCE degreeclasstopic_id_seq INCREMENT 1;   
CREATE TABLE DegreeClassTopics (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('degreeclasstopic_id_seq'),
	degreeClass BIGINT NOT NULL REFERENCES DegreeClasses(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	ordering SMALLINT NULL
);

CREATE TABLE DegreeClassTopicTranslations (
	degreeClassTopic BIGINT NOT NULL REFERENCES DegreeClassTopics(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	description text NOT NULL,
	PRIMARY KEY(degreeClassTopic, language)
);

