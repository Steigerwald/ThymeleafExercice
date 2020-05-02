USE escalade;

INSERT INTO TBL_ROLE (id_role, nom_role) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

INSERT INTO TBL_USER (id_user, mail_user, mot_de_passe_user, nom_user,prenom_user,role_id_role) VALUES
(1, 'admin@gmail.com', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Steigerwald','Brice',1),
(2, 'user@gmail.com', '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'Steigerwald','Jacques',2);
