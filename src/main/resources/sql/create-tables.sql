CREATE DATABASE sweranker
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
    
CREATE TYPE language_type AS ENUM (
	'PT_PT',
    'EN_UK'
);   

CREATE SEQUENCE textcontents_id_seq INCREMENT 1;    
CREATE TABLE TextContents (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('textcontents_id_seq'),
	language language_type NOT NULL,
	textvalue text
);

CREATE TABLE TranslatedTexts (
    id BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL,
	textvalue text,
	PRIMARY KEY(id, language)
);

CREATE SEQUENCE bodiesofknowledge_id_seq INCREMENT 1;
CREATE TABLE BodiesOfKnowledge (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('bodiesofknowledge_id_seq'),
    year smallint NOT NULL,
	image text NOT NULL,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE knowledgeareas_id_seq INCREMENT 1;    
CREATE TABLE KnowledgeAreas (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('knowledgeareas_id_seq'),
	image text NOT NULL,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE knowledgetopics_id_seq INCREMENT 1;    
CREATE TABLE KnowledgeTopics(
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('knowledgetopics_id_seq'),
	knowledgearea SMALLINT NOT NULL REFERENCES KnowledgeAreas(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE country_id_seq INCREMENT 1;
CREATE TABLE Countries(
	id SMALLINT NOT NULL PRIMARY KEY DEFAULT nextval('country_id_seq'),
	alpha2code text NOT NULL UNIQUE,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE school_id_seq INCREMENT 1;    
CREATE TABLE Schools (
	id SMALLINT NOT NULL PRIMARY KEY DEFAULT nextval('school_id_seq'),
	name TEXT NOT NULL,
	country SMALLINT NOT NULL REFERENCES Countries(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE courses_id_seq INCREMENT 1; 
CREATE TABLE Courses (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('courses_id_seq'),
	acronym text NOT NULL,
	image text NOT NULL,
	school SMALLINT NOT NULL REFERENCES Schools(id) ON UPDATE NO ACTION ON DELETE NO ACTION ,
	year SMALLINT NOT NULL,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE courseclasses_id_seq INCREMENT 1;   
CREATE TABLE CourseClasses (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('courseclasses_id_seq'),
	year SMALLINT NOT NULL,
	semester SMALLINT NULL,
	ects SMALLINT NULL,
	isOptional BOOLEAN NOT NULL DEFAULT 'FALSE',
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	course BIGINT NOT NULL REFERENCES Courses(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE courseclasstopic_id_seq INCREMENT 1;   
CREATE TABLE CourseClassTopics (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('courseclasstopic_id_seq'),
	courseClass BIGINT NOT NULL REFERENCES CourseClasses(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	ordering SMALLINT NULL,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);
