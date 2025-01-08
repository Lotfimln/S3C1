-- Table Locataire
INSERT INTO Locataire (Id_Locataire, Nom, Prenom, Mail, Telephone, DateNais, DateDépart)
VALUES
('L001', 'Dupont', 'Jean', 'jean.dupont@mail.com', '+33612345678', TO_DATE('1985-03-15', 'YYYY-MM-DD'), NULL),
('L002', 'Martin', 'Claire', 'claire.martin@mail.com', '+33798765432', TO_DATE('1990-07-20', 'YYYY-MM-DD'), NULL);

-- Table Immeuble
INSERT INTO Immeuble (Id_Immeuble, Adresse, NbLogements, TypeImmeuble)
VALUES
(1, '10 rue des Lilas, Paris', 10, 'Appartement'),
(2, '20 avenue des Champs, Lyon', 5, 'Maison');

-- Table Assureur
INSERT INTO Assureur (Id_Assurance, Nom, DateAssurance, Prime)
VALUES
(1, 'AssurHome', TO_DATE('2023-01-01', 'YYYY-MM-DD'), 500),
(2, 'MaisonSecure', TO_DATE('2024-01-01', 'YYYY-MM-DD'), 700);

-- Table Entreprise
INSERT INTO Entreprise (Id_Entreprise, Nom, SIREN, Adresse)
VALUES
(1, 'RenovBat', 123456789, '5 avenue des Entrepreneurs, Lille'),
(2, 'ElectricAuto', 987654321, '3 boulevard des Lumières, Marseille');

-- Table Index_Compteur
INSERT INTO Index_Compteur (Id_Index_Compteur, TypeCompteur, ValeurCourante, AncienneValeur, DateReleve)
VALUES
(1, 'Électricité', 150.200, 140.500, TO_DATE('2025-01-01', 'YYYY-MM-DD')),
(2, 'Eau', 300.750, 280.650, TO_DATE('2025-01-02', 'YYYY-MM-DD'));

-- Table Louable
INSERT INTO Louable (Id_Louable, Adresse, Superficie, NumeroFiscal, Statut, DateAnniversaire, Id_Immeuble, Id_Assurance)
VALUES
(1, '10 rue des Lilas, Paris', 75.500, 123456789012, 'Y', TO_DATE('2024-01-01', 'YYYY-MM-DD'), 1, 1),
(2, '20 avenue des Champs, Lyon', 120.000, 987654321098, 'N', TO_DATE('2025-01-01', 'YYYY-MM-DD'), 2, 2);

-- Table Taxe
INSERT INTO Taxe (Id_Taxe, MontantTaxeFoncieres, DateTaxe, Id_Immeuble)
VALUES
(1, 1500.000, 2024, 1),
(2, 2000.000, 2025, 2);

-- Table Facture
INSERT INTO Facture (Id_Facture, Montant, DateFacture, ReferenceDevis, Entreprise, DatePaiement, Id_Entreprise, Id_Louable)
VALUES
(1, 500.000, TO_DATE('2024-06-01', 'YYYY-MM-DD'), 'DEV12345', 'RenovBat', TO_DATE('2024-06-15', 'YYYY-MM-DD'), 1, 1),
(2, 300.000, TO_DATE('2025-01-01', 'YYYY-MM-DD'), 'DEV67890', 'ElectricAuto', TO_DATE('2025-01-10', 'YYYY-MM-DD'), 2, 2);

-- Table Contrat_de_location
INSERT INTO Contrat_de_location (Id_Contrat_de_location, DateDebut, DateFin, MontantLoyer, ProvisionsCharges, TypeContrat, DateAnniversaire, IndiceICC, MontantCaution, NomCaution, AdresseCaution, Id_Louable)
VALUES
(1, TO_DATE('2023-01-01', 'YYYY-MM-DD'), NULL, 1200.000, 200.000, 'Bail classique', TO_DATE('2024-01-01', 'YYYY-MM-DD'), 1.500, 2400.000, 'Dupont', '5 rue de la Paix, Paris', 1),
(2, TO_DATE('2024-05-01', 'YYYY-MM-DD'), NULL, 1500.000, 250.000, 'Bail meublé', TO_DATE('2025-05-01', 'YYYY-MM-DD'), 1.600, 3000.000, 'Martin', '7 avenue de Lyon, Lyon', 2);

-- Table Charge
INSERT INTO Charge (Id_Charge, Type_Charge, Montant, Recuperable, PeriodeDebut, PeriodeFin, Id_Facture, Id_Louable)
VALUES
(1, 'Entretien', 100.000, 'Y', TO_DATE('2024-01-01', 'YYYY-MM-DD'), TO_DATE('2024-12-31', 'YYYY-MM-DD'), 1, 1),
(2, 'Réparations', 200.000, 'N', TO_DATE('2025-01-01', 'YYYY-MM-DD'), TO_DATE('2025-06-30', 'YYYY-MM-DD'), 2, 2);

-- Table Garage
INSERT INTO Garage (Id_Louable, DateAcqui)
VALUES
(1, TO_DATE('2020-01-01', 'YYYY-MM-DD'));

-- Table Logement
INSERT INTO Logement (Id_Louable, NbPieces, DateAcqui)
VALUES
(2, 5, TO_DATE('2021-06-01', 'YYYY-MM-DD'));

-- Table Colocataire
INSERT INTO Colocataire (Id_Locataire, Id_Locataire_1)
VALUES
('L001', 'L002');

-- Table Diagnostic
INSERT INTO Diagnostic (Id_Diagnostic, Type_Diag, DateDiagnostic, Id_Louable)
VALUES
(1, 'DPE', TO_DATE('2023-01-01', 'YYYY-MM-DD'), 1),
(2, 'Gaz', TO_DATE('2024-01-01', 'YYYY-MM-DD'), 2);

-- Table Quittances
INSERT INTO Quittances (Id_Quittances, DateDerPaiement, MontantLoyer, MontantProvision, Id_Locataire, Id_Contrat_de_location)
VALUES
(1, TO_DATE('2024-06-01', 'YYYY-MM-DD'), 1200.000, 200.000, 'L001', 1),
(2, TO_DATE('2025-01-01', 'YYYY-MM-DD'), 1500.000, 250.000, 'L002', 2);

-- Table Indexer
INSERT INTO Indexer (Id_Index_Compteur, Id_Immeuble)
VALUES
(1, 1),
(2, 2);

-- Table Associer
INSERT INTO Associer (Id_Louable, Id_Index_Compteur)
VALUES
(1, 1),
(2, 2);

-- Table Apparaitre
INSERT INTO Apparaitre (Id_Charge, Id_Index_Compteur)
VALUES
(1, 1),
(2, 2);

-- Table Correspondre
INSERT INTO Correspondre (Id_Locataire, Id_Contrat_de_location)
VALUES
(1, 1),
(2, 2);
