--
-- PostgreSQL database dump
--

SET search_path = public, pg_catalog;

--
-- TOC entry 37 (OID 16977)
-- Name: plpgsql_call_handler (); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION plpgsql_call_handler () RETURNS language_handler
    AS '/usr/local/pgsql/lib/plpgsql.so', 'plpgsql_call_handler'
    LANGUAGE c;


--
-- TOC entry 36 (OID 16978)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: public; Owner: 
--

CREATE PROCEDURAL LANGUAGE plpgsql HANDLER plpgsql_call_handler;


--
-- TOC entry 2 (OID 75857)
-- Name: pga_graphs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_graphs (
    graphname character varying(64) NOT NULL,
    graphsource text,
    graphcode text
);


--
-- TOC entry 3 (OID 75864)
-- Name: pga_layout; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_layout (
    tablename character varying(64) NOT NULL,
    nrcols smallint,
    colnames text,
    colwidth text
);


--
-- TOC entry 4 (OID 75871)
-- Name: pga_images; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_images (
    imagename character varying(64) NOT NULL,
    imagesource text
);


--
-- TOC entry 5 (OID 75878)
-- Name: pga_queries; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_queries (
    queryname character varying(64) NOT NULL,
    querytype character(1),
    querycommand text,
    querytables text,
    querylinks text,
    queryresults text,
    querycomments text
);


--
-- TOC entry 6 (OID 75885)
-- Name: pga_reports; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_reports (
    reportname character varying(64) NOT NULL,
    reportsource text,
    reportbody text,
    reportprocs text,
    reportoptions text
);


--
-- TOC entry 7 (OID 75892)
-- Name: pga_forms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_forms (
    formname character varying(64) NOT NULL,
    formsource text
);


--
-- TOC entry 8 (OID 75899)
-- Name: pga_diagrams; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_diagrams (
    diagramname character varying(64) NOT NULL,
    diagramtables text,
    diagramlinks text
);


--
-- TOC entry 9 (OID 75906)
-- Name: pga_scripts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE pga_scripts (
    scriptname character varying(64) NOT NULL,
    scriptsource text
);


--
-- TOC entry 10 (OID 75913)
-- Name: page; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE page (
    id integer NOT NULL,
    id_father integer NOT NULL,
    label character varying(20),
    url character varying(100),
    id_rights smallint
);


--
-- TOC entry 11 (OID 75919)
-- Name: entity; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE entity (
    id serial NOT NULL,
    id_civility smallint,
    company character varying(30),
    last_name character varying(30),
    first_name character varying(30),
    address character varying(100),
    address_bis character varying(100),
    city character varying(20),
    cp character varying(5),
    phone character varying(10),
    cellphone character varying(10),
    fax character varying(10),
    email character varying(30)
);


--
-- TOC entry 12 (OID 75935)
-- Name: agency; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE agency (
    id serial NOT NULL,
    id_entity integer NOT NULL,
    id_user_leader integer,
    parent_company boolean,
    customer_code character varying(10),
    bill_code character varying(10),
    agency_code character varying(2)
);


--
-- TOC entry 13 (OID 75948)
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE employee (
    id serial NOT NULL,
    id_agency integer NOT NULL,
    id_entity integer NOT NULL,
    id_rights smallint NOT NULL,
    login character varying(10) NOT NULL,
    "password" character varying(10) NOT NULL
);


--
-- TOC entry 38 (OID 131298)
-- Name: get_type (text, text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_type (text, text) RETURNS text
    AS 'DECLARE
	-- id ALIAS FOR $1;
	-- key ALIAS FOR $2;
	value text;
BEGIN
	SELECT INTO value type.value
	FROM type
	WHERE type.id = $1 AND type.key = $2;

	RETURN value;
END;
'
    LANGUAGE plpgsql;


--
-- TOC entry 14 (OID 135645)
-- Name: view_employee; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW view_employee AS
    SELECT employee.id, employee.id_agency AS "agencyId", employee.login, employee."password", employee.id_rights AS rights, entity.id_civility, get_type('civility'::text, (entity.id_civility)::text) AS civility, entity.last_name AS "lastName", entity.first_name AS "firstName", ((entity.first_name || ' '::character varying) || entity.last_name) AS name, entity.address_bis AS "addressBis", entity.address, entity.city, entity.cp AS "postalCode", entity.phone, entity.cellphone AS "cellPhone", entity.email, entity.fax FROM (employee JOIN entity ON ((employee.id_entity = entity.id)));


--
-- TOC entry 39 (OID 135695)
-- Name: get_nb_employee (bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_nb_employee (bigint) RETURNS integer
    AS 'DECLARE
	result int4;
BEGIN
	SELECT INTO result count(id)
	FROM employee
	WHERE employee.id_agency = $1;

	IF (result IS NULL) THEN
		result := 0;
	END IF;

	RETURN result;
END;
'
    LANGUAGE plpgsql;


--
-- TOC entry 15 (OID 135698)
-- Name: view_agency; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW view_agency AS
    SELECT agency.id, agency.id_user_leader, agency.parent_company, get_nb_employee((agency.id)::bigint) AS nb_employee, agency.customer_code, agency.bill_code, agency.agency_code, entity.company, entity.address, entity.city, entity.cp, entity.phone, entity.fax, entity.email FROM agency, entity WHERE (agency.id_entity = entity.id);


--
-- TOC entry 40 (OID 140914)
-- Name: get_customer_code (bigint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION get_customer_code (bigint) RETURNS text
    AS 'DECLARE
	-- customer_id ALIAS FOR $1;
	tmp text;
	result	text;
BEGIN
	-- cast
	tmp = $1 || '''';

	-- creation of the code with 6 zeros 
	WHILE (char_length(tmp) != 5) LOOP
		tmp := ''0'' || tmp;
	END LOOP;
	
	SELECT INTO result agency.customer_code || tmp
	FROM agency, customer
	WHERE agency.id = customer.id_agency AND customer.id = $1;

	RETURN result;
END;



'
    LANGUAGE plpgsql;


--
-- TOC entry 16 (OID 140940)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE customer (
    id serial NOT NULL,
    id_agency integer NOT NULL,
    id_entity integer NOT NULL,
    db_creation_date date,
    db_modification_date date
);


--
-- TOC entry 17 (OID 140947)
-- Name: view_customer; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW view_customer AS
    SELECT customer.id, get_customer_code((customer.id)::bigint) AS customer_code, customer.id_agency, entity.id_civility, get_type('civility'::text, (entity.id_civility)::text) AS civility, entity.company, entity.last_name, entity.first_name, ((entity.first_name || ' '::character varying) || entity.last_name) AS name, entity.address_bis, entity.address, entity.city, entity.cp AS postal_code, entity.phone, entity.cellphone AS cell_phone, entity.email, entity.fax FROM customer, entity WHERE (customer.id_entity = entity.id);


--
-- TOC entry 41 (OID 141451)
-- Name: import_product (character varying, character varying, character varying, character varying, smallint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION import_product (character varying, character varying, character varying, character varying, smallint) RETURNS bigint
    AS 'DECLARE
	-- $1 ALIAS FOR reference;
	-- $2 ALIAS FOR designation;
	-- $3 ALIAS FOR brand;
	-- $4 ALIAS FOR category;
	-- $5 ALIAS FOR type;
 	new_id_catalogue bigint;
	new_id_category bigint;
  	new_id_brand bigint;
  	is_brand int2;
	is_category int2;
BEGIN
	-- new brand ?
	SELECT INTO is_brand type.key
	FROM type
	WHERE type.id = ''brand'' AND upper(type.value) LIKE (upper($3));

	IF (is_brand IS NULL) THEN
		SELECT INTO new_id_brand (max(CAST(CAST(type.key AS text) AS int2)) + 1)
 		FROM type
		WHERE type.id = ''brand'';
			
		IF (new_id_brand IS NULL) THEN
			new_id_brand := 1;
		END IF;

		INSERT INTO type (id, key, value)
		VALUES (''brand'', CAST(new_id_brand AS varchar(20)), upper($3));
	ELSE
		new_id_brand := is_brand;
	END IF;

	-- new category ?
	SELECT INTO is_category type.key
	FROM type
	WHERE type.id = ''catalogue_category'' AND upper(type.value) LIKE (upper($4));

	IF (is_category IS NULL) THEN
		SELECT INTO new_id_category (max(CAST(CAST(type.key AS text) AS int2)) + 1)
 		FROM type
		WHERE type.id = ''catalogue_category'';
			
		IF (new_id_category IS NULL) THEN
			new_id_category := 1;
		END IF;

		INSERT INTO type (id, key, value)
		VALUES (''catalogue_category'', CAST(new_id_category AS varchar(20)), upper($4));
	ELSE
		new_id_category := is_category;
	END IF;

	-- product existing ?
	SELECT INTO new_id_catalogue id
	FROM catalogue
	WHERE catalogue.reference = $1;

	IF (new_id_catalogue IS	NULL) THEN
		INSERT INTO catalogue (reference, designation, id_brand, id_type, id_category)
		VALUES (upper($1), $2, new_id_brand, $5, new_id_category);

		SELECT INTO new_id_catalogue currval(''catalogue_id_seq'');
	ELSE
		new_id_catalogue := 0;
	END IF;

	RETURN new_id_catalogue;
END;




'
    LANGUAGE plpgsql;


--
-- TOC entry 18 (OID 141452)
-- Name: type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "type" (
    id character varying(20) NOT NULL,
    "key" character varying(20) NOT NULL,
    value character varying(30) NOT NULL
);


--
-- TOC entry 19 (OID 141468)
-- Name: catalogue; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE catalogue (
    id serial NOT NULL,
    reference character varying(25) NOT NULL,
    designation character varying(100),
    id_brand smallint DEFAULT 0 NOT NULL,
    id_type smallint DEFAULT 1 NOT NULL,
    id_category smallint DEFAULT 0 NOT NULL
);


--
-- TOC entry 20 (OID 147673)
-- Name: view_catalogue; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW view_catalogue AS
    SELECT catalogue.id, catalogue.reference, catalogue.designation, get_type('brand'::text, (catalogue.id_brand)::text) AS brand, get_type('catalogue_type'::text, (catalogue.id_type)::text) AS "type", get_type('catalogue_category'::text, (catalogue.id_category)::text) AS category FROM catalogue;


--
-- TOC entry 42 (OID 147690)
-- Name: add_agency (integer, boolean, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_agency (integer, boolean, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying) RETURNS integer
    AS 'DECLARE
	-- employeeLeaderId ALIAS FOR $1;
	-- parentCompany ALIAS FOR $2;
	-- customerCode ALIAS FOR $3;
	-- billCode ALIAS FOR $4;
	-- agencyCode ALIAS FOR $5;
	-- company ALIAS FOR $6;
	-- adress ALIAS FOR $7;
	-- city ALIAS FOR $8;
	-- postal code ALIAS FOR $9;
	-- phone ALIAS FOR $10;
	-- fax ALIAS FOR $11;
	-- email ALIAS FOR $12;
	new_entity_id bigint;
	new_agency_id bigint;

	result bigint;
BEGIN
	result := 0;

	-- creation of the entity
	INSERT INTO entity (id_civility, company, address, city, cp, phone, fax, email)
	VALUES (0, $6, $7, $8, $9, $10, $11, $12);

	SELECT INTO new_entity_id currval(''entity_id_seq'');
	
	-- creation of the agency
	INSERT INTO	agency (id_entity, customer_code, bill_code, agency_code, id_user_leader, parent_company)
	VALUES (new_entity_id, $3, $4, $5, $1, $2);

	SELECT INTO new_agency_id currval(''agency_id_seq'');
		
	result := new_agency_id;

	RETURN result;
END;





'
    LANGUAGE plpgsql;


--
-- TOC entry 50 (OID 147691)
-- Name: add_customer (integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_customer (integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying) RETURNS integer
    AS 'DECLARE
	-- agencyId ALIAS FOR $1;
	-- civilityId ALIAS FOR $2;
	-- last name ALIAS FOR $3;
	-- first name ALIAS $4;
	-- adress ALIAS FOR $5;
	-- city ALIAS FOR $6;
	-- postal code ALIAS FOR $7;
	-- phone ALIAS FOR $8;
	-- cell phone ALIAS FOR $9;
	-- fax ALIAS FOR $10;
	-- email ALIAS FOR $11;
	new_entity_id int4;
	new_customer_id int4;

	result int4;
BEGIN
	result := 0;

	-- creation of the entity
	IF ($2 = 5 OR $2 = 6 OR $2 = 7) THEN
		-- company 
		INSERT INTO entity (id_civility, company, address, city, cp, phone, cellphone, fax, email)
		VALUES ($2, $3, $5, $6, $7, $8, $9, $10, $11);
	ELSE
		-- person
		INSERT INTO entity (id_civility, last_name, first_name, address, city, cp, phone, cellphone, fax, email)
		VALUES ($2, $3, $4, $5, $6, $7, $8, $9, $10, $11);
	END IF;

	SELECT INTO new_entity_id currval(''entity_id_seq'');
	
	-- creation of the employee
	INSERT INTO customer (id_agency, id_entity, db_creation_date, db_modification_date)
	VALUES ($1, new_entity_id, current_date, current_date);

	SELECT INTO new_customer_id currval(''customer_id_seq'');

	RETURN result;
END;








'
    LANGUAGE plpgsql;


--
-- TOC entry 43 (OID 147692)
-- Name: add_employee (integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, smallint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_employee (integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, smallint) RETURNS integer
    AS 'DECLARE
	-- agencyId ALIAS FOR $1;
	-- civilityId ALIAS FOR $2;
	-- last name ALIAS FOR $3;
	-- first name ALIAS $4;
	-- adress ALIAS FOR $5;
	-- city ALIAS FOR $6;
	-- postal code ALIAS FOR $7;
	-- phone ALIAS FOR $8;
	-- cell phone ALIAS FOR $9;
	-- login ALIAS FOR $10;
	-- password ALIAS FOR $11;
	-- rights ALIAS FOR $12;
	new_entity_id int4;
	new_employee_id int4;

	new_login text;
	result int4;
BEGIN
	result := 0;

	-- check if the login owns to another employee
	SELECT INTO new_login employee.login FROM employee WHERE login = $10;

	IF (new_login IS NULL) THEN
		-- creation of the entity
		INSERT INTO entity (id_civility, last_name, first_name, address, city, cp, phone, cellphone)
		VALUES ($2, $3, $4, $5, $6, $7, $8, $9);

		SELECT INTO new_entity_id currval(''entity_id_seq'');
	
		-- creation of the employee
		INSERT INTO employee (id_agency, id_entity, login, password, id_rights)
		VALUES ($1, new_entity_id, $10, $11, $12);

		SELECT INTO new_employee_id currval(''employee_id_seq'');
		
		result := new_employee_id;
	ELSE
		-- two employees can not have the same login
		result := -1;
	END IF;

	RETURN result;
END;





'
    LANGUAGE plpgsql;


--
-- TOC entry 44 (OID 147693)
-- Name: add_product (character varying, character varying, smallint, smallint, smallint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION add_product (character varying, character varying, smallint, smallint, smallint) RETURNS integer
    AS 'DECLARE
	result int4;
BEGIN
	INSERT INTO catalogue (reference, designation, id_brand, id_category, id_type)
	VALUES ($1, $2, $3, $4, $5);

	SELECT INTO result currval(''catalogue_id_seq'');

	RETURN result;
END;


'
    LANGUAGE plpgsql;


--
-- TOC entry 45 (OID 147694)
-- Name: update_agency (integer, integer, boolean, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION update_agency (integer, integer, boolean, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying) RETURNS integer
    AS 'DECLARE
	-- id ALIAS FOR $1;
	-- employeeLeaderId ALIAS FOR $2;
	-- parentCompany ALIAS FOR $3;
	-- customerCode ALIAS FOR $4;
	-- billCode ALIAS FOR $5;
	-- agencyCode ALIAS FOR $6;
	-- company ALIAS FOR $7;
	-- adress ALIAS FOR $8;
	-- city ALIAS FOR $9;
	-- postal code ALIAS FOR $10;
	-- phone ALIAS FOR $12;
	-- fax ALIAS FOR $13;
	-- email ALIAS FOR $13;
	agency_entity int4;
BEGIN
	-- get the entity id associated to the agency
	SELECT INTO agency_entity id_entity FROM agency WHERE id = $1;

	-- update of the entity
	UPDATE entity 
	SET company = $7,
		 address = $8,
		 city = $9,
	 	 cp = $10,
		 phone = $11,
		 fax = $12,
		 email = $13
	WHERE id = agency_entity;

	-- modification of the agency
	UPDATE agency	
	SET customer_code = $4,
		 bill_code = $5,
		 agency_code = $6,
		 id_user_leader = $2,
		 parent_company = $3
	WHERE id = $1;

	RETURN $1;
END;








'
    LANGUAGE plpgsql;


--
-- TOC entry 49 (OID 147695)
-- Name: update_customer (integer, integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION update_customer (integer, integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying) RETURNS integer
    AS 'DECLARE
	-- customerId alias FOR $1;
	-- agencyId ALIAS FOR $2;
	-- civilityId ALIAS FOR $3;
	-- last name ALIAS FOR $4;
	-- first name ALIAS $5;
	-- adress ALIAS FOR $6;
	-- city ALIAS FOR $7;
	-- postal code ALIAS FOR $8;
	-- phone ALIAS FOR $9;
	-- cell phone ALIAS FOR $10;
	-- fax ALIAS FOR $11;
	-- email ALIAS FOR $12;
	customer_entity int4;
	result int4;
BEGIN
	result := $1;

	-- update of the entity
	SELECT INTO customer_entity customer.id_entity 
	FROM customer 
	WHERE customer.id = $1;

	IF ($3 = 5 OR $3 = 6 OR $3 = 7) THEN
		-- company
		UPDATE entity	
		SET id_civility = $3, 
				company = $4,
				last_name = '''',
				first_name = '''',
				address = $6, 
				city = $7, 
				cp = $8, 
				phone = $9,
				cellphone = $10,
				fax = $11,
				email = $12
		WHERE entity.id = customer_entity;
	ELSE
		UPDATE entity	
		SET id_civility = $3, 
				last_name = $4,
				first_name = $5,
				address = $6, 
				city = $7, 
				cp = $8, 
				phone = $9,
				cellphone = $10,
				fax = $11,
				email = $12
		WHERE entity.id = customer_entity;
	END IF;

	-- update of the customer
	UPDATE customer
	SET id_agency = $2,
			db_modification_date = current_date
	WHERE employee.id = $1;
	
	RETURN result;
END;





'
    LANGUAGE plpgsql;


--
-- TOC entry 46 (OID 147696)
-- Name: update_employee (integer, integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, smallint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION update_employee (integer, integer, smallint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, smallint) RETURNS integer
    AS 'DECLARE
	-- employeeId alias FOR $1;
	-- agencyId ALIAS FOR $2;
	-- civilityId ALIAS FOR $3;
	-- last name ALIAS FOR $4;
	-- first name ALIAS $5;
	-- adress ALIAS FOR $6;
	-- city ALIAS FOR $7;
	-- postal code ALIAS FOR $8;
	-- phone ALIAS FOR $9;
	-- cell phone ALIAS FOR $10;
	-- login ALIAS FOR $11;
	-- password ALIAS FOR $12;
	-- rights ALIAS FOR $13;
	employee_entity int4;
	other_login varchar;
	result int4;
BEGIN
	result := $1;

	-- check if the new login is not the login of another employee
	SELECT INTO other_login employee.login
	FROM employee
	WHERE employee.id <> $1 AND employee.login = $11;

	IF (other_login IS NOT NULL) THEN
		result := -1;
	ELSE
		-- update of the entity
		SELECT INTO employee_entity employee.id_entity 
		FROM employee 
		WHERE employee.id = $1;

		UPDATE entity
		SET id_civility = $3, 
			last_name = $4,
			first_name = $5,
			address = $6, 
			city = $7, 
			cp = $8, 
			phone = $9,
			cellphone = $10 
		WHERE entity.id = employee_entity;
	
		-- update of the employee
		UPDATE employee
		SET id_agency = $2,
			login = $11,
			password = $12,
			id_rights = $13
		WHERE employee.id = $1;
	END IF;

	RETURN result;
END;





'
    LANGUAGE plpgsql;


--
-- TOC entry 47 (OID 147697)
-- Name: update_product (integer, character varying, character varying, smallint, smallint, smallint); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION update_product (integer, character varying, character varying, smallint, smallint, smallint) RETURNS integer
    AS 'DECLARE

BEGIN
	UPDATE catalogue
	SET reference = $2,
		 designation = $3,
		 id_brand = $4,
		 id_category = $5,
		 id_type = $6
	WHERE id = $1;

	RETURN $1;
END;

'
    LANGUAGE plpgsql;


--
-- TOC entry 48 (OID 147701)
-- Name: delete_employee (integer); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION delete_employee (integer) RETURNS boolean
    AS 'DECLARE
	-- id_employee ALIAS FOR $1;
	entity_id int4;
BEGIN

	-- get the entity of the employee
	SELECT INTO entity_id employee.id_entity
	FROM employee
	WHERE employee.id = $1;

	-- delete the employee
	DELETE FROM employee WHERE employee.id = $1;

	-- delete the entity associated to the employee
	DELETE FROM entity WHERE entity.id	= entity_id;

	RETURN true;
END;	

'
    LANGUAGE plpgsql;


--
-- TOC entry 32 (OID 135678)
-- Name: employee_agency_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE INDEX employee_agency_index ON employee USING btree (id_agency);


--
-- TOC entry 21 (OID 75862)
-- Name: pga_graphs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_graphs
    ADD CONSTRAINT pga_graphs_pkey PRIMARY KEY (graphname);


--
-- TOC entry 22 (OID 75869)
-- Name: pga_layout_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_layout
    ADD CONSTRAINT pga_layout_pkey PRIMARY KEY (tablename);


--
-- TOC entry 23 (OID 75876)
-- Name: pga_images_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_images
    ADD CONSTRAINT pga_images_pkey PRIMARY KEY (imagename);


--
-- TOC entry 24 (OID 75883)
-- Name: pga_queries_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_queries
    ADD CONSTRAINT pga_queries_pkey PRIMARY KEY (queryname);


--
-- TOC entry 25 (OID 75890)
-- Name: pga_reports_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_reports
    ADD CONSTRAINT pga_reports_pkey PRIMARY KEY (reportname);


--
-- TOC entry 26 (OID 75897)
-- Name: pga_forms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_forms
    ADD CONSTRAINT pga_forms_pkey PRIMARY KEY (formname);


--
-- TOC entry 27 (OID 75904)
-- Name: pga_diagrams_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_diagrams
    ADD CONSTRAINT pga_diagrams_pkey PRIMARY KEY (diagramname);


--
-- TOC entry 28 (OID 75911)
-- Name: pga_scripts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY pga_scripts
    ADD CONSTRAINT pga_scripts_pkey PRIMARY KEY (scriptname);


--
-- TOC entry 29 (OID 75915)
-- Name: page_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY page
    ADD CONSTRAINT page_pkey PRIMARY KEY (id);


--
-- TOC entry 30 (OID 75922)
-- Name: entity_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY entity
    ADD CONSTRAINT entity_pkey PRIMARY KEY (id);


--
-- TOC entry 31 (OID 75938)
-- Name: agency_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY agency
    ADD CONSTRAINT agency_pkey PRIMARY KEY (id);


--
-- TOC entry 33 (OID 75951)
-- Name: employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (id);


--
-- TOC entry 34 (OID 140943)
-- Name: customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 35 (OID 141474)
-- Name: catalogue_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY catalogue
    ADD CONSTRAINT catalogue_pkey PRIMARY KEY (id);


