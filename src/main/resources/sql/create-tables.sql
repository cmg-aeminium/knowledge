CREATE DATABASE sweranker
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
    
CREATE TYPE language_type AS ENUM (
	'pt-PT',
    'en-UK'
);   

CREATE SEQUENCE textcontents_id_seq INCREMENT 1;    
CREATE TABLE TextContents (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('textcontents_id_seq'),
	language language_type NOT NULL DEFAULT 'pt-PT',
	textvalue text
);

CREATE TABLE TranslatedTexts (
    id BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	language language_type NOT NULL DEFAULT 'en-UK',
	textvalue text,
	PRIMARY KEY(id, language)
);

CREATE SEQUENCE knowledgebodies_id_seq INCREMENT 1;
CREATE TABLE KnowledgeBodies (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('knowledgebodies_id_seq'),
    year smallint NOT NULL,
	image text NOT NULL,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	createdAt TIMESTAMP WITH TIME ZONE DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

CREATE SEQUENCE knowledgeareas_id_seq INCREMENT 1;    
CREATE TABLE KnowledgeAreas (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('knowledgeareas_id_seq'),
	image text NOT NULL,
	knowledgebody BIGINT NOT NULL REFERENCES KnowledgeBodies(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	createdAt TIMESTAMP WITH TIME ZONE DEFAULT (current_timestamp AT TIME ZONE 'UTC')
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
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	country SMALLINT NOT NULL REFERENCES Countries(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	createdAt TIMESTAMP WITH TIME ZONE DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

CREATE SEQUENCE courses_id_seq INCREMENT 1; 
CREATE TABLE Courses (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('courses_id_seq'),
	acronym text NOT NULL,
	image text NOT NULL,
	school SMALLINT NOT NULL REFERENCES Schools(id) ON UPDATE NO ACTION ON DELETE NO ACTION ,
	year SMALLINT NOT NULL,
	name BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	createdAt TIMESTAMP WITH TIME ZONE DEFAULT (current_timestamp AT TIME ZONE 'UTC')
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
	course BIGINT NOT NULL REFERENCES Courses(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	createdAt TIMESTAMP WITH TIME ZONE DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);

CREATE SEQUENCE courseclasstopic_id_seq INCREMENT 1;   
CREATE TABLE CourseClassTopics (
	id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('courseclasstopic_id_seq'),
	courseClass BIGINT NOT NULL REFERENCES CourseClasses(id) ON UPDATE NO ACTION ON DELETE NO ACTION,
	ordering SMALLINT NULL,
	description BIGINT NOT NULL REFERENCES TextContents(id) ON UPDATE NO ACTION ON DELETE NO ACTION
);


CREATE TYPE user_status_type AS ENUM (
    'ACTIVE',
    'PENDING',
    'INACTIVE'
);

CREATE SEQUENCE users_id_seq INCREMENT 1;   
CREATE TABLE Users (
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('users_id_seq'),
    name TEXT NULL,
    email TEXT NOT NULL,
    salt TEXT NULL,
    password TEXT NULL,
    status user_status_type NOT NULL DEFAULT 'PENDING',
    language language_type NOT NULL DEFAULT 'pt-PT',
    createdAt TIMESTAMP WITH TIME ZONE DEFAULT (current_timestamp AT TIME ZONE 'UTC')
);
