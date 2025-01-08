-------------------------- LOCATAIRE -------------------------------------

-- Déclencheur pour vérifier la date de naissance du locataire
create or replace trigger checkLocataireDateNais before insert or update on Locataire
    for each row
    begin
       if :NEW.DateNais >= SYSDATE then
          -- Si la date de naissance est postérieure ou égale à l'actuelle
          raise_application_error(-20001, 'La date de naissance ne peut pas être égale ou ultérieure à la date actuelle.'); end if;
    end;
/

-------------------------- LOGEMENT -------------------------------------

-- Déclencheur pour vérifier que la date d'acquisition du bien n'est pas ultérieure à la date actuelle
create or replace trigger checkLogementDateAcqui before insert or update on Logement
    for each row
    begin
       if :NEW.DateAcqui > SYSDATE then
          -- Si la la date d'acquisition est postérieure à la date actuelle
          raise_application_error(-20001, 'La date d''acquisition du logement ne peut pas être ultérieure à la date actuelle.'); end if;
    end;
/

-------------------------- GARAGE -------------------------------------

-- Déclencheur pour vérifier que la date d'acquisition du bien n'est pas ultérieure à la date actuelle
create or replace trigger checkGarageDateAcqui before insert or update on Garage
    for each row
    begin
       if :NEW.DateAcqui > SYSDATE then
          -- Si la la date d'acquisition est postérieure à la date actuelle
          raise_application_error(-20001, 'La date d''acquisition du garage ne peut pas être ultérieure à la date actuelle.'); end if;
    end;
/

---------------------- IMMEUBLE ----------------------

-- Déclencheur pour vérifier qu'un immeuble de type "maison" ne contient qu'un seul logement
create or replace trigger checkImmeubleMais before insert or update on Immeuble
    for each row
    begin   
        if :NEW.TypeImmeuble = 'Maison' then
                -- Si le nombre de logements est supérieur à 1
                if :NEW.NbLogements > 1 then
                    raise_application_error(-20023, 'Un immeuble de type "maison" ne peut contenir qu''un seul logement.'); end if; end if;
        end;
/

---------------------- DIAGNOSTIC ----------------------

-- Déclencheur pour vérifier que la date de validité du diagnostic est ultérieure à la date actuelle
create or replace trigger checkDiagnosticDate before insert or update on Diagnostic
    for each row
    begin
        if :NEW.DateDiagnostic <= SYSDATE then
          -- Si la date de validité est antérieure ou égale à l'actuelle
          raise_application_error(-20001, 'La date de validité doit être ultérieure à la date actuelle.'); end if;
    end;
/

---------------------- QUITTANCES ----------------------

-- Déclencheur pour attribuer une valeur par défaut à la date du dernier paiement si elle est NULL
create or replace trigger checkQuittancesDefaultDate before insert on Quittances
    for each row
    declare
        v_date_debut_location DATE;
    begin
        select DateDebut into v_date_debut_location from Contrat_de_location where Contrat_de_location.Id_Contrat_de_location=:NEW.Id_Contrat_de_location; --Récupération date de début de location
        
          if v_date_debut_location is null then
            -- Si la date est null, attribuer la valeur de la date de début de location à la date de la dernière régularisation
            :NEW.DateDerPaiement := v_date_debut_location; end if;
    end;
/

-- Déclencheur pour vérifier que la date de la dernière régularisation n'est pas ultérieure à la date actuelle lors de l'insertion ou de la mise à jour
create or replace trigger checkQuittancesDerPaie before insert on Quittances
    for each row
    begin
       if :NEW.DateDerPaiement > SYSDATE then
          -- Si la date de la dernière régularisation est postérieure à l'actuelle
          raise_application_error(-20001, 'La date de la dernière régularisation ne peut pas être ultérieure à la date actuelle.'); end if;
    end;
/

---------------------- CONTRAT DE LOCATION ----------------------

-- Déclencheur pour vérifier que la date de début de location n'est pas ultérieure à l'actuelle
create or replace trigger checkDateDeb before insert or update on Contrat_de_location
    for each row
    begin
       if :NEW.DateDebut > SYSDATE then
          -- Si la date de début de location est ultérieure à l'actuelle
          raise_application_error(-20001, 'La date de début de location ne peut pas être ultérieure à la date actuelle.'); end if;
    end;
/


---------------------- FACTURE ----------------------

-- Déclencheur pour vérifier que la date d'émission de la facture n'est pas ultérieure à l'actuelle
create or replace trigger checkDateFact before insert or update on Facture
    for each row
    begin
       if :NEW.DateFacture > SYSDATE then
          -- Si la date de la facture est postérieure à la date actuelle
          raise_application_error(-20001, 'La date de la facture ne peut pas être ultérieure à la date actuelle.'); end if;
    end;
/

-- Déclencheur pour vérifier que la date de paiement de la facture n'est pas ultérieure à l'actuelle
create or replace trigger checkDatePaie before insert on Facture
    for each row
    begin
       if :NEW.DateFacture > SYSDATE then
        -- Si la date de paiement est ultérieure à l'actuelle
          raise_application_error(-20001, 'La date de paiement ne peut pas être ultérieure à la date actuelle.'); end if;
    end;
/

-- Déclencheur pour vérifier que la date d'émission de la facture
create or replace trigger checkDateFactPaie before insert or update on Facture
    for each row
    begin
       if :NEW.DateFacture > :NEW.DatePaiement then 
       -- Si la date de facturation est supérieure à la date de paiement
          raise_application_error(-20002, 'La date de paiement ne peut être antérieure à la date de facturation.'); end if;
    end;
/

---------------------- INDEX COMPTEUR ----------------------

-- Déclencheur pour vérifier que la date du relevé des compteurs
create or replace trigger checkDateReleve before insert on Index_Compteur
    for each row 
    begin
       if :NEW.DateReleve > SYSDATE then 
       -- Si la date est supérieure à l'actuelle
          raise_application_error(-20001, 'La date du relevé ne peut pas être ultérieure à la date actuelle.'); end if;
    end;
/

---------------------- TAXE ----------------------

-- Déclencheur pour vérifier les années d'impôt
create or replace trigger checkAnneeImpot before insert on Taxe
for each row
    declare
        v_AnneeActuelle INT;
    begin    
        v_AnneeActuelle := TO_NUMBER(TO_CHAR(SYSDATE, 'YYY')); -- Obtenir l'année actuelle
        
        if :NEW.DateTaxe >= v_AnneeActuelle then 
        -- Si la date est supérieure à l'année précédante, annuler l'insertion
            raise_application_error(-20001, 'L''année de l''impôt ne peut pas être supérieur à la dernière année.'); end if;
    end;
/
