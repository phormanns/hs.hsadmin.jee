CREATE SEQUENCE bank_account_bank_account_i_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE bank_account_bank_account_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE basecomponent (
    basecomponent_id integer DEFAULT nextval(('"basecomponent_basecomponent_seq"'::text)::regclass) NOT NULL,
    basecomponent_code character varying(10) NOT NULL,
    description character varying(100) NOT NULL,
    sorting integer NOT NULL,
    valid boolean NOT NULL
);

CREATE SEQUENCE basecomponent_basecomponent_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE basepacket (
    basepacket_id integer DEFAULT nextval(('"basepacket_basepacket_id_seq"'::text)::regclass) NOT NULL,
    basepacket_code character varying(10) NOT NULL,
    description character varying(100) NOT NULL,
    sorting integer NOT NULL,
    valid boolean NOT NULL,
    article_number integer NOT NULL
);

CREATE SEQUENCE basepacket_basepacket_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE billdata_billdata_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE business_partner (
    bp_id integer DEFAULT nextval(('"business_partner_bp_id_seq"'::text)::regclass) NOT NULL,
    member_id integer NOT NULL,
    member_code character varying(20) NOT NULL,
    member_since date,
    member_until date,
    member_role character varying(100),
    author_contract date,
    nondisc_contract date,
    shares_updated date,
    shares_signed integer NOT NULL,
    uid_vat character varying(20),
    free boolean DEFAULT false NOT NULL,
    indicator_vat character varying(20) DEFAULT 'GROSS'::character varying NOT NULL,
    exempt_vat boolean DEFAULT false NOT NULL,
    CONSTRAINT ckc_member_id_business CHECK (((member_id >= 10000) AND (member_id <= 99999))),
    CONSTRAINT ckc_shares_signed_business CHECK ((shares_signed >= 0)),
    CONSTRAINT ckt_business_partner CHECK ((((((member_since IS NULL) AND (member_until IS NULL)) OR ((member_since IS NOT NULL) AND (member_until IS NULL))) OR (((member_since IS NOT NULL) AND (member_until IS NOT NULL)) AND (member_since < member_until))) AND ((member_code)::text ~~ 'hsh00-%'::text)))
);

CREATE SEQUENCE business_partner_bp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE contact (
    contact_id integer DEFAULT nextval(('"contact_contact_id_seq"'::text)::regclass) NOT NULL,
    bp_id integer NOT NULL,
    salut character varying(30),
    first_name character varying(40) NOT NULL,
    last_name character varying(40) NOT NULL,
    title character varying(20),
    firma character varying(120),
    co character varying(50),
    street character varying(50),
    zipcode character varying(10),
    city character varying(40),
    country character varying(30),
    phone_private character varying(30),
    phone_office character varying(30),
    phone_mobile character varying(30),
    fax character varying(30),
    email character varying(100) NOT NULL,
    CONSTRAINT ckc_email_contact CHECK (((email)::text ~~ '%@%.%'::text))
);

CREATE VIEW business_partner_ticket AS
    SELECT (business_partner.member_id)::character varying(20) AS member_id, "substring"((business_partner.member_code)::text, 7) AS member_code, contact.salut, contact.first_name, contact.last_name, contact.title, contact.firma, contact.co, contact.street, contact.zipcode, contact.city, contact.country, contact.phone_private, contact.phone_office, contact.phone_mobile, contact.fax, contact.email, ((COALESCE(to_char((business_partner.member_since)::timestamp with time zone, 'YYYY-DD-MM'::text), '-'::text) || ' / '::text) || COALESCE(to_char((business_partner.member_until)::timestamp with time zone, 'YYYY-DD-MM'::text), '-'::text)) AS comment, 1 AS valid FROM (business_partner LEFT JOIN contact ON ((contact.bp_id = business_partner.bp_id))) ORDER BY (business_partner.member_id)::character varying(20);

CREATE TABLE component (
    basepacket_id integer NOT NULL,
    basecomponent_id integer NOT NULL,
    min_quantity integer NOT NULL,
    max_quantity integer NOT NULL,
    default_quantity integer NOT NULL,
    increment_quantity integer NOT NULL,
    include_quantity integer NOT NULL,
    admin_only boolean NOT NULL,
    article_number integer NOT NULL,
    component_id integer DEFAULT nextval(('"component_id_seq"'::text)::regclass) NOT NULL,
    CONSTRAINT ckt_component CHECK ((((((((((0 <= min_quantity) AND (min_quantity <= default_quantity)) AND (default_quantity <= max_quantity)) AND (include_quantity <= default_quantity)) AND (0 <= include_quantity)) AND (mod(min_quantity, increment_quantity) = 0)) AND (mod(max_quantity, increment_quantity) = 0)) AND (mod(default_quantity, increment_quantity) = 0)) AND (mod(include_quantity, increment_quantity) = 0)))
);

CREATE SEQUENCE component_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE contact_contact_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE contactrole_ref (
    contact_id integer NOT NULL,
    role character varying(40) NOT NULL
);

CREATE SEQUENCE database_database_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE database (
    database_id integer DEFAULT nextval('database_database_id_seq'::regclass) NOT NULL,
    engine character varying(12) NOT NULL,
    packet_id integer NOT NULL,
    name character varying(64) NOT NULL,
    owner character varying(24) NOT NULL,
    encoding character varying(12) NOT NULL
);

CREATE SEQUENCE dbuser_dbuser_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE database_user (
    dbuser_id integer DEFAULT nextval('dbuser_dbuser_id_seq'::regclass) NOT NULL,
    engine character varying(12) NOT NULL,
    packet_id integer NOT NULL,
    name character varying(64) NOT NULL
);

CREATE SEQUENCE domain_domain_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE domain (
    domain_name character varying(256) NOT NULL,
    domain_since date,
    domain_dns_master character varying(64),
    domain_id integer DEFAULT nextval('domain_domain_id_seq'::regclass) NOT NULL,
    domain_owner integer NOT NULL
);

CREATE TABLE domain__domain_option (
    domain_option_id integer NOT NULL,
    domain_id integer NOT NULL
);

CREATE TABLE domain_option (
    domain_option_id integer DEFAULT nextval(('"domain_option_id_seq"'::text)::regclass) NOT NULL,
    domain_option_name character varying(50) NOT NULL
);

CREATE SEQUENCE domain_option_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE emailaddr_emailaddr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE emailaddr (
    emailaddr_id integer DEFAULT nextval('emailaddr_emailaddr_id_seq'::regclass) NOT NULL,
    localpart character varying(64) NOT NULL,
    domain_id integer NOT NULL,
    target text,
    subdomain character varying(64)
);

CREATE SEQUENCE emailalias_emailalias_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE emailalias (
    emailalias_id integer DEFAULT nextval('emailalias_emailalias_id_seq'::regclass) NOT NULL,
    pac_id integer NOT NULL,
    target text NOT NULL,
    name character varying(96) NOT NULL
);

CREATE TABLE hive (
    hive_id integer DEFAULT nextval(('"hive_hive_id_seq"'::text)::regclass) NOT NULL,
    hive_name character varying(3) NOT NULL,
    inet_addr_id integer NOT NULL,
    description character varying(100)
);

CREATE SEQUENCE hive_hive_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE inet_addr (
    inet_addr_id integer DEFAULT nextval(('"inet_addr_inet_addr_id_seq"'::text)::regclass) NOT NULL,
    inet_addr inet NOT NULL,
    description character varying(100),
    CONSTRAINT ckc_inet_addr_inet_add CHECK ((masklen(inet_addr) = 32))
);

CREATE SEQUENCE inet_addr_inet_addr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE member_id_seq
    START WITH 10200
    INCREMENT BY 1
    MINVALUE 10200
    MAXVALUE 99999
    CACHE 1;

CREATE TABLE packet (
    packet_id integer DEFAULT nextval(('"packet_packet_id_seq"'::text)::regclass) NOT NULL,
    packet_name character varying(5) NOT NULL,
    bp_id integer NOT NULL,
    hive_id integer NOT NULL,
    created date NOT NULL,
    cancelled date,
    cur_inet_addr_id integer,
    old_inet_addr_id integer,
    free boolean DEFAULT false NOT NULL,
    basepacket_id integer,
    CONSTRAINT ckt_packet CHECK (((cancelled IS NULL) OR (cancelled > created)))
);

CREATE TABLE packet_component (
    basecomponent_id integer NOT NULL,
    packet_id integer NOT NULL,
    quantity integer NOT NULL,
    created date,
    cancelled date,
    packet_component_id integer DEFAULT nextval(('"packet_component_id_seq"'::text)::regclass) NOT NULL,
    CONSTRAINT ckt_packet_component CHECK (((cancelled IS NULL) OR (cancelled > created)))
);

CREATE SEQUENCE packet_component_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE packet_packet_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE price (
    id integer NOT NULL,
    article_number integer NOT NULL,
    price numeric(10,2) NOT NULL,
    vat numeric(4,2) NOT NULL,
    price_list integer
);

CREATE SEQUENCE price_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE price_id_seq OWNED BY price.id;

CREATE TABLE price_list (
    id integer NOT NULL,
    name character varying(20)
);

CREATE SEQUENCE price_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE price_list_id_seq OWNED BY price_list.id;

CREATE TABLE pricelist_ref (
    bp_id integer NOT NULL,
    price_list character varying(40) NOT NULL
);

CREATE TABLE queue (
    queue_entry_id integer DEFAULT nextval(('"queue_queue_entry_id_seq"'::text)::regclass) NOT NULL,
    user_id integer NOT NULL,
    started timestamp without time zone NOT NULL,
    finished timestamp without time zone,
    title character varying(192),
    details text,
    CONSTRAINT ckt_queue CHECK (((finished IS NULL) OR (finished >= started)))
);

CREATE SEQUENCE queue_queue_entry_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE queue_task (
    task_id integer DEFAULT nextval(('"queue_task_id_seq"'::text)::regclass) NOT NULL,
    proc bytea,
    exception text,
    started timestamp without time zone NOT NULL,
    finished timestamp without time zone,
    title character varying(192),
    details text,
    user_id integer
);

CREATE SEQUENCE queue_task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE role (
    role_name character varying(40) NOT NULL
);

CREATE TABLE sepa_mandat (
    sepa_mandat_id integer DEFAULT nextval(('"sepa_mandat_id_seq"'::text)::regclass) NOT NULL,
    bp_id integer NOT NULL,
    bank_customer character varying(50) NOT NULL,
    bank_name character varying(50),
    bank_iban character varying(40) NOT NULL,
    bank_bic character varying(40) NOT NULL,
    mandat_ref character varying(40) NOT NULL,
    mandat_signed date NOT NULL,
    mandat_since date NOT NULL,
    mandat_used date,
    mandat_until date,
    CONSTRAINT dateschk1 CHECK (((mandat_signed <= mandat_since) AND (mandat_since <= mandat_until))),
    CONSTRAINT dateschk2 CHECK ((((mandat_since <= mandat_until) AND (mandat_since <= mandat_used)) AND (mandat_used <= mandat_until)))
);

CREATE SEQUENCE sepa_mandat_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE unixuser_unixuser_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE unixuser (
    unixuser_id integer DEFAULT nextval('unixuser_unixuser_id_seq'::regclass) NOT NULL,
    name character varying(64) NOT NULL,
    comment character varying(128),
    shell character varying(64) NOT NULL,
    homedir character varying(128) NOT NULL,
    locked boolean NOT NULL,
    packet_id integer NOT NULL,
    quota_softlimit integer DEFAULT 0 NOT NULL,
    userid integer NOT NULL,
    quota_hardlimit integer DEFAULT 0,
    CONSTRAINT unixuser_userid CHECK ((userid >= 10000))
);

ALTER TABLE ONLY price ALTER COLUMN id SET DEFAULT nextval('price_id_seq'::regclass);

ALTER TABLE ONLY price_list ALTER COLUMN id SET DEFAULT nextval('price_list_id_seq'::regclass);

ALTER TABLE ONLY business_partner
    ADD CONSTRAINT business_partner_bp_id_key UNIQUE (bp_id);

ALTER TABLE ONLY database
    ADD CONSTRAINT database_uniq UNIQUE (engine, name);

ALTER TABLE ONLY database_user
    ADD CONSTRAINT database_user_uniq UNIQUE (engine, name);

ALTER TABLE ONLY emailaddr
    ADD CONSTRAINT emailaddr_uniq UNIQUE (localpart, subdomain, domain_id);

ALTER TABLE ONLY emailalias
    ADD CONSTRAINT emailalias_pkey PRIMARY KEY (emailalias_id);

ALTER TABLE ONLY emailalias
    ADD CONSTRAINT emailalias_uniq UNIQUE (name);

ALTER TABLE ONLY basecomponent
    ADD CONSTRAINT pk_basecomponent PRIMARY KEY (basecomponent_id);

ALTER TABLE ONLY basepacket
    ADD CONSTRAINT pk_basepacket PRIMARY KEY (basepacket_id);

ALTER TABLE ONLY business_partner
    ADD CONSTRAINT pk_business_partner PRIMARY KEY (bp_id);

ALTER TABLE ONLY component
    ADD CONSTRAINT pk_component PRIMARY KEY (component_id);

ALTER TABLE ONLY contact
    ADD CONSTRAINT pk_contact PRIMARY KEY (contact_id);

ALTER TABLE ONLY contactrole_ref
    ADD CONSTRAINT pk_contactrole_ref PRIMARY KEY (contact_id, role);

ALTER TABLE ONLY database
    ADD CONSTRAINT pk_database PRIMARY KEY (database_id);

ALTER TABLE ONLY database_user
    ADD CONSTRAINT pk_database_user PRIMARY KEY (dbuser_id);

ALTER TABLE ONLY domain__domain_option
    ADD CONSTRAINT pk_domain__domain_option PRIMARY KEY (domain_option_id, domain_id);

ALTER TABLE ONLY domain_option
    ADD CONSTRAINT pk_domain_option PRIMARY KEY (domain_option_id);

ALTER TABLE ONLY hive
    ADD CONSTRAINT pk_hive PRIMARY KEY (hive_id);

ALTER TABLE ONLY inet_addr
    ADD CONSTRAINT pk_inet_addr PRIMARY KEY (inet_addr_id);

ALTER TABLE ONLY packet
    ADD CONSTRAINT pk_packet PRIMARY KEY (packet_id);

ALTER TABLE ONLY packet_component
    ADD CONSTRAINT pk_packet_component PRIMARY KEY (packet_component_id);

ALTER TABLE ONLY role
    ADD CONSTRAINT pk_role PRIMARY KEY (role_name);

ALTER TABLE ONLY sepa_mandat
    ADD CONSTRAINT pk_sepa_mandat PRIMARY KEY (sepa_mandat_id);

ALTER TABLE ONLY unixuser
    ADD CONSTRAINT pk_unixuser PRIMARY KEY (unixuser_id);

ALTER TABLE ONLY price_list
    ADD CONSTRAINT price_list_pkey PRIMARY KEY (id);

ALTER TABLE ONLY price_list
    ADD CONSTRAINT price_list_uniq_name UNIQUE (name);

ALTER TABLE ONLY price
    ADD CONSTRAINT price_pkey PRIMARY KEY (id);

ALTER TABLE ONLY unixuser
    ADD CONSTRAINT unixuser_name_key UNIQUE (name);

CREATE UNIQUE INDEX basecomponent_in_1 ON basecomponent USING btree (basecomponent_code);

CREATE UNIQUE INDEX basepacket_in_1 ON basepacket USING btree (basepacket_code);

CREATE INDEX component_in_1 ON component USING btree (basecomponent_id);

CREATE INDEX contact_in_1 ON contact USING btree (bp_id);

CREATE UNIQUE INDEX customer_in_1 ON business_partner USING btree (member_code);

CREATE UNIQUE INDEX customer_in_2 ON business_partner USING btree (member_id);

CREATE UNIQUE INDEX database_unique_owner ON database_user USING btree (name, engine);

CREATE UNIQUE INDEX domain_option_name_idx ON domain_option USING btree (domain_option_name);

CREATE UNIQUE INDEX domain_unique_id ON domain USING btree (domain_id);

CREATE UNIQUE INDEX domain_unique_name ON domain USING btree (domain_name);

CREATE UNIQUE INDEX emailaddr_uniq2 ON emailaddr USING btree (localpart, domain_id) WHERE (subdomain IS NULL);

CREATE UNIQUE INDEX hive_in_1 ON hive USING btree (hive_name);

CREATE UNIQUE INDEX inet_addr_in_1 ON inet_addr USING btree (inet_addr);

CREATE INDEX packet_component_in_1 ON packet_component USING btree (packet_id);

CREATE INDEX packet_component_in_2 ON packet_component USING btree (basecomponent_id);

CREATE UNIQUE INDEX packet_in_1 ON packet USING btree (packet_name);

CREATE INDEX packet_in_2 ON packet USING btree (bp_id);

ALTER TABLE ONLY packet
    ADD CONSTRAINT base_packet_ref FOREIGN KEY (basepacket_id) REFERENCES basepacket(basepacket_id);

ALTER TABLE ONLY database
    ADD CONSTRAINT database_owner FOREIGN KEY (owner, engine) REFERENCES database_user(name, engine);

ALTER TABLE ONLY database
    ADD CONSTRAINT database_packet_id_fkey FOREIGN KEY (packet_id) REFERENCES packet(packet_id) DEFERRABLE;

ALTER TABLE ONLY database_user
    ADD CONSTRAINT dbuser_packet_id_fkey FOREIGN KEY (packet_id) REFERENCES packet(packet_id) DEFERRABLE;

ALTER TABLE ONLY domain__domain_option
    ADD CONSTRAINT domain_id_fkey FOREIGN KEY (domain_id) REFERENCES domain(domain_id) DEFERRABLE;

ALTER TABLE ONLY domain__domain_option
    ADD CONSTRAINT domain_option_id_fkey FOREIGN KEY (domain_option_id) REFERENCES domain_option(domain_option_id) DEFERRABLE;

ALTER TABLE ONLY domain
    ADD CONSTRAINT domain_owner_chk FOREIGN KEY (domain_owner) REFERENCES unixuser(unixuser_id) MATCH FULL;

ALTER TABLE ONLY emailaddr
    ADD CONSTRAINT email_domain FOREIGN KEY (domain_id) REFERENCES domain(domain_id);

ALTER TABLE ONLY emailalias
    ADD CONSTRAINT emailalias_pac_id_fkey FOREIGN KEY (pac_id) REFERENCES packet(packet_id) DEFERRABLE;

ALTER TABLE ONLY sepa_mandat
    ADD CONSTRAINT fk_bank_acc_reference_business FOREIGN KEY (bp_id) REFERENCES business_partner(bp_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY component
    ADD CONSTRAINT fk_bcomp_bpack FOREIGN KEY (basepacket_id) REFERENCES basepacket(basepacket_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY packet_component
    ADD CONSTRAINT fk_comp_pack FOREIGN KEY (packet_id) REFERENCES packet(packet_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY contact
    ADD CONSTRAINT fk_contact_reference_business FOREIGN KEY (bp_id) REFERENCES business_partner(bp_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY contactrole_ref
    ADD CONSTRAINT fk_contactrole_ref_contact FOREIGN KEY (contact_id) REFERENCES contact(contact_id);

ALTER TABLE ONLY contactrole_ref
    ADD CONSTRAINT fk_contactrole_ref_role FOREIGN KEY (role) REFERENCES role(role_name);

ALTER TABLE ONLY hive
    ADD CONSTRAINT fk_hive_inet FOREIGN KEY (inet_addr_id) REFERENCES inet_addr(inet_addr_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY packet
    ADD CONSTRAINT fk_pac_cur_inet FOREIGN KEY (cur_inet_addr_id) REFERENCES inet_addr(inet_addr_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY packet
    ADD CONSTRAINT fk_pac_old_inet FOREIGN KEY (old_inet_addr_id) REFERENCES inet_addr(inet_addr_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY packet
    ADD CONSTRAINT fk_packet_bp FOREIGN KEY (bp_id) REFERENCES business_partner(bp_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY packet
    ADD CONSTRAINT fk_packet_hive FOREIGN KEY (hive_id) REFERENCES hive(hive_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY pricelist_ref
    ADD CONSTRAINT fk_pricelist_ref_bp FOREIGN KEY (bp_id) REFERENCES business_partner(bp_id) ON UPDATE RESTRICT ON DELETE CASCADE;

ALTER TABLE ONLY pricelist_ref
    ADD CONSTRAINT fk_pricelist_ref_pricelist FOREIGN KEY (price_list) REFERENCES price_list(name) ON UPDATE RESTRICT ON DELETE CASCADE;

ALTER TABLE ONLY component
    ADD CONSTRAINT fk_reference_13 FOREIGN KEY (basecomponent_id) REFERENCES basecomponent(basecomponent_id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE ONLY price
    ADD CONSTRAINT price_price_list_fkey FOREIGN KEY (price_list) REFERENCES price_list(id);

ALTER TABLE ONLY queue_task
    ADD CONSTRAINT queue_task_user_id_fkey FOREIGN KEY (user_id) REFERENCES unixuser(unixuser_id) ON DELETE SET NULL;

ALTER TABLE ONLY queue
    ADD CONSTRAINT queue_user_id_fkey FOREIGN KEY (user_id) REFERENCES unixuser(unixuser_id) DEFERRABLE;
