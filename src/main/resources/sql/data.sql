USE escalades;

SET FOREIGN_KEY_CHECKS=0;

TRUNCATE TABLE escalades.TBL_ROLE;
TRUNCATE TABLE escalades.TBL_USER;
TRUNCATE TABLE escalades.TBL_TOPO;
TRUNCATE TABLE escalades.TBL_SITE;
TRUNCATE TABLE escalades.TBL_SECTEUR;
TRUNCATE TABLE escalades.TBL_VOIE;
TRUNCATE TABLE escalades.TBL_COMMENTAIRE;
TRUNCATE TABLE escalades.TBL_RESERVATION;

INSERT INTO escalades.TBL_ROLE (id_role, nom_role) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT INTO escalades.TBL_USER (id_user, mail_user, mot_de_passe_user, nom_user,prenom_user,role_id_role) VALUES
(1, 'admin@gmail.com', '$2a$10$2Mgmm6OlYvb54/SQik3EO.jCHE4RA4S5lpDSjmD73sDnS1zhPK1Ru', 'Steigerwald','Brice',1),
(2, 'user@gmail.com', '$2a$10$2Mgmm6OlYvb54/SQik3EO.jCHE4RA4S5lpDSjmD73sDnS1zhPK1Ru', 'Steigerwald','Jacques',2),
(3, 'tara@gmail.com', '$2a$10$2Mgmm6OlYvb54/SQik3EO.jCHE4RA4S5lpDSjmD73sDnS1zhPK1Ru', 'Hot','Tara',2),
(4, 'jean@gmail.com', '$2a$10$2Mgmm6OlYvb54/SQik3EO.jCHE4RA4S5lpDSjmD73sDnS1zhPK1Ru', 'Pierre','Jean',2),
(5, 'anne@gmail.com', '$2a$10$2Mgmm6OlYvb54/SQik3EO.jCHE4RA4S5lpDSjmD73sDnS1zhPK1Ru', 'Priska','Anne',2);

INSERT INTO escalades.TBL_VOIE (id_voie, cotation, nombre_longueurs, nombre_points,numero_voie,secteur_id_secteur) VALUES
(1, '2c', 2, 3,'1',1),
(2, '1a', 1, 2,'4',2),
(3, '9c', 5, 8,'3',4),
(4, '2c', 4, 6,'2',2),
(5, '5a', 2, 2,'8',3),
(6, '4b', 4, 7,'7',1),
(7, '3a', 2, 2,'9',1),
(8, '6a', 2, 2,'5',5),
(9, '3b', 4, 7,'10',5),
(10, '3a', 2, 2,'6',6),
(11,'1a',1,5,'34',7);

INSERT INTO escalades.TBL_SECTEUR (id_secteur, descriptif_secteur, hauteur, nom_secteur,site_id_site) VALUES
(1, 'secteur de la plaine, au milieu des volcans', 360,'le feu embrasé',2),
(2, 'secteur vertical, le mur ensoleillé', 540,'le mur',1),
(3, 'secteur du rocher, dans les falaises', 470,'le rocher',1),
(4, 'secteur du plateau, dans le maquis', 250,'le plateau',4),
(5, 'secteur de la montagne éternelle, dans le glacier', 800,'le glacier',3),
(6, 'secteur de la colline oubliée', 350,'l"oubliée',5),
(7, 'secteur de la plaine française', 20,'le plat',6);

INSERT INTO escalades.TBL_SITE (id_site, descriptif, lieu, nom_site,officiel,topo_id_topo,ispublic, user_id_user) VALUES
(1, 'site de la réunion, au milieu de l"ile', 'La réunion','l"ile',1,1,0,2),
(2, 'site de l"etna, au milieu du volcan', 'Sicile','le volcan',0,1,0,3),
(3, 'site des Alpes, dans les glaciers', 'les Alpes','Val torens',1,2,0,1),
(4, 'site du Massif, en haut du col', 'Massif Central','le plateau central',0,3,0,2),
(5, 'site du mont Oublié, sur la colline', 'Massif Armoricain','Le mont',1,4,0,4),
(6, 'site de toutes les peurs', 'Hymalaya français','le mont trop dur',1,5,0,5);

INSERT INTO escalades.TBL_TOPO (id_topo, date_parution, description, disponible, location, nom_topo,owner_id_user) VALUES
(1,'2019-09-28 02:45:30', 'topo pour profiter de l"ile, de la montagne et des volcans',0,1,'La balade complète',4),
(2,'2019-11-28 02:55:30', 'topo pour profiter du centre montagneux de la France',0,1,'La vieille montagne',4),
(3,'2020-01-28 11:45:30', 'topo pour profiter de la Bretagne, des collines de granite',0,1,'Kenavo',5),
(4,'2020-02-14 11:45:30', 'topo pour profiter de la haute altitude',0,1,'Alpes',1),
(5,'2020-04-14 11:45:30', 'topo pour profiter des plaines',0,1,'Corèze',3);

INSERT INTO escalades.TBL_RESERVATION (id_reservation, etat, date_reservation,topo_id_topo,user_id_user) VALUES
(1,'acceptee','2020-04-28 02:45:30',2,1),
(2,'en attente','2020-04-21 02:45:30',3,2),
(3,'acceptee','2020-05-04 02:45:30',1,1),
(4,'refusee','2020-05-25 02:45:30',4,2);


INSERT INTO escalades.TBL_COMMENTAIRE (id_commentaire, contenu, date_commentaire, site_id_site,user_id_user) VALUES
(1,'Trop compliqué, pas d"eau','2020-04-29 02:45:30', 1,4),
(2,'hyper facile, même trop','2020-05-01 02:45:30', 4,3),
(3,'super content, trop d"eau','2020-04-28 02:45:30', 5,5),
(4,'quelle galère pour la marche à pieds','2020-04-26 03:56:24',2,1),
(5,'je ne suis pas fan de ce site','2020-04-26 03:56:24',4,3);

SET FOREIGN_KEY_CHECKS=1;