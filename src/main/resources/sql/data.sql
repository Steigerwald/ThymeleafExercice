SET FOREIGN_KEY_CHECKS=0;

TRUNCATE TABLE escalade.TBL_ROLE;
TRUNCATE TABLE escalade.TBL_USER;
TRUNCATE TABLE escalade.TBL_TOPO;
TRUNCATE TABLE escalade.TBL_SITE;
TRUNCATE TABLE escalade.TBL_SECTEUR;
TRUNCATE TABLE escalade.TBL_VOIE;
TRUNCATE TABLE escalade.TBL_COMMENTAIRE;
TRUNCATE TABLE escalade.TBL_RESERVATION_TOPO;

INSERT INTO escalade.TBL_ROLE (id_role, nom_role) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT INTO escalade.TBL_USER (id_user, mail_user, mot_de_passe_user, nom_user,prenom_user,role_id_role) VALUES
(1, 'admin@gmail.com', '$2a$10$2Mgmm6OlYvb54/SQik3EO.jCHE4RA4S5lpDSjmD73sDnS1zhPK1Ru', 'Steigerwald','Brice',1),
(2, 'user@gmail.com', '$2a$10$2Mgmm6OlYvb54/SQik3EO.jCHE4RA4S5lpDSjmD73sDnS1zhPK1Ru', 'Steigerwald','Jacques',2);

INSERT INTO TBL_VOIE (id_voie, cotation, nombre_longueurs, nombre_points,numero_voie,secteur_id_secteur) VALUES
(1, '2c', 2, 3,'1',1),
(2, '1a', 1, 2,'4',2),
(3, '9c', 5, 8,'3',4),
(4, '2c', 4, 6,'2',2),
(5, '5a', 2, 2,'8',3),
(6, '4b', 4, 7,'7',1),
(7, '3a', 2, 2,'9',1),
(8, '6a', 2, 2,'5',5),
(9, '3b', 4, 7,'10',5),
(10, '3a', 2, 2,'6',6);

INSERT INTO escalade.TBL_SECTEUR (id_secteur, descriptif_secteur, hauteur, nom_secteur,site_id_site) VALUES
(1, 'secteur de la plaine, au milieu des volcans', '360m','le feu embrasé',2),
(2, 'secteur vertical, le mur ensoleillé', '540m','le mur',1),
(3, 'secteur du rocher, dans les falaises', '470m','le rocher',1),
(4, 'secteur du plateau, dans le maquis', '250m','le plateau',4),
(5, 'secteur de la montagne éternelle, dans le glacier', '800m','le glacier',3),
(6, 'secteur de la colline oubliée', '350m','l"oubliée',5);

INSERT INTO escalade.TBL_SITE (id_site, descriptif, lieu, nom_site,topo_id_topo) VALUES
(1, 'site de la réunion, au milieu de l"ile', 'La réunion','l"ile',1),
(2, 'site de l"etna, au milieu du volcan', 'Sicile','le volcan',1),
(3, 'site des Alpes, dans les glaciers', 'les Alpes','Val torens',1),
(4, 'site du Massif, en haut du col', 'Massif Central','le plateau central',2),
(5, 'site du mont Oublié, sur la colline', 'Massif Armoricain','Le mont',3);

INSERT INTO escalade.TBL_TOPO (id_topo, date_parution, description, disponible, location, nom_topo,reservation_id_reservation) VALUES
(1,'2019-09-28 02:45:30', 'topo pour profiter de l"ile, de la montagne et des volcans',1,1,'La balade complète',1),
(2,'2019-11-28 02:55:30', 'topo pour profiter du centre montagneux de la France',1,0,'La vieille montagne',2),
(3,'2020-01-28 11:45:30', 'topo pour profiter de la Bretagne, des collines de granite',1,1,'Kenavo',3);

INSERT INTO escalade.TBL_RESERVATION_TOPO (id_reservation, acceptation, date_reservation, numero_reservation,user_id_user) VALUES
(1,1,'2020-04-28 02:45:30', '45', 1),
(2,0,'2020-04-21 02:45:30', '55', 2),
(3,1,'2020-05-04 02:45:30', '65', 1);

INSERT INTO escalade.TBL_COMMENTAIRE (id_commentaire, contenu, date_commentaire, site_id_site) VALUES
(1,'Trop compliqué, pas d"eau','2020-04-29 02:45:30', 1),
(2,'hyper facile, même trop','2020-05-01 02:45:30', 4),
(3,'super content, trop d"eau','2020-04-28 02:45:30', 5);

SET FOREIGN_KEY_CHECKS=1;