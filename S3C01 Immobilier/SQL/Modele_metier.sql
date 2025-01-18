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
        SELECT DateRegularisation INTO v_date_derniere_regularisation
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
    
    UPDATE Associer SET DateReleve = SYSDATE WHERE Id_Louable = P_Id_Louable AND Id_Index_Compteur = v_IndexCompteur;
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
------------- CALCUL LA CONSOMMATION D'UN SEUL BIEN A PARTIR DU ---------------
---------- MONTANT DE LA DERNIERE FACTURE DE L'IMMEUBLE, PRENANT EN -----------
-------------------------- COMPTE LA QUOTITE ----------------------------------
-------------------------------------------------------------------------------
-- Fonction pour calculer le prix total de la consommation pour un logement
-- en prenant en compte la quotité du bien pour un type de compteur spécifique
CREATE OR REPLACE FUNCTION PrixConsoLogementQuotite(
    p_Id_Bien IN Bien.Id_Bien%TYPE,
    p_Id_Compteur IN Compteur.Id_Compteur%TYPE
) RETURN NUMBER
IS
    -- Déclaration des variables
    v_ConsommationLogement NUMBER := 0;  -- Initialiser à 0 en cas d'absence de facture
    v_Quotite NUMBER(20, 10);
    v_TypeQuotite VARCHAR2(50);
    v_TypeCompteur COMPTEUR.typeComp%TYPE;
    v_PrixConsommationTotale NUMBER;

BEGIN
    -- Récupérer le type de compteur
    SELECT typeComp 
    INTO v_TypeCompteur
    FROM Compteur 
    WHERE Id_Compteur = p_Id_Compteur;

    -- Récupérer la quotité du bien
    SELECT pourcentage, type_quotite
    INTO v_Quotite, v_TypeQuotite
    FROM Quotter
    WHERE Id_Bien = p_Id_Bien
    AND type_quotite = v_TypeCompteur;

    -- Vérifier si la quotité a été trouvée
    IF v_Quotite IS NULL THEN
        -- Si non, générer une erreur personnalisée
        RAISE_APPLICATION_ERROR(-20001, 'Quotité non trouvée pour le bien donné et le type de compteur spécifié.');
    END IF;

    -- Récupérer le montant de la dernière facture de type de compteur pour l'immeuble
    BEGIN
        SELECT NVL(SUM(montant), 0)  -- Utiliser NVL pour remplacer NULL par 0
        INTO v_PrixConsommationTotale
        FROM Facture
        WHERE Id_Immeuble = (SELECT Id_Immeuble FROM Bien WHERE Id_Bien = p_Id_Bien)
          AND designation = v_TypeCompteur
          AND date_emission > (SELECT date_derniere_reg FROM Louer WHERE Id_Bien = p_Id_Bien);

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Gérer le cas où aucune donnée n'est trouvée
            DBMS_OUTPUT.PUT_LINE('Aucune facture trouvée pour le type de compteur spécifié.');
            RETURN 0;
        WHEN OTHERS THEN
            -- Gérer les autres exceptions
            RAISE_APPLICATION_ERROR(-20004, 'Erreur lors de la récupération du montant de la facture.');
    END;

    -- Vérifier si la consommation est nulle ou négative
    IF v_PrixConsommationTotale < 0 THEN
        -- Si oui, générer une erreur personnalisée
        RAISE_APPLICATION_ERROR(-20005, 'Montant de la facture invalide.');
    END IF;

    -- Calculer le prix de la consommation en prenant en compte la quotité
    v_ConsommationLogement := v_PrixConsommationTotale * (v_Quotite / 100);

    -- Retourner le prix de la consommation
    RETURN v_ConsommationLogement;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Gérer le cas où aucune donnée trouvée pour le compteur spécifié
        RETURN 0; -- On en a besoin car cette fonction est appelée dans les fonctions suivantes
        RAISE_APPLICATION_ERROR(-20002, 'Aucune donnée trouvée.');
    WHEN OTHERS THEN
        -- Gérer les autres exceptions
        RAISE_APPLICATION_ERROR(-20006, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END;
/

---------------------- Bloc anonyme pour tester la fonction—---------------------------
DECLARE
    v_PrixConsommation NUMBER;
BEGIN
    -- Appeler la fonction CalculerConsommationBien
    v_PrixConsommation := PrixConsoLogementQuotite('B004', 'ComptImmCompans_EauGeneral');

    IF v_PrixConsommation IS NOT NULL THEN
        DBMS_OUTPUT.PUT_LINE('PrixConsoLogementQuotite : ' || v_PrixConsommation);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Erreur');
    END IF;
END;
/


-------------------------------------------------------------------------------
-------------- CALCUL LA CONSOMMATION D'UN SEUL BIEN A PARTIR DU --------------
--------------------- COMPTEUR, EN FONCTION DE SON TYPE -----------------------
-------------------------------------------------------------------------------
-- Fonction pour calculer la consommation d'un bien à partir d'un compteur
-- en focntion de son type (propre au bine, ou général)
CREATE OR REPLACE FUNCTION CalculerConsoBien(
    p_Id_immeuble IN Compteur.Id_immeuble%TYPE,
    p_Id_Bien IN Compteur.Id_Bien%TYPE,
    p_Id_Compteur IN Compteur.Id_Compteur%TYPE
) RETURN NUMBER
IS
    -- Déclaration des variables
    v_Id_Bien_compteur Compteur.Id_Bien%TYPE;
    v_Id_immeuble_compteur Compteur.Id_Immeuble%TYPE;
BEGIN
    -- Vérifier si l'immeuble existe
    SELECT Id_Immeuble
    INTO v_Id_immeuble_compteur
    FROM Compteur
    WHERE Id_Compteur = p_Id_Compteur;

    -- Vérifier si le bien existe dans l'immeuble spécifié
    SELECT Id_Bien
    INTO v_Id_Bien_compteur
    FROM Compteur
    WHERE Id_Compteur = p_Id_Compteur;

    -- Gérer le cas où le bien n'appartient pas à l'immeuble indiqué
    IF v_Id_immeuble_compteur IS NOT NULL AND v_Id_immeuble_compteur <> p_Id_immeuble THEN
        -- Si oui, générer une erreur personnalisée
        RAISE_APPLICATION_ERROR(-20012, 'Le bien spécifié n''appartient pas à l''immeuble indiqué.');
    END IF;

    -- Vérifier si le compteur est propre (lié à un bien spécifique)
    IF v_Id_Bien_compteur IS NOT NULL AND v_Id_immeuble_compteur IS NULL THEN
        -- Appeler la fonction PrixConsoLogement
        RETURN PrixConsoLogement(p_Id_Compteur);
    -- Si le compteur est général (lié à un immeuble)
    ELSIF v_Id_Bien_compteur IS NULL AND v_Id_immeuble_compteur IS NOT NULL THEN
        -- Appeler la fonction PrixConsoLogementQuotite
        RETURN PrixConsoLogementQuotite(p_Id_Bien, p_Id_Compteur);
    END IF;

    -- Gérer les autres exceptions
    RAISE_APPLICATION_ERROR(-20007, 'Erreur lors du calcul de la consommation.');

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Gérer le cas où aucune donnée trouvée pour le compteur spécifié
        RETURN 0; -- On en a besoin car cette fonction est appelée dans les fontions prochaines
    WHEN OTHERS THEN
        -- Gérer les autres exceptions en affichant le message d'erreur d'origine
        RAISE_APPLICATION_ERROR(-20009, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END;
/


----------------------- Bloc anonyme pour tester la fonction—---------------------------
DECLARE
    v_PrixConsommation NUMBER(15, 2);
BEGIN
    -- Appeler la fonction CalculerConsommationBien
    v_PrixConsommation := CalculerConsoBien('Compans_Caffarelli_8','B004', 'ComptImmCompans_EauGeneral');

    IF v_PrixConsommation IS NOT NULL THEN
        DBMS_OUTPUT.PUT_LINE('CalculerConsoBien : ' || v_PrixConsommation);
    ELSE
        DBMS_OUTPUT.PUT_LINE('Erreur');
    END IF;
END;
/

-------------------------------------------------------------------------------
-------------- CALCUL LA CONSOMMATION REELLEES DES CHARGES  ------------------
--------------------- (eau, gaz, electricite) --------------------------------
------------------EN UTILISANT LES INDEX DES COMPTEURS-------------------------
-------------------------------------------------------------------------------
-- Fonction pour calculer la somme totale des consommations réelles
-- liées à un immeuble ou à un bien spécifique
CREATE OR REPLACE FUNCTION TOTALChargesReelles (
    p_Id_immeuble IN Bien.Id_Immeuble%TYPE DEFAULT NULL,
    p_Id_Bien IN Bien.Id_Bien%TYPE DEFAULT NULL
) RETURN NUMBER
IS
    -- Variable pour stocker la somme totale des consommations
    v_TotalConsommation NUMBER := 0;

    -- Déclarer les variables pour stocker les résultats des requêtes
    v_Id_Compteur Compteur.Id_Compteur%TYPE;
    v_ConsommationCompteur NUMBER;

    -- Curseurs pour récupérer les compteurs liés à l'immeuble ou au bien
    CURSOR cur_Compteurs IS
        SELECT Id_Compteur
        FROM Compteur
        WHERE (Id_Immeuble = p_Id_immeuble AND Id_Bien IS NULL)
           OR (Id_Bien = p_Id_Bien AND Id_Immeuble IS NULL);

BEGIN
    -- Boucle à travers les compteurs sélectionnés
    FOR rec_Compteur IN cur_Compteurs LOOP
        -- Appeler la fonction CalculerConsoBien pour chaque compteur
        v_ConsommationCompteur := CalculerConsoBien(p_Id_immeuble, p_Id_Bien, rec_Compteur.Id_Compteur);
        
        -- Ajouter la consommation du compteur à la somme totale (en tenant compte des valeurs nulles)
        v_TotalConsommation := v_TotalConsommation + COALESCE(v_ConsommationCompteur, 0);
    END LOOP;

    -- Retourner la somme totale des consommations
    RETURN v_TotalConsommation;

EXCEPTION
    WHEN OTHERS THEN
        -- Gérer les autres exceptions
        DBMS_OUTPUT.PUT_LINE('Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END TOTALChargesReelles;
/


----------------------- Bloc anonyme pour tester la fonction—---------------------------

DECLARE
    v_TotalConsommation NUMBER;
BEGIN
    -- Appel de la fonction pour un immeuble spécifique (remplacez l'ID de l'immeuble)
    v_TotalConsommation := TOTALChargesReelles('Compans_Caffarelli_8', 'B004');

    -- Affichage du résultat
    DBMS_OUTPUT.PUT_LINE('Total des charges réelles pour l''immeuble : ' || v_TotalConsommation);
END;
/


-------------------------------------------
----- SOMMES TOTAL PROVISIONS -------------
-- en fonction de date jour - date départ
-------------------------------------------
-- Fonction pour calculer le total des provisions pour un bien loué spécifié
-- Elle utilise la différence en mois entre la date actuelle et la date de la dernière régularisation
-- multipliée par le montant mensuel de la provision
CREATE OR REPLACE FUNCTION totalProvisions (
    p_id_Bien IN LOUER.Id_Bien%type
) RETURN NUMBER
IS
    -- Déclaration de la variable pour stocker le total des provisions
    v_total_provisions NUMBER(15,2) := 0;
    BEGIN
        -- Calcul du total des provisions pour le locataire spécifié
        SELECT NVL(SUM((MONTHS_BETWEEN(SYSDATE, date_derniere_reg) * provision_chargeMens_TTC)), 0)
        INTO v_total_provisions
        FROM Louer
        WHERE Id_Bien = p_id_Bien;
        
        -- Retourner le total des provisions calculé
        RETURN v_total_provisions;
        
        EXCEPTION
        WHEN NO_DATA_FOUND THEN
            -- Gérer le cas où aucune donnée n'est trouvée
            DBMS_OUTPUT.PUT_LINE('Aucune donnée trouvée pour le bien spécifié.');
            RETURN 0;
        WHEN OTHERS THEN
            -- Gérer les autres exceptions
            RAISE_APPLICATION_ERROR(-20001, 'Une erreur s''est produite : ' || SQLERRM);
            RETURN NULL;
END totalProvisions;
/

---------------------- Bloc anonyme pour tester la fonction—---------------------------
DECLARE
    v_result NUMBER(15,2);
BEGIN
    -- Appel de la fonction totalProvisions avec un ID de locataire spécifique
    v_result := totalProvisions('B004');

    -- Affichage du résultat
    DBMS_OUTPUT.PUT_LINE('Total des provisions : ' || v_result);
END;
/


-------------------------------------
-- SOMMES TOTAL ORDURES MENAGERES ---
-------------------------------------
-------------------------------------
-- Fonction pour calculer le total des charges réelles liées aux ordures ménagères pour un bien loué spécifié
-- Elle récupère la somme des montants des factures émises entre la date de la dernière régularisation et la date actuelle
-- pour le bien spécifié, avec la désignation 'Ordures ménagères'.
CREATE OR REPLACE FUNCTION totalOrduresMenageres (
    p_Id_Bien IN LOUER.Id_Bien%type
) RETURN NUMBER
IS
    v_total_charges NUMBER := 0; -- Variable pour stocker le total des charges réelles
BEGIN
    -- Calcul du total des charges réelles pour le locataire spécifié
    SELECT NVL(SUM(f.montant), 0)
    INTO v_total_charges
    FROM FACTURE f, Louer l
    WHERE p_Id_Bien = l.Id_Bien
    AND l.Id_Bien = f.Id_Bien
    AND f.date_emission BETWEEN l.date_derniere_reg AND SYSDATE 
    AND f.designation IN ('Ordures ménagères');
    
    -- Retourner le total des charges réelles calculé
    RETURN v_total_charges;
    
    EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Gérer le cas où aucune donnée n'est trouvée
        DBMS_OUTPUT.PUT_LINE('Aucune donnée trouvée pour le bien spécifié.');
        RETURN 0;
    WHEN OTHERS THEN
        -- Gérer les autres exceptions
        RAISE_APPLICATION_ERROR(-20001, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
END totalOrduresMenageres;
/

----------------------- Bloc anonyme pour tester la fonction—---------------------------
DECLARE
    v_result NUMBER;
BEGIN
    -- Appel de la fonction totalOrduresMenageres avec un ID de locataire spécifique
    v_result := totalOrduresMenageres('B010');
    
    -- Affichage du résultat
    DBMS_OUTPUT.PUT_LINE('Total des charges réelles : ' || v_result);
END;
/

----------------------------------
-------- LOYERS RESTANTS DU ------
-- prendre tous types de charges --
----------------------------------
-- Fonction pour calculer le montant restant dû pour les loyers réels d'un bien loué spécifié
-- Elle récupère le total des loyers réels émis entre la date de la dernière régularisation et la date actuelle,
-- puis soustrait le total des loyers réellement payés sur la même période
CREATE OR REPLACE FUNCTION restantDuLoyers (
    p_Id_Bien IN LOUER.Id_Bien%type
) RETURN NUMBER
IS
    v_totalLoyerReels NUMBER := 0;
    v_totalLoyerPayes NUMBER := 0;
BEGIN
    -- Calcul du total des loyers reels pour le locataire spécifié
    SELECT NVL(SUM(f.montant), 0)
    INTO v_totalLoyerReels
    FROM FACTURE f, Louer l
    WHERE p_Id_Bien = l.Id_Bien
    AND l.Id_Bien = f.Id_Bien
    AND f.date_emission BETWEEN l.date_derniere_reg AND SYSDATE 
    AND f.designation IN ('Loyer');
    
    -- Calcul du total des loyers payes pour le locataire spécifié
    SELECT NVL(SUM(f.montant_reel_paye), 0)
    INTO v_totalLoyerPayes
    FROM FACTURE f, Louer l
    WHERE p_Id_Bien = l.Id_Bien
    AND l.Id_Bien = f.Id_Bien
    AND f.date_emission BETWEEN l.date_derniere_reg AND SYSDATE 
    AND f.designation IN ('Loyer');
    
    -- Retourner le total des charges réelles calculé
    RETURN v_totalLoyerReels - v_totalLoyerPayes;
    
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Gérer le cas où aucune donnée n'est trouvée
        DBMS_OUTPUT.PUT_LINE('Aucune donnée trouvée pour le bien spécifié.');
        RETURN 0;
    WHEN OTHERS THEN
        -- Gérer les autres exceptions
        RAISE_APPLICATION_ERROR(-20001, 'Une erreur s''est produite : ' || SQLERRM);
        RETURN NULL;
        
END restantDuLoyers;
/

----------------------- Bloc anonyme pour tester la fonction—---------------------------
DECLARE
    v_result NUMBER;
BEGIN
    -- Appel de la fonction totalChargesRéelles avec un ID de locataire spécifique
    v_result := restantDuLoyers('B005');
    
    -- Affichage du résultat
    DBMS_OUTPUT.PUT_LINE('restantDuLoyers : ' || v_result);
END;
/


--------------------------------------------
------------- CALCUL TRAVAUX ---------------
--------------------------------------------
-- Fonction pour calculer la somme totale des travaux pour un bien spécifié et son immeuble associé
-- Elle récupère le total des travaux émis pour le bien spécifié et l'immeuble associé,
-- puis retourne la somme des deux
CREATE OR REPLACE FUNCTION totalDesTravauxBienEtImmeuble(
    p_Id_Bien IN BIEN.Id_Bien%TYPE
)
RETURN NUMBER 
IS 
    v_total_bien NUMBER := 0;       -- Initialisation de la somme des travaux pour le bien
    v_total_immeuble NUMBER := 0;   -- Initialisation de la somme des travaux pour l'immeuble
    v_id_immeuble BIEN.Id_Immeuble%TYPE;  -- Variable pour stocker l'ID de l'immeuble associé au bien
BEGIN 
    -- Calculer la somme des travaux pour le bien
    SELECT COALESCE(SUM(montant), 0) INTO v_total_bien
    FROM facture f
    WHERE f.id_bien IS NOT NULL
    AND f.id_bien = p_Id_Bien
    AND f.designation = 'Travaux';

    -- Rechercher l'immeuble où se trouve le bien 
    SELECT ID_Immeuble INTO v_id_immeuble FROM BIEN WHERE Id_Bien = p_Id_Bien; 
    
    -- Calculer la somme des travaux pour l'immeuble
    SELECT COALESCE(SUM(montant), 0) INTO v_total_immeuble
    FROM facture f
    WHERE f.id_immeuble IS NOT NULL
    AND f.id_immeuble = v_id_immeuble
    AND f.designation = 'Travaux';

    -- Retourner la somme totale des travaux pour le bien et son immeuble
    RETURN v_total_bien + v_total_immeuble;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        --- Gérer le cas où aucune donnée n'est trouvée
        DBMS_OUTPUT.PUT_LINE('Aucune donnée trouvée pour le bien spécifié.');
        RETURN 0;
    WHEN OTHERS THEN
        RETURN NULL;
END;
/

----------------------- Bloc anonyme pour tester la fonction—---------------------------
DECLARE
    v_result NUMBER;
    v_id_bien BIEN.Id_Bien%TYPE := 'B009';
BEGIN
    v_result := totalDesTravauxBienEtImmeuble(p_Id_Bien => v_id_bien);
    DBMS_OUTPUT.PUT_LINE('Le total des travaux est : ' || TO_CHAR(v_result));
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
END;
/

---------------------------------------------------------
------------- CALCUL TRAVAUX IMPUTABLES ---------------
---------------------------------------------------------
-- Fonction pour calculer la somme totale des travaux imputables au locataire pour un bien spécifié
-- Elle récupère le total des travaux émis pour le bien spécifié qui sont imputables au locataire,
-- puis retourne cette somme
CREATE OR REPLACE FUNCTION totalTravauxImputableLocataire(
    p_Id_Bien IN BIEN.Id_Bien%TYPE
)
RETURN NUMBER 
IS 
    v_total_bien NUMBER := 0;       
BEGIN 
    -- Calculer la somme des travaux imputables au locataire pour le bien
    SELECT COALESCE(SUM(montant), 0) INTO v_total_bien
    FROM facture f
    WHERE f.id_bien IS NOT NULL
    AND f.id_bien = p_Id_Bien
    AND f.imputable_locataire = 1
    AND f.designation = 'Travaux';
    
    -- Retourner la somme totale des travaux imputables au locataire pour le bien
    RETURN v_total_bien ;

EXCEPTION
    WHEN NO_DATA_FOUND THEN
        -- Gérer le cas où aucune donnée n'est trouvée
        DBMS_OUTPUT.PUT_LINE('Aucune donnée trouvée pour le bien spécifié.');
        RETURN 0;
    WHEN OTHERS THEN
        -- Gérer les autres exceptions
        RETURN NULL;
END;
/

----------------------- Bloc anonyme pour tester la fonction—---------------------------
DECLARE
    v_result NUMBER;
    v_id_bien BIEN.Id_Bien%TYPE := '8-RC'; -- Remplacez 'B009' par un ID existant
BEGIN
    v_result := totalTravauxImputableLocataire(p_Id_Bien => v_id_bien);
    DBMS_OUTPUT.PUT_LINE('Le total des travaux imputables au locataire est : ' || TO_CHAR(v_result));
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Erreur : ' || SQLERRM);
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


----------------------- Bloc anonyme pour tester la fonction ---------------------------
DECLARE
    v_result NUMBER;
BEGIN
    -- Appel de la fonction totalProvisions avec un ID du bien spécifique
    v_result := calculerRegularisationCharges('BienD', 'Roy_Anthony', '05/02/23');

    -- Affichage du résultat
    DBMS_OUTPUT.PUT_LINE('Total des charges régularisées : ' || v_result);
END;
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


----------------------- Bloc anonyme pour tester la fonction ---------------------------
DECLARE
    v_result NUMBER;
BEGIN
    -- Appel de la fonction totalProvisions avec un ID du bien spécifique
    v_result := calculerSoldeDeToutCompte('BienA', 'Roy_Anthony', '05/02/23');

    -- Affichage du résultat
    DBMS_OUTPUT.PUT_LINE('calculerSoldeDeToutCompte : ' || v_result);
END;
/

