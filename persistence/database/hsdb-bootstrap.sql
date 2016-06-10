--
-- Table: basepacket
--
INSERT INTO basepacket (basepacket_code, description, sorting, valid, article_number) 
	VALUES ('PAC/DW', 'Dynamic-Web/Base', 300, true, 100);
INSERT INTO basepacket (basepacket_code, description, sorting, valid, article_number) 
	VALUES ('PAC/SW', 'Static-Web/Base', 400, true, 200);
INSERT INTO basepacket (basepacket_code, description, sorting, valid, article_number) 
	VALUES ('PAC/WEB', 'Web/Base', 100, true, 300);
INSERT INTO basepacket (basepacket_code, description, sorting, valid, article_number) 
	VALUES ('SRV/MGD', 'Managed Server', 200, true, 400);
	
--
-- Table: basecomponent
--
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
  	VALUES ('MULTI', 'Accounts', 100, true);
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
	VALUES ('TRAFFIC', 'Monatliches Datenvolumen in GB', 200, true);
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
	VALUES ('QUOTA', 'Festplattenspeicherplatz in MB', 300, true);
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
	VALUES ('DAEMON', 'Nutzung eines eigenen Servers/Daemons', 400, true);
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
	VALUES ('MIRROR', 'Echtzeitspiegelung der Daten', 500, true);
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
	VALUES ('BACKUP', 'Nächtliches Backup', 600, true);
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
	VALUES ('DISK', 'Nächtliches Backup', 700, true);
INSERT INTO basecomponent (basecomponent_code, description, sorting, valid)
	VALUES ('CPU', 'Nächtliches Backup', 800, true);

--
-- Table: component
--
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 1, 10, 1, 1, 1, false, 100 FROM basepacket, basecomponent WHERE basepacket_code='PAC/DW' AND  basecomponent_code='MULTI';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 1, 10, 1, 1, 1, false, 200 FROM basepacket, basecomponent WHERE basepacket_code='PAC/SW' AND  basecomponent_code='MULTI';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 1, 10, 1, 1, 1, false, 300 FROM basepacket, basecomponent WHERE basepacket_code='PAC/WEB' AND  basecomponent_code='MULTI';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 128, 40960, 128, 128, 128, false, 101 FROM basepacket, basecomponent WHERE basepacket_code='PAC/DW' AND  basecomponent_code='QUOTA';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 512, 40960, 512, 512, 512, false, 201 FROM basepacket, basecomponent WHERE basepacket_code='PAC/SW' AND  basecomponent_code='QUOTA';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 512, 40960, 512, 512, 0, false, 301 FROM basepacket, basecomponent WHERE basepacket_code='PAC/WEB' AND  basecomponent_code='QUOTA';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 2, 40960, 2, 2, 2, false, 102 FROM basepacket, basecomponent WHERE basepacket_code='PAC/DW' AND  basecomponent_code='TRAFFIC';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 4, 40960, 4, 4, 4, false, 202 FROM basepacket, basecomponent WHERE basepacket_code='PAC/SW' AND  basecomponent_code='TRAFFIC';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 5, 40960, 5, 5, 0, false, 302 FROM basepacket, basecomponent WHERE basepacket_code='PAC/WEB' AND  basecomponent_code='TRAFFIC';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 4, 0, 1, 0, false, 103 FROM basepacket, basecomponent WHERE basepacket_code='PAC/DW' AND  basecomponent_code='DAEMON';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 4, 0, 1, 0, false, 303 FROM basepacket, basecomponent WHERE basepacket_code='PAC/WEB' AND  basecomponent_code='DAEMON';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 1, 1, 1, 1, false, 104 FROM basepacket, basecomponent WHERE basepacket_code='PAC/DW' AND  basecomponent_code='MIRROR';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 1, 1, 1, 1, false, 204 FROM basepacket, basecomponent WHERE basepacket_code='PAC/SW' AND  basecomponent_code='MIRROR';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 1, 1, 1, 1, false, 304 FROM basepacket, basecomponent WHERE basepacket_code='PAC/WEB' AND  basecomponent_code='MIRROR';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 1, 1, 1, 1, false, 105 FROM basepacket, basecomponent WHERE basepacket_code='PAC/DW' AND  basecomponent_code='BACKUP';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 1, 1, 1, 1, false, 205 FROM basepacket, basecomponent WHERE basepacket_code='PAC/SW' AND  basecomponent_code='BACKUP';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 0, 1, 1, 1, 1, false, 305 FROM basepacket, basecomponent WHERE basepacket_code='PAC/WEB' AND  basecomponent_code='BACKUP';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 25, 100, 25, 25, 0, false, 410 FROM basepacket, basecomponent WHERE basepacket_code='SRV/MGD' AND  basecomponent_code='DISK';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 1, 16, 1, 1, 0, false, 420 FROM basepacket, basecomponent WHERE basepacket_code='SRV/MGD' AND  basecomponent_code='CPU';
INSERT INTO component (basepacket_id, basecomponent_id, min_quantity, max_quantity, default_quantity, increment_quantity, include_quantity, admin_only, article_number)
	SELECT basepacket_id, basecomponent_id, 10, 1000, 10, 10, 0, false, 430 FROM basepacket, basecomponent WHERE basepacket_code='SRV/MGD' AND  basecomponent_code='TRAFFIC';
		
--
-- Table: role 
--
INSERT INTO role (role_name) VALUES ('billing');
INSERT INTO role (role_name) VALUES ('operation');

--
-- Table: business_partner 
--
INSERT INTO business_partner (member_id, member_code, member_since, shares_signed, free, indicator_vat, exempt_vat)
	VALUES (10000, 'hsh00-hsh', current_date, 0, TRUE, 'NET', FALSE);

--
-- table: contact 
--
INSERT INTO contact (bp_id, salut, first_name, last_name, firma, email)
	SELECT bp_id, 'Herr', 'Sigi', 'Superb', 'Hosting Inc.', 'info@example.com' FROM business_partner WHERE member_id=10000;

--
-- table: contactrole_ref 
--
INSERT INTO contactrole_ref ( contact_id, role ) 
	SELECT contact_id, 'billing' FROM contact WHERE email='info@example.com';
INSERT INTO contactrole_ref ( contact_id, role ) 
	SELECT contact_id, 'operation' FROM contact WHERE email='info@example.com';
	
--
-- Table: inet_addr 
--
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.67', 'address 67');
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.72', 'address 72');
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.73', 'address 73');
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.74', 'address 74');
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.75', 'address 75');
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.76', 'address 76');
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.77', 'address 77');
INSERT INTO inet_addr (inet_addr, description)
	VALUES (inet '176.9.242.78', 'address 78');

--
-- Table: Hive 
--
INSERT INTO hive (hive_name, inet_addr_id, description)
	SELECT 'h99', inet_addr_id, 'Test Hive h99' FROM inet_addr WHERE inet_addr = inet '176.9.242.67';

--
-- table: packet 
--
INSERT INTO packet (packet_name, bp_id, hive_id, created, cur_inet_addr_id, free, basepacket_id)
	SELECT 'hsh00', business_partner.bp_id, hive.hive_id, current_date, inet_addr.inet_addr_id, true, basepacket.basepacket_id 
		FROM business_partner, hive, inet_addr, basepacket 
		WHERE hive_name='h99' AND inet_addr = inet '176.9.242.72' AND member_id = 10000 AND basepacket.basepacket_code='PAC/DW'; 
INSERT INTO packet (packet_name, bp_id, hive_id, created, cur_inet_addr_id, free, basepacket_id)
	SELECT 'hsh01', business_partner.bp_id, hive.hive_id, current_date, inet_addr.inet_addr_id, true, basepacket.basepacket_id 
		FROM business_partner, hive, inet_addr, basepacket 
		WHERE hive_name='h99' AND inet_addr = inet '176.9.242.73' AND member_id = 10000 AND basepacket.basepacket_code='PAC/DW';
		
--
-- table: packet_component 
--
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 1, packet.packet_id, 2, current_date FROM packet 
		WHERE packet.packet_name = 'hsh00'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 2, packet.packet_id, 128, current_date FROM packet 
		WHERE packet.packet_name = 'hsh00'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 3, packet.packet_id, 1, current_date FROM packet 
		WHERE packet.packet_name = 'hsh00'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 4, packet.packet_id, 1, current_date FROM packet 
		WHERE packet.packet_name = 'hsh00'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 5, packet.packet_id, 1, current_date FROM packet 
		WHERE packet.packet_name = 'hsh00'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 1, packet.packet_id, 2, current_date FROM packet 
		WHERE packet.packet_name = 'hsh01'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 2, packet.packet_id, 128, current_date FROM packet 
		WHERE packet.packet_name = 'hsh01'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 3, packet.packet_id, 1, current_date FROM packet 
		WHERE packet.packet_name = 'hsh01'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 4, packet.packet_id, 1, current_date FROM packet 
		WHERE packet.packet_name = 'hsh01'; 
INSERT INTO packet_component (basecomponent_id, packet_id, quantity, created)
	SELECT 5, packet.packet_id, 1, current_date FROM packet 
		WHERE packet.packet_name = 'hsh01'; 

--
-- table: unixuser 
--
INSERT INTO unixuser (name, comment, shell, homedir, locked, packet_id, userid)
	SELECT 'hsh00', 'packet hsh00', '/bin/bash', '/home/pacs/hsh00', FALSE, packet_id, 10001 FROM packet 
		WHERE packet_name='hsh00'; 
INSERT INTO unixuser (name, comment, shell, homedir, locked, packet_id, userid)
	SELECT 'hsh01', 'packet hsh01', '/bin/bash', '/home/pacs/hsh01', FALSE, packet_id, 10002 FROM packet 
		WHERE packet_name='hsh01'; 
INSERT INTO unixuser (name, comment, shell, homedir, locked, packet_id, userid)
	SELECT 'hsh01-ad', 'hostmaster ad', '/bin/bash', '/home/pacs/hsh01/users/ad', FALSE, packet_id, 10003 FROM packet 
		WHERE packet_name='hsh01'; 

--
-- table: domain 
--
INSERT INTO domain (domain_name, domain_since, domain_dns_master, domain_owner)
	SELECT 'hostsharing.net', current_date, 'dns.hostsharing.net', unixuser_id FROM unixuser
		WHERE unixuser.name='hsh00';
		
		--
-- table: domain_option
--
INSERT INTO domain_option (domain_option_name) 
	VALUES ('backupmxforexternalmx');
INSERT INTO domain_option (domain_option_name) 
	VALUES ('greylisting');
INSERT INTO domain_option (domain_option_name)
	VALUES ('htdocsfallback');
INSERT INTO domain_option (domain_option_name) 
	VALUES ('includes');
INSERT INTO domain_option (domain_option_name) 
	VALUES ('indexes');
INSERT INTO domain_option (domain_option_name)
	VALUES ('multiviews');
INSERT INTO domain_option (domain_option_name)
	VALUES ('php');

--
-- table: price_list
--
INSERT INTO price_list VALUES (1, 'Default Price List');

--
-- table: customer_price_list_mapping
--
INSERT INTO pricelist_ref (SELECT bp_id, 'Default Price List' FROM business_partner);

