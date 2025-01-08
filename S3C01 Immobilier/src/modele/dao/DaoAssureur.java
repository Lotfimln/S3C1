package modele.dao;

import java.util.ArrayList;
import java.util.List;

import modele.Assureur;

public class DaoAssureur{

    // Méthode simulant la récupération des assureurs
    public static List<Assureur> recupererAssureurs() {
        // Simulation de données
        List<Assureur> assureurs = new ArrayList<>();
        assureurs.add(new Assureur(1, "Assureur A", new java.util.Date(), 2));
        assureurs.add(new Assureur(2, "Assureur B", new java.util.Date(), 2));
        assureurs.add(new Assureur(3, "Assureur C", new java.util.Date(), 2));
        return assureurs;
    }
}
