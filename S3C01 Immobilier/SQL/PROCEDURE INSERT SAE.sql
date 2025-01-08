-------------------------- LOCATAIRE -------------------------------------
create or replace procedure Insert_Locataire(p_Id_Locataire VARCHAR2, p_Nom VARCHAR2, p_Prenom VARCHAR2, p_Mail VARCHAR2, p_Telephone VARCHAR2, p_DateNais DATE, p_DateDépart DATE)
    as 
    begin
        insert into Locataire (Id_Locataire, Nom, Prenom, Mail, Telephone, DateNais, DateDépart) values (p_Id_Locataire, p_Nom, p_Prenom, p_Mail, p_Telephone, p_DateNais, p_DateDépart);
    end;
/

---------------------- IMMEUBLE ----------------------
create or replace procedure Insert_Immeuble(p_Id_Immeuble INT, p_Adresse VARCHAR2, p_NbLogements INT, p_TypeImmeuble VARCHAR2) 
    as
    begin
        insert into Immeuble (Id_Immeuble, Adresse, NbLogements, TypeImmeuble) values (p_Id_Immeuble, p_Adresse, p_NbLogements, p_TypeImmeuble);
    end;
/

-------------------------- LOGEMENT -------------------------------------
create or replace procedure Insert_Logement(p_Id_Louable INT, p_NbPieces INT, p_DateAcqui DATE) 
    as 
    begin
        insert into Logement (Id_Louable, NbPieces, DateAcqui) values (p_Id_Louable, p_NbPieces, p_DateAcqui);
    end;
/

-------------------------- GARAGE -------------------------------------
create or replace procedure Insert_Garage(p_Id_Louable INT, p_DateAcqui DATE)
    as
    begin 
        insert into Garage (Id_Louable, DateAcqui) values (p_Id_Louable, p_DateAcqui);
    end;
/

---------------------- INDEX COMPTEUR ----------------------
create or replace procedure Insert_Index_Compteur(
    p_Id_Index_Compteur INT,
    p_TypeCompteur VARCHAR2,
    p_ValeurCourante NUMBER,
    p_AncienneValeur NUMBER,
    p_DateReleve DATE
) AS
BEGIN
    INSERT INTO Index_Compteur (Id_Index_Compteur, TypeCompteur, ValeurCourante, AncienneValeur, DateReleve)
    VALUES (p_Id_Index_Compteur, p_TypeCompteur, p_ValeurCourante, p_AncienneValeur, p_DateReleve);
END;
/

---------------------- QUITTANCES ----------------------
create or replace procedure Insert_Quittances(
    p_Id_Quittances INT,
    p_DateDerPaiement DATE,
    p_MontantLoyer NUMBER,
    p_MontantProvision NUMBER,
    p_Id_Locataire VARCHAR2,
    p_Id_Contrat_de_location INT
) AS
BEGIN
    INSERT INTO Quittances (Id_Quittances, DateDerPaiement, MontantLoyer, MontantProvision, Id_Locataire, Id_Contrat_de_location)
    VALUES (p_Id_Quittances, p_DateDerPaiement, p_MontantLoyer, p_MontantProvision, p_Id_Locataire, p_Id_Contrat_de_location);
END;
/

---------------------- DIAGNOSTIC ----------------------
create or replace procedure Insert_Diagnostic(
    p_Id_Diagnostic INT,
    p_Type_Diag VARCHAR2,
    p_DateDiagnostic DATE,
    p_Id_Louable INT
) AS
BEGIN
    INSERT INTO Diagnostic (Id_Diagnostic, Type_Diag, DateDiagnostic, Id_Louable)
    VALUES (p_Id_Diagnostic, p_Type_Diag, p_DateDiagnostic, p_Id_Louable);
END;
/

---------------------- COLOCATAIRE ----------------------
create or replace procedure Insert_Colocataire(
    p_Id_Locataire VARCHAR2,
    p_Id_Locataire_1 VARCHAR2
) AS
BEGIN
    INSERT INTO Colocataire (Id_Locataire, Id_Locataire_1)
    VALUES (p_Id_Locataire, p_Id_Locataire_1);
END;
/

---------------------- CHARGE ----------------------
create or replace procedure Insert_Charge(
    p_Id_Charge INT,
    p_Type_Charge VARCHAR2,
    p_Montant NUMBER,
    p_Recuperable VARCHAR2,
    p_PeriodeDebut DATE,
    p_PeriodeFin DATE,
    p_Id_Facture INT,
    p_Id_Louable INT
) AS
BEGIN
    INSERT INTO Charge (Id_Charge, Type_Charge, Montant, Recuperable, PeriodeDebut, PeriodeFin, Id_Facture, Id_Louable)
    VALUES (p_Id_Charge, p_Type_Charge, p_Montant, p_Recuperable, p_PeriodeDebut, p_PeriodeFin, p_Id_Facture, p_Id_Louable);
END;
/

---------------------- CONTRAT DE LOCATION ----------------------
create or replace procedure Insert_Contrat_de_location(
    p_Id_Contrat_de_location INT,
    p_DateDebut DATE,
    p_DateFin DATE,
    p_MontantLoyer NUMBER,
    p_ProvisionsCharges NUMBER,
    p_TypeContrat VARCHAR2,
    p_DateAnniversaire DATE,
    p_IndiceICC NUMBER,
    p_MontantCaution NUMBER,
    p_NomCaution VARCHAR2,
    p_AdresseCaution VARCHAR2,
    p_Id_Louable INT
) AS
BEGIN
    INSERT INTO Contrat_de_location (Id_Contrat_de_location, DateDebut, DateFin, MontantLoyer, ProvisionsCharges, TypeContrat, DateAnniversaire, IndiceICC, MontantCaution, NomCaution, AdresseCaution, Id_Louable)
    VALUES (p_Id_Contrat_de_location, p_DateDebut, p_DateFin, p_MontantLoyer, p_ProvisionsCharges, p_TypeContrat, p_DateAnniversaire, p_IndiceICC, p_MontantCaution, p_NomCaution, p_AdresseCaution, p_Id_Louable);
END;
/

---------------------- FACTURE ----------------------
create or replace procedure Insert_Facture(
    p_Id_Facture INT,
    p_Montant NUMBER,
    p_DateFacture DATE,
    p_ReferenceDevis VARCHAR2,
    p_Entreprise VARCHAR2,
    p_DatePaiement DATE,
    p_Id_Entreprise INT,
    p_Id_Louable INT
) AS
BEGIN
    INSERT INTO Facture (Id_Facture, Montant, DateFacture, ReferenceDevis, Entreprise, DatePaiement, Id_Entreprise, Id_Louable)
    VALUES (p_Id_Facture, p_Montant, p_DateFacture, p_ReferenceDevis, p_Entreprise, p_DatePaiement, p_Id_Entreprise, p_Id_Louable);
END;
/

---------------------- TAXE ----------------------
create or replace procedure Insert_Taxe(
    p_Id_Taxe INT,
    p_MontantTaxeFoncieres NUMBER,
    p_DateTaxe INT,
    p_Id_Immeuble INT
) AS
BEGIN
    INSERT INTO Taxe (Id_Taxe, MontantTaxeFoncieres, DateTaxe, Id_Immeuble)
    VALUES (p_Id_Taxe, p_MontantTaxeFoncieres, p_DateTaxe, p_Id_Immeuble);
END;
/

---------------------- LOUABLE ----------------------
create or replace procedure Insert_Louable(
    p_Id_Louable INT,
    p_Adresse VARCHAR2,
    p_Superficie NUMBER,
    p_NumeroFiscal NUMBER,
    p_Statut VARCHAR2,
    p_DateAnniversaire DATE,
    p_Id_Immeuble INT,
    p_Id_Assurance INT
) AS
BEGIN
    INSERT INTO Louable (Id_Louable, Adresse, Superficie, NumeroFiscal, Statut, DateAnniversaire, Id_Immeuble, Id_Assurance
