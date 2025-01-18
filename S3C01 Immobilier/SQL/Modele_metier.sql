---------------------------------------------------------------------------
------------- CONSOMMATION D'UN COMPTEUR POUR UN BIEN DONNÉ ---------------
---------------------------------------------------------------------------
-- Fonction pour calculer la consommation entre deux relevés pour un compteur donné
CREATE OR REPLACE FUNCTION CalculerConsommation(
    P_Id_Index_Compteur IN Index_Compteur.Id_Index_Compteur%TYPE
) RETURN NUMBER
IS
    -- Déclaration des variables
    v_Consommation NUMBER := 0;  -- Initialiser à 0 en cas d'absence de relevé
    v_NouvelIndice NUMBER;
    v_AncienIndice NUMBER;
BEGIN
    -- Récupérer l'indice actuel (le plus récent)
    SELECT ValeurCourante, AncienneValeur INTO v_NouvelIndice, v_AncienIndice
    FROM Index_Compteur
    WHERE Id_Index_Compteur = P_Id_Index_Compteur;

    -- Vérifier si l'indice précédent a été trouvé
    IF v_AncienIndice IS NULL THEN
        -- Si non, retourner 0 (pas d'erreur générée)
        RETURN 0;
    END IF;

    -- Calculer la consommation
    v_Consommation := v_NouvelIndice - v_AncienIndice;

    -- Retourner la consommation calculée
    RETURN v_Consommation;

EXCEPTION
    -- Si on ne trouve aucun relevé, on renvoie 0
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('Aucun relevé ! ');
        RETURN 0; -- On en a besoin car cette fonction est appelée dans les fontions prochaines
    WHEN TOO_MANY_ROWS THEN
        -- Gérer le cas où plusieurs relevés sont trouvés avec une erreur personnalisée
        RAISE_APPLICATION_ERROR(-20003, 'Plusieurs relevés trouvés pour le Compteur donné.');
    WHEN OTHERS THEN
        -- Gérer toutes les autres exceptions en affichant le message d'erreur d'origine
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
        RETURN NULL;
END;
/


-----------------------------------------------------------------------------------------------
------- CALCULE DU PRIX DE LA PARTIE VARIABLE D'UN COMPTEUR EN FONCTION DE SON TYPE -----------
-----------------------------------------------------------------------------------------------
-- Fonction pour calculer le coût variable de la consommation en fonction du type de compteur
-- La consommation multipliée par le prix du m3 
CREATE OR REPLACE FUNCTION PartieVariableConso(
    P_Id_Index_Compteur IN Index_Compteur.Id_Index_Compteur%TYPE
) RETURN NUMBER
IS
    -- Déclaration des variables
    v_Consommation INT;
    v_PrixConsommation NUMBER;
    v_TypeCompteur Index_Compteur.TypeCompteur%TYPE;
BEGIN
    -- Appeler la fonction CalculerConsommation pour obtenir la consommation en m3
    v_Consommation := CalculerConsommation(P_Id_Index_Compteur);

    -- Vérifier si la consommation est nulle ou négative
    IF v_Consommation = 0 THEN 
        RETURN 0;
    ELSIF v_Consommation < 0 THEN 
         -- Si oui, générer une erreur personnalisée
        RAISE_APPLICATION_ERROR(-20003, 'Consommation invalide.');
    END IF;
        
    -- Sélectionner le typeComp depuis la table COMPTEUR
    SELECT TypeCompteur INTO v_TypeCompteur 
    FROM Index_Compteur 
    WHERE Id_Index_Compteur = P_Id_Index_Compteur;
    
    -- Utiliser une structure CASE pour déterminer le prix de la consommation en fonction du typeComp
    CASE v_TypeCompteur
        WHEN 'Energie' THEN
            v_PrixConsommation := v_Consommation * 0.2276; -- Constante pour le prix du m3 d'électricité
        WHEN 'Eau' THEN
            v_PrixConsommation := v_Consommation * 3.34;   -- Constante pour le prix du m3 de l'eau
        WHEN 'Gaz' THEN
            v_PrixConsommation := v_Consommation * 1.21;   -- Constante pour le prix du m3 du gaz
        ELSE
            -- Si le type de compteur n'est pas pris en charge, générer une erreur personnalisée
            RAISE_APPLICATION_ERROR(-20004, 'Type de compteur non pris en charge.');
    END CASE;

    -- Retourner le prix de la consommation
    RETURN v_PrixConsommation;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Si on ne trouve aucun compteur, on renvoie 0
        DBMS_OUTPUT.PUT_LINE('Aucun compteur ! ');
        RETURN 0; -- On en a besoin car cette fonction est appelée dans les fontions prochaines
    WHEN OTHERS THEN
        -- Gérer toutes les autres exceptions en affichant le message d'erreur d'origine
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
        RETURN NULL;
END;
/


--------------------------------------------------------------------------------------
------------- CALCULE DU PRIX TOTAL DE LA CONSOMMATION D’UN BIEN DONNÉ ---------------
--------------------------------------------------------------------------------------
-- Fonction pour calculer le prix total de la consommation pour un logement
-- Somme de la partie variable et partie fixe (prix abonement)
CREATE OR REPLACE FUNCTION PrixConsoLogement(P_Id_Louable IN Louable.Id_Louable%TYPE)
RETURN NUMBER
IS
    -- Déclaration des variables
    v_PrixAbonnement NUMBER;
    v_nb_mois_utilisation NUMBER;
    v_derniere_date_releve DATE;
    v_date_derniere_regularisation DATE;
    v_IndexCompteur Index_compteur.Id_index_compteur%TYPE;
    v_Immeuble Immeuble.Id_Immeuble%TYPE;
    v_soluce NUMBER;
BEGIN
    -- Récupérer le prix de l'abonnement à partir de la table Compteur
    SELECT PrixAbonnement INTO v_PrixAbonnement
    FROM Associer
    WHERE Id_Louable = P_Id_Louable;

    IF v_PrixAbonnement IS NULL THEN
        SELECT PrixAbonnement INTO v_PrixAbonnement
        FROM Indexer
        WHERE Id_Immeuble = v_Immeuble;
    END IF;
    
    IF v_PrixAbonnement IS NULL THEN
        -- Si oui, générer une erreur personnalisée
        RAISE_APPLICATION_ERROR(-20006, 'Prix de l''abonnement non trouvé pour le compteur ' || P_Id_Louable);
    END IF;
    

    SELECT Id_Index_Compteur INTO v_IndexCompteur
    FROM Associer
    WHERE Id_Louable = P_Id_Louable;
    
    -- Récupérer la date de la dernière régularisation pour le bien
    BEGIN
        SELECT MAX(DateRegularisation) INTO v_date_derniere_regularisation
        FROM Associer
        WHERE Id_Louable = P_Id_Louable;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20009, 'Aucune date de dernière régularisation trouvée pour le bien ' || P_Id_Louable);
    END;
    
    -- Récupérer la date actuelle (la plus récente)
    BEGIN
        SELECT DateReleve INTO v_derniere_date_releve
        FROM Associer
        WHERE Id_Index_Compteur = v_IndexCompteur AND Id_Louable = P_Id_Louable
            AND DateReleve > v_date_derniere_regularisation
        ORDER BY DateReleve DESC
        FETCH FIRST 1 ROWS ONLY;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RAISE_APPLICATION_ERROR(-20010, 'Aucune date de relevé trouvée pour le compteur ' || P_Id_Louable);
    END;
    
    -- Récupérer le nombre de mois d'occupation
    -- Utilisation de la différence entre les dates pour obtenir le nombre de mois
    v_nb_mois_utilisation := MONTHS_BETWEEN(v_derniere_date_releve, SYSDATE);

    -- Gérer le cas où la différence entre les dates est nulle ou négative
    IF v_nb_mois_utilisation <= 0 THEN
        RAISE_APPLICATION_ERROR(-20012, 'La période d''occupation du bien est invalide.');
    END IF;
    

    v_soluce := v_PrixAbonnement * v_nb_mois_utilisation + PartieVariableConso(v_IndexCompteur);
    RETURN v_soluce;

EXCEPTION
    WHEN OTHERS THEN
        RETURN 0; -- On en a besoin car cette fonction est appelée dans les fontions prochaines
        -- Gérer les autres exceptions
        RAISE_APPLICATION_ERROR(-20013, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END PrixConsoLogement;
/

-------------------------------------------------------------------------------
-------------- CALCUL LA CONSOMMATION DES CHARGES PAR IMMEUBLE  ---------------
--------------------- (eau, gaz, electricite) --------------------------------
-------------------------------------------------------------------------------
CREATE OR REPLACE FUNCTION TOTALChargesImmeuble (
    p_Id_immeuble IN Immeuble.Id_Immeuble%TYPE
) RETURN NUMBER
IS
    -- Variable pour stocker la somme totale des consommations
    v_TotalConsommation NUMBER := 0;

    -- Déclarer les variables pour stocker les résultats des requêtes
    v_ConsommationLouable NUMBER;

    -- Curseurs pour récupérer les compteurs liés à l'immeuble ou au bien
    CURSOR cur_Immeuble IS
        SELECT Id_Louable
        FROM Louable
        WHERE Id_Immeuble = p_Id_immeuble;

BEGIN
    -- Boucle à travers les compteurs sélectionnés
    FOR rec_Immeuble IN cur_Immeuble LOOP
        -- Appeler la fonction CalculerConsoBien pour chaque compteur
        v_ConsommationLouable := PrixConsoLogement(rec_Immeuble.Id_Louable);
        
        -- Ajouter la consommation du compteur à la somme totale (en tenant compte des valeurs nulles)
        v_TotalConsommation := v_ConsommationLouable;
    END LOOP;

    -- Retourner la somme totale des consommations
    RETURN v_TotalConsommation;

EXCEPTION
    WHEN OTHERS THEN
        -- Gérer les autres exceptions
        DBMS_OUTPUT.PUT_LINE('Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END TOTALChargesImmeuble;
/



-------------------------------------------
----- SOMMES TOTAL PROVISIONS -------------
-- en fonction de date jour - date départ
-------------------------------------------
-- Fonction pour calculer le total des provisions pour un bien loué spécifié
-- Elle utilise la différence en mois entre la date actuelle et la date de la dernière régularisation
-- multipliée par le montant mensuel de la provision
CREATE OR REPLACE FUNCTION totalProvisions (
    p_id_Louable IN Louable.Id_Louable%TYPE
) RETURN NUMBER
IS
    -- Variable pour stocker le total des provisions
    v_total_provisions NUMBER(15, 2) := 0;
    -- Variable pour stocker la date du dernier relev�
    v_date_derniere_releve DATE;
BEGIN
    -- R�cup�rer la date du dernier relev� pour le bien lou� sp�cifi�
    SELECT MAX(DateReleve)
    INTO v_date_derniere_releve
    FROM Associer
    WHERE Id_Louable = p_id_Louable;

    -- Calcul du total des provisions bas�es sur la date du dernier relev�
    SELECT NVL(SUM((MONTHS_BETWEEN(SYSDATE, v_date_derniere_releve) * ProvisionsCharges)), 0)
    INTO v_total_provisions
    FROM Contrat_de_location
    WHERE Id_Louable = p_id_Louable
      AND (DateFin IS NULL OR DateFin > SYSDATE);

    -- Retourner le total des provisions calcul�es
    RETURN v_total_provisions;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Aucun relev� ou contrat trouv� pour le bien lou� sp�cifi�
        DBMS_OUTPUT.PUT_LINE('Aucune donn�e trouv�e pour le bien lou� sp�cifi�.');
        RETURN 0;
    WHEN OTHERS THEN
        -- G�rer toute autre exception
        RAISE_APPLICATION_ERROR(-20001, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END totalProvisions;
/

-------------------------------------
-- SOMMES TOTAL ORDURES MENAGERES ---
-------------------------------------
-------------------------------------
-- Fonction pour calculer le total des charges réelles liées aux ordures ménagères pour un bien loué spécifié
-- Elle récupère la somme des montants des factures émises entre la date de la dernière régularisation et la date actuelle
-- pour le bien spécifié, avec la désignation 'Ordures ménagères'.
CREATE OR REPLACE FUNCTION totalOrduresMenageres (
    p_Id_Louable IN Louable.Id_Louable%TYPE
) RETURN NUMBER
IS
    v_total_charges NUMBER := 0; -- Variable pour stocker le total des charges
    v_superficie_appt NUMBER(10, 3); -- Superficie du bien louable
BEGIN
    -- R�cup�rer la superficie du bien louable
    SELECT Superficie
    INTO v_superficie_appt
    FROM Louable
    WHERE Id_Louable = p_Id_Louable;

    -- Calcul du total des charges d'ordures m�nag�res
    SELECT NVL(SUM(f.Montant * (v_superficie_appt / (
                SELECT SUM(L.Superficie)
                FROM Louable L
                WHERE L.Id_Immeuble = (SELECT Id_Immeuble FROM Louable WHERE Id_Louable = p_Id_Louable)
            ))), 0)
    INTO v_total_charges
    FROM Facture f
    WHERE f.Id_Louable = p_Id_Louable
      AND f.DateFacture BETWEEN (
            SELECT MAX(DateRegularisation) FROM Associer WHERE Id_Louable = p_Id_Louable
        ) AND SYSDATE
      AND f.ReferenceDevis = 'Ordures m�nag�res'; -- D�signation sp�cifique pour les ordures m�nag�res

    -- Retourner le total des charges calcul�es
    RETURN v_total_charges;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- G�rer le cas o� aucune donn�e n'est trouv�e
        DBMS_OUTPUT.PUT_LINE('Aucune donn�e trouv�e pour le bien lou� sp�cifi�.');
        RETURN 0;
    WHEN OTHERS THEN
        -- G�rer les autres exceptions
        RAISE_APPLICATION_ERROR(-20001, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END totalOrduresMenageres;
/



----------------------------------
-------- LOYERS DE L'IMMEUBLE------
----------------------------------
CREATE OR REPLACE FUNCTION totalLoyersPayes (
    p_Id_Immeuble IN Immeuble.Id_Immeuble%TYPE
) RETURN NUMBER
IS
    -- Variable pour stocker le total des loyers pay�s
    v_totalLoyersPayes NUMBER := 0;
BEGIN
    -- Calcul du total des loyers pay�s pour tous les logements associ�s � l'immeuble
    SELECT NVL(SUM(q.MontantLoyer), 0)
    INTO v_totalLoyersPayes
    FROM Quittances q
    JOIN Contrat_de_location cdl ON q.Id_Contrat_de_location = cdl.Id_Contrat_de_location
    JOIN Louable l ON cdl.Id_Louable = l.Id_Louable
    WHERE l.Id_Immeuble = p_Id_Immeuble;

    -- Retourner le total des loyers pay�s
    RETURN v_totalLoyersPayes;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- G�rer le cas o� aucune donn�e n'est trouv�e
        DBMS_OUTPUT.PUT_LINE('Aucune donn�e trouv�e pour l''immeuble sp�cifi�.');
        RETURN 0;
    WHEN OTHERS THEN
        -- G�rer toute autre exception
        RAISE_APPLICATION_ERROR(-20001, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END totalLoyersPayes;
/


--------------------------------------------
------------- CALCUL TRAVAUX ---------------
--------------------------------------------
-- Fonction pour calculer la somme totale des travaux pour un bien spécifié et son immeuble associé
-- Elle récupère le total des travaux émis pour le bien spécifié et l'immeuble associé,
-- puis retourne la somme des deux
CREATE OR REPLACE FUNCTION totalDesTravauxBienEtImmeuble(
    p_Id_Louable IN Louable.Id_Louable%TYPE
)
RETURN NUMBER 
IS 
    v_total_bien NUMBER := 0;       -- Somme des travaux pour le bien louable
    v_total_immeuble NUMBER := 0;   -- Somme des travaux pour l'immeuble associ�
    v_id_immeuble Immeuble.Id_Immeuble%TYPE;  -- ID de l'immeuble associ� au bien louable
BEGIN 
    -- Calculer la somme des travaux pour le bien louable
    SELECT NVL(SUM(f.Montant), 0)
    INTO v_total_bien
    FROM Facture f
    WHERE f.Id_Louable IS NOT NULL
      AND f.Id_Louable = p_Id_Louable
      AND f.ReferenceDevis = 'Travaux'; -- Filtrer les factures de travaux

    -- R�cup�rer l'ID de l'immeuble associ� au bien louable
    SELECT Id_Immeuble
    INTO v_id_immeuble
    FROM Louable
    WHERE Id_Louable = p_Id_Louable;

    -- Calculer la somme des travaux pour l'immeuble
    SELECT NVL(SUM(f.Montant), 0)
    INTO v_total_immeuble
    FROM Facture f
    WHERE f.Id_Louable IS NULL -- Factures g�n�rales � l'immeuble
      AND f.Id_Entreprise IS NOT NULL -- Factures directement li�es � des travaux d'immeuble
      AND f.ReferenceDevis = 'Travaux';

    -- Retourner la somme totale des travaux
    RETURN v_total_bien + v_total_immeuble;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- G�rer le cas o� aucune donn�e n'est trouv�e
        DBMS_OUTPUT.PUT_LINE('Aucune donn�e trouv�e pour le bien sp�cifi�.');
        RETURN 0;
    WHEN OTHERS THEN
        -- G�rer d'autres exceptions
        RAISE_APPLICATION_ERROR(-20001, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END;
/


---------------------------------------------------------
------------- CALCUL FACTURES IMPAYES ---------------
---------------------------------------------------------
CREATE OR REPLACE FUNCTION totalFactureImpayees RETURN NUMBER IS
    v_somme NUMBER := 0;  -- Variable pour stocker la somme des factures non pay�es
BEGIN
    -- Calcule la somme des factures non pay�es
    SELECT SUM(Montant) 
    INTO v_somme
    FROM Facture
    WHERE DatePaiement IS NULL;
    
    -- Retourne le r�sultat
    RETURN v_somme;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Si aucune facture non pay�e n'est trouv�e, retourner 0
        RETURN 0;
    WHEN OTHERS THEN
        -- G�rer les autres exceptions
        RAISE;
END;
/

----------------------------------------------
---------- RECUPERATION LOYERS IMPAYES --------
----------------------------------------------
CREATE OR REPLACE FUNCTION GenererDatesLoyers(Id_Louable INT) RETURN SYS_REFCURSOR IS
    v_cursor SYS_REFCURSOR;
BEGIN
    -- Cr�ation du curseur pour g�n�rer les dates mensuelles
    OPEN v_cursor FOR
    SELECT TRUNC(ADD_MONTHS(l.DateAcquisition, LEVEL - 1), 'MM') AS Date_Loyer
    FROM Louable l
    WHERE l.Id_Louable = Id_Louable
    CONNECT BY PRIOR l.Id_Louable = l.Id_Louable
        AND ADD_MONTHS(l.DateAcquisition, LEVEL - 1) <= SYSDATE
    AND LEVEL <= MONTHS_BETWEEN(SYSDATE, l.DateAcquisition) + 1
    ORDER BY Date_Loyer;

    RETURN v_cursor;
EXCEPTION
    WHEN OTHERS THEN
        -- G�rer les exceptions
        RAISE;
END;
/

CREATE OR REPLACE FUNCTION ListerLoyersImpayees RETURN SYS_REFCURSOR IS
    v_cursor SYS_REFCURSOR;
    v_dates_cursor SYS_REFCURSOR;
    v_date_loyer DATE;
    v_id_louable INT;
    v_adresse VARCHAR2(50);
BEGIN
    -- Cr�ation du curseur principal pour r�cup�rer les loyers impay�s
    OPEN v_cursor FOR
    SELECT l.Id_Louable, l.Adresse
    FROM Louable l
    WHERE l.DateAcquisition <= SYSDATE
    ORDER BY l.Id_Louable;

    -- Parcours des r�sultats pour r�cup�rer les loyers impay�s
    LOOP
        FETCH v_cursor INTO v_id_louable, v_adresse;
        EXIT WHEN v_cursor%NOTFOUND;
        
        -- Utilisation de la fonction GenererDatesLoyers pour r�cup�rer les dates
        v_dates_cursor := GenererDatesLoyers(v_id_louable);
        
        -- Parcours des dates g�n�r�es pour v�rifier les paiements
        LOOP
            FETCH v_dates_cursor INTO v_date_loyer;
            EXIT WHEN v_dates_cursor%NOTFOUND;
            
            -- V�rifier si le paiement est manquant pour cette date
            IF NOT EXISTS (
                SELECT 1
                FROM Quittances q
                WHERE q.Id_Contrat_de_location IN (SELECT Id_Contrat_de_location FROM Correspondre WHERE Id_Locataire = q.Id_Locataire)
                AND TRUNC(q.DatePaiement, 'MM') = v_date_loyer
            ) THEN
                -- Si pas de paiement trouv�, afficher le loyer impay�
                DBMS_OUTPUT.PUT_LINE('Id_Louable: ' || v_id_louable || ', Adresse: ' || v_adresse || ', Date Loyer Impay�: ' || v_date_loyer);
            END IF;
        END LOOP;

        CLOSE v_dates_cursor;
    END LOOP;

    CLOSE v_cursor;
    RETURN NULL;  -- Aucun curseur de retour dans cette version, mais la logique peut �tre �tendue
EXCEPTION
    WHEN OTHERS THEN
        -- G�rer les exceptions
        RAISE;
END;
/








----------------------------------------------
---------- REGULARISATION DES CHARGES --------
----------------------------------------------
-- Fonction pour prendre en compte les charges réelles, les provisions et les dettes de loyers 
-- pour calculer le solde après régularisation d'un locataire pour un bien spécifié
-- Elle retourne le solde après régularisation.
CREATE OR REPLACE FUNCTION calculerRegularisationCharges (
    p_id_bien IN LOUER.id_bien%type,
    p_id_locataire IN LOUER.Id_Locataire%type,
    p_Date_Debut IN LOUER.Date_Debut%type
) RETURN NUMBER
IS
    -- Déclaration des variables locales
    v_charges_reelles NUMBER;       -- Variable pour stocker le total des charges réelles
    v_provisions NUMBER;            -- Variable pour stocker le total des provisions
    v_solde_apres NUMBER;           -- Variable pour stocker le solde après régularisation
    v_restantDuLoyers NUMBER;       -- Variable pour stocker le total des dettes de loyers
    v_idImmeuble Immeuble.Id_Immeuble%TYPE;  -- Variable pour stocker l'ID de l'immeuble associé au bien

BEGIN
    -- Récupérer l'ID de l'immeuble associé au bien
    SELECT Id_Immeuble 
    INTO v_idImmeuble
    FROM Bien
    WHERE Id_Bien = p_id_bien;

    -- Calculer les charges réelles pour le bien
    v_charges_reelles := totalOrduresMenageres(p_id_bien) + TOTALChargesReelles(v_idImmeuble, p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_charges_reelles : ' || v_charges_reelles);

    -- Calculer les provisions pour le bien
    v_provisions := totalProvisions(p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_provisions : ' || v_provisions);

    -- Calculer les dettes de loyers pour le locataire
    v_restantDuLoyers := restantDuLoyers(p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_restantDuLoyers : ' || v_restantDuLoyers);

    -- Calculer le solde après régularisation
    v_solde_apres := v_charges_reelles + v_restantDuLoyers - v_provisions;
    
    -- Mettre à jour la date de dernière régularisation dans Louer
    UPDATE Louer
    SET date_derniere_reg = SYSDATE
    WHERE Louer.Id_Bien = p_id_bien
    AND Louer.Date_Debut = p_Date_Debut
    AND Louer.Id_Locataire = p_id_locataire;
    
    -- Retourner le solde après régularisation
    RETURN v_solde_apres;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Gérer l'absence de données
        RETURN 0;
    WHEN OTHERS THEN
        -- Gérer toutes les autres exceptions
        RAISE_APPLICATION_ERROR(-20003, 'Erreur inattendue : ' || SQLERRM);
END calculerRegularisationCharges;
/

----------------------------------------------
---------- SOLDE DE TOUT COMPTE --------------
----------------------------------------------
-- Fonction pour calculer le solde de tout compte pour un locataire et un bien spécifiés
-- Elle prend en compte les charges réelles, les provisions, les travaux imputables, 
-- la caution et les dettes de loyers pour déterminer le solde final après régularisation

CREATE OR REPLACE FUNCTION calculerSoldeDeToutCompte (
    p_id_bien IN LOUER.id_bien%type,
    p_id_locataire IN LOUER.Id_Locataire%type,
    p_Date_Debut IN LOUER.Date_Debut%type
) RETURN NUMBER
IS
    -- Déclaration des variables locales
    v_charges_reelles NUMBER;            -- Variable pour stocker le total des charges réelles
    v_provisions NUMBER;                 -- Variable pour stocker le total des provisions
    v_travaux_imputables NUMBER;         -- Variable pour stocker le total des travaux imputables
    v_solde_apres NUMBER;                -- Variable pour stocker le solde après régularisation
    v_caution NUMBER;                    -- Variable pour stocker la caution associée au bien
    v_restantDuLoyers NUMBER;            -- Variable pour stocker le total des dettes de loyers
    v_idImmeuble Immeuble.ID_Immeuble%TYPE;  -- Variable pour stocker l'ID de l'immeuble associé au bien
    v_OrduresMenageres NUMBER;           -- Variable pour stocker le total des charges 'Ordures ménagères'
    v_total_charges NUMBER;              -- Variable pour stocker le total des charges réelles
    
BEGIN
    -- Récupérer la caution associée au bien
    SELECT caution_TTC 
    INTO v_caution
    FROM Louer
    WHERE Id_Bien = p_id_bien
    AND Date_Debut = p_Date_Debut
    AND Id_Locataire = p_id_locataire;
    DBMS_OUTPUT.PUT_LINE('v_caution : ' || v_caution);

    -- Récupérer l'ID de l'immeuble associé au bien
    SELECT Id_Immeuble 
    INTO v_idImmeuble
    FROM Bien
    WHERE Id_Bien = p_id_bien;

    -- Calculer les charges réelles pour le locataire
    v_OrduresMenageres := totalOrduresMenageres(p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_OrduresMenageres : ' || v_OrduresMenageres);

    v_total_charges := TOTALChargesReelles(v_idImmeuble, p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_total_charges : ' || v_total_charges);

    v_charges_reelles := v_OrduresMenageres + v_total_charges;
    DBMS_OUTPUT.PUT_LINE('v_charges_reelles : ' || v_charges_reelles);

    -- Calculer les provisions pour le locataire
    v_provisions := totalProvisions(p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_provisions : ' || v_provisions);

    -- Calculer les travaux imputables au locataire
    v_travaux_imputables := totalTravauxImputableLocataire(p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_travaux_imputables : ' || v_travaux_imputables);

    -- Calculer les dettes de loyers pour le locataire
    v_restantDuLoyers := restantDuLoyers(p_id_bien);
    DBMS_OUTPUT.PUT_LINE('v_restantDuLoyers : ' || v_restantDuLoyers);

    -- Calculer le solde après régularisation
    v_solde_apres := v_provisions - v_charges_reelles + v_caution - v_travaux_imputables - v_restantDuLoyers;
    DBMS_OUTPUT.PUT_LINE('v_solde_apres : ' || v_solde_apres);
    
    -- Mettre à jour la date de dernière régularisation dans Louer
    UPDATE Louer
    SET date_derniere_reg = SYSDATE
    WHERE Louer.Id_Bien = p_id_bien
    AND Louer.Date_Debut = p_Date_Debut
    AND Louer.Id_Locataire = p_id_locataire;
    
    -- Retourner le solde après régularisation
    RETURN v_solde_apres;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Gérer l'absence de données
        RETURN 0;
    WHEN OTHERS THEN
        -- Gérer toutes les autres exceptions
        RAISE_APPLICATION_ERROR(-20003, 'Erreur inattendue : ' || SQLERRM);
END calculerSoldeDeToutCompte;
/
