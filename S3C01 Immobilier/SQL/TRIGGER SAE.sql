-------------------------- LOCATAIRE -------------------------------------

-- D�clencheur pour v�rifier la date de naissance du locataire
create or replace trigger checkLocataireDateNais before insert or update on Locataire
    for each row
    begin
       if :NEW.DateNais >= SYSDATE then
          -- Si la date de naissance est post�rieure ou �gale � l'actuelle
          raise_application_error(-20001, 'La date de naissance ne peut pas �tre �gale ou ult�rieure � la date actuelle.'); end if;
    end;
/

-------------------------- LOGEMENT -------------------------------------

-- D�clencheur pour v�rifier que la date d'acquisition du bien n'est pas ult�rieure � la date actuelle
create or replace trigger checkLogementDateAcqui before insert or update on Logement
    for each row
    begin
       if :NEW.DateAcqui > SYSDATE then
          -- Si la la date d'acquisition est post�rieure � la date actuelle
          raise_application_error(-20001, 'La date d''acquisition du logement ne peut pas �tre ult�rieure � la date actuelle.'); end if;
    end;
/

-------------------------- GARAGE -------------------------------------

-- D�clencheur pour v�rifier que la date d'acquisition du bien n'est pas ult�rieure � la date actuelle
create or replace trigger checkGarageDateAcqui before insert or update on Garage
    for each row
    begin
       if :NEW.DateAcqui > SYSDATE then
          -- Si la la date d'acquisition est post�rieure � la date actuelle
          raise_application_error(-20001, 'La date d''acquisition du garage ne peut pas �tre ult�rieure � la date actuelle.'); end if;
    end;
/

---------------------- IMMEUBLE ----------------------

-- D�clencheur pour v�rifier qu'un immeuble de type "maison" ne contient qu'un seul logement
create or replace trigger checkImmeubleMais before insert or update on Immeuble
    for each row
    begin   
        if :NEW.TypeImmeuble = 'Maison' then
                -- Si le nombre de logements est sup�rieur � 1
                if :NEW.NbLogements > 1 then
                    raise_application_error(-20023, 'Un immeuble de type "maison" ne peut contenir qu''un seul logement.'); end if; end if;
        end;
/

---------------------- DIAGNOSTIC ----------------------

-- D�clencheur pour v�rifier que la date de validit� du diagnostic est ult�rieure � la date actuelle
create or replace trigger checkDiagnosticDate before insert or update on Diagnostic
    for each row
    begin
        if :NEW.DateDiagnostic <= SYSDATE then
          -- Si la date de validit� est ant�rieure ou �gale � l'actuelle
          raise_application_error(-20001, 'La date de validit� doit �tre ult�rieure � la date actuelle.'); end if;
    end;
/

---------------------- QUITTANCES ----------------------

-- D�clencheur pour attribuer une valeur par d�faut � la date du dernier paiement si elle est NULL
create or replace trigger checkQuittancesDefaultDate before insert on Quittances
    for each row
    declare
        v_date_debut_location DATE;
    begin
        select DateDebut into v_date_debut_location from Contrat_de_location where Contrat_de_location.Id_Contrat_de_location=:NEW.Id_Contrat_de_location; --R�cup�ration date de d�but de location
        
          if v_date_debut_location is null then
            -- Si la date est null, attribuer la valeur de la date de d�but de location � la date de la derni�re r�gularisation
            :NEW.DateDerPaiement := v_date_debut_location; end if;
    end;
/

-- D�clencheur pour v�rifier que la date de la derni�re r�gularisation n'est pas ult�rieure � la date actuelle lors de l'insertion ou de la mise � jour
create or replace trigger checkQuittancesDerPaie before insert on Quittances
    for each row
    begin
       if :NEW.DateDerPaiement > SYSDATE then
          -- Si la date de la derni�re r�gularisation est post�rieure � l'actuelle
          raise_application_error(-20001, 'La date de la derni�re r�gularisation ne peut pas �tre ult�rieure � la date actuelle.'); end if;
    end;
/

---------------------- CONTRAT DE LOCATION ----------------------

-- D�clencheur pour v�rifier que la date de d�but de location n'est pas ult�rieure � l'actuelle
create or replace trigger checkDateDeb before insert or update on Contrat_de_location
    for each row
    begin
       if :NEW.DateDebut > SYSDATE then
          -- Si la date de d�but de location est ult�rieure � l'actuelle
          raise_application_error(-20001, 'La date de d�but de location ne peut pas �tre ult�rieure � la date actuelle.'); end if;
    end;
/


---------------------- FACTURE ----------------------

-- D�clencheur pour v�rifier que la date d'�mission de la facture n'est pas ult�rieure � l'actuelle
create or replace trigger checkDateFact before insert or update on Facture
    for each row
    begin
       if :NEW.DateFacture > SYSDATE then
          -- Si la date de la facture est post�rieure � la date actuelle
          raise_application_error(-20001, 'La date de la facture ne peut pas �tre ult�rieure � la date actuelle.'); end if;
    end;
/

-- D�clencheur pour v�rifier que la date de paiement de la facture n'est pas ult�rieure � l'actuelle
create or replace trigger checkDatePaie before insert on Facture
    for each row
    begin
       if :NEW.DateFacture > SYSDATE then
        -- Si la date de paiement est ult�rieure � l'actuelle
          raise_application_error(-20001, 'La date de paiement ne peut pas �tre ult�rieure � la date actuelle.'); end if;
    end;
/

-- D�clencheur pour v�rifier que la date d'�mission de la facture
create or replace trigger checkDateFactPaie before insert or update on Facture
    for each row
    begin
       if :NEW.DateFacture > :NEW.DatePaiement then 
       -- Si la date de facturation est sup�rieure � la date de paiement
          raise_application_error(-20002, 'La date de paiement ne peut �tre ant�rieure � la date de facturation.'); end if;
    end;
/

---------------------- INDEX COMPTEUR ----------------------

-- D�clencheur pour v�rifier que la date du relev� des compteurs
create or replace trigger checkDateReleve before insert on Index_Compteur
    for each row 
    begin
       if :NEW.DateReleve > SYSDATE then 
       -- Si la date est sup�rieure � l'actuelle
          raise_application_error(-20001, 'La date du relev� ne peut pas �tre ult�rieure � la date actuelle.'); end if;
    end;
/

---------------------- TAXE ----------------------

-- D�clencheur pour v�rifier les ann�es d'imp�t
create or replace trigger checkAnneeImpot before insert on Taxe
for each row
    declare
        v_AnneeActuelle INT;
    begin    
        v_AnneeActuelle := TO_NUMBER(TO_CHAR(SYSDATE, 'YYY')); -- Obtenir l'ann�e actuelle
        
        if :NEW.DateTaxe >= v_AnneeActuelle then 
        -- Si la date est sup�rieure � l'ann�e pr�c�dante, annuler l'insertion
            raise_application_error(-20001, 'L''ann�e de l''imp�t ne peut pas �tre sup�rieur � la derni�re ann�e.'); end if;
    end;
/
