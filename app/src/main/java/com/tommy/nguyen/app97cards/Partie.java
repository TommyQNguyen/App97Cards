package com.tommy.nguyen.app97cards;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partie {
    private final int BASE_POINTS = 100;
    private int nombreDeCartes = 97, nombreDePoints = 0;
    private boolean mouvementLegal = false, finDeLaPartie = false;
    private List<Integer> listeDeCartes, listeCartesJoueur, listeCartesCroissantes, listeCartesDecroissantes;

    public Partie() {
        listeDeCartes = new ArrayList<>();
        listeCartesJoueur = new ArrayList<>();
        listeCartesCroissantes = new ArrayList<>();
        listeCartesDecroissantes = new ArrayList<>();

        for (int i = 1; i <= nombreDeCartes; i++)
            listeDeCartes.add(i);

        // https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html
        Collections.shuffle(listeDeCartes);
    }

    // Au debut du jeu, placer une carte au hasard pour le joueur
    public void placerCarteAleatoire(LinearLayout carte) {
        ((TextView) carte.getChildAt(0)).setText(Integer.toString(listeDeCartes.remove(0)));
    }

    // Valider si le joueur peut placer sa carte sur une des piles croissantes
    public boolean validerPileCroissante(int carteSelectionne, int carteSurLaPile) {
        boolean plusGrandQuePile = carteSelectionne > carteSurLaPile;
        boolean moinsDe10 = carteSelectionne == carteSurLaPile - 10;
        if (plusGrandQuePile || moinsDe10) {
            listeDeCartes.remove(Integer.valueOf(carteSelectionne));
            nombreDeCartes = nombreDeCartes - 1;
            return true;
        }
        else {
            return false;
        }
    }

    // Verifie si le joueur peut placer sa carte sur une des piles decroissantes
    public boolean validerPileDecroissante(int carteSelectionne, int carteSurLaPile) {
        boolean plusPetitQuePile = carteSelectionne < carteSurLaPile;
        boolean plusDe10 = carteSelectionne == carteSurLaPile + 10;
        if (plusPetitQuePile || plusDe10) {
            listeDeCartes.remove(Integer.valueOf(carteSelectionne));
            nombreDeCartes = nombreDeCartes - 1;
            return true;
        }
        else {
            return false;
        }
    }

    // Vide les listes de cartes pour analyser
    // l'etat de la partie a chaque nouveau tour.
    public void viderListesDeCartes() {
        listeCartesJoueur.clear();
        listeCartesCroissantes.clear();
        listeCartesDecroissantes.clear();
    }

    // Extraction des valeurs de leurs conteneurs pour ensuite
    // les analyser.
    public void extraireValeurCarteJoueur (Context context, LinearLayout carte) {
        TextView textView = new TextView(context);
        textView = (TextView) carte.getChildAt(0);
        String valeurCarte = textView.getText().toString();
        listeCartesJoueur.add(Integer.parseInt(valeurCarte));
    }

    public void extraireValeurPileCroissante (Context context, LinearLayout carte) {
        TextView textView = new TextView(context);
        textView = (TextView) carte.getChildAt(0);
        String valeurCarte = textView.getText().toString();
        listeCartesCroissantes.add(Integer.parseInt(valeurCarte));
    }

    public void extraireValeurPileDecroissante (Context context, LinearLayout carte) {
        TextView textView = new TextView(context);
        textView = (TextView) carte.getChildAt(0);
        String valeurCarte = textView.getText().toString();
        listeCartesDecroissantes.add(Integer.parseInt(valeurCarte));
    }

    // Analyser les cartes du joueur pour voir s'il peut continuer la partie.
    // Retourne vrai si c'est impossible de faire un autre mouvement.
    public boolean validerMouvementImpossible() {
        boolean mouvementPossiblePileCroissante = false, mouvementPossiblePileDecroissante = false;
        for (int i = 0; i < listeCartesJoueur.size(); i++) {
            for (int j = 0; j < listeCartesCroissantes.size(); j++)
            {
                boolean plusGrandQuePile = listeCartesJoueur.get(i) > listeCartesCroissantes.get(j);
                boolean plusPetitQuePile = listeCartesJoueur.get(i) < listeCartesDecroissantes.get(j);
                boolean valeurPlusPetitDe10 = listeCartesJoueur.get(i) == listeCartesJoueur.get(i) - 10;
                boolean valeurPlusGrandDe10 = listeCartesJoueur.get(i) == listeCartesJoueur.get(i) + 10;
                if (plusGrandQuePile || valeurPlusPetitDe10) {
                    mouvementPossiblePileCroissante = true;
                }
                if (plusPetitQuePile || valeurPlusGrandDe10) {
                    mouvementPossiblePileDecroissante = true;
                }
            }
        }
        if (mouvementPossiblePileCroissante == true || mouvementPossiblePileDecroissante == true) {
            return false;
        }
        else {
            return true;
        }
    }

    public void calculerPoints (int millisecondesEcoulees, int pointsProximite) {
        int pointsCartesRestantes = BASE_POINTS - nombreDeCartes;

        if (millisecondesEcoulees > 10000)
            nombreDePoints = nombreDePoints + 10;
        else if (millisecondesEcoulees > 8000)
            nombreDePoints = nombreDePoints + 20;
        else if (millisecondesEcoulees > 6000)
            nombreDePoints = nombreDePoints + 40;
        else if (millisecondesEcoulees > 4000)
            nombreDePoints = nombreDePoints + 60;
        else if (millisecondesEcoulees > 2000)
            nombreDePoints = nombreDePoints + 80;

        nombreDePoints = nombreDePoints + pointsProximite + pointsCartesRestantes;
    }

    public int getBASE_POINTS() {
        return BASE_POINTS;
    }

    public int getNombreDeCartes() {
        return nombreDeCartes;
    }

    public List<Integer> getListeDeCartes() {
        return listeDeCartes;
    }

    public List<Integer> getListeCartesCroissantes() {
        return listeCartesCroissantes;
    }

    public List<Integer> getListeCartesDecroissantes() {
        return listeCartesDecroissantes;
    }

    public List<Integer> getListeCartesJoueur() {
        return listeCartesJoueur;
    }

    public boolean isMouvementLegal() {
        return mouvementLegal;
    }

    public void setMouvementLegal(boolean mouvementLegal) {
        this.mouvementLegal = mouvementLegal;
    }

    public void setNombreDePoints(int nombreDePoints) {
        this.nombreDePoints = nombreDePoints;
    }

    public int getNombreDePoints() {
        return nombreDePoints;
    }

    public void setFinDeLaPartie(boolean finDeLaPartie) {
        this.finDeLaPartie = finDeLaPartie;
    }

    public boolean isFinDeLaPartie() {
        return finDeLaPartie || nombreDeCartes == 0;
    }
}
