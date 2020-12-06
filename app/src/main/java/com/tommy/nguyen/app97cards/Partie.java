package com.tommy.nguyen.app97cards;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Partie {

    int nombreDeCartes = 97;
    List<Integer> listeDeCartes = new ArrayList<>();

    public Partie() {}

    // Initialise les cartes au debut
    public List<Integer> initialiserListeCartes (List<Integer> listeDeCartes) {
        for (int i = 1; i <= nombreDeCartes; i++) {
            listeDeCartes.add(i);
        }
        return listeDeCartes;
    }

    // Melanger l'order des cartes dans la liste
    public List<Integer> melangerListeCartes(List<Integer> listeDeCartes) {
        // https://docs.oracle.com/javase/6/docs/api/java/util/Collections.html
        Collections.shuffle(listeDeCartes);
        return listeDeCartes;
    }

    public void placerCarteAleatoire(List<Integer> listeDeCartes, LinearLayout carte) {
        Partie partie = new Partie();
        listeDeCartes = partie.melangerListeCartes(listeDeCartes);
        int valeurCarteAleatoire = listeDeCartes.remove(0);
        ((TextView) carte.getChildAt(0)).setText(Integer.toString(valeurCarteAleatoire));
    }

    // Verifie si on peut placer une carte sur une pile croissante
    public boolean validerPileCroissante(int carteSelectionne, int carteSurLaPile) {
        if(carteSelectionne > carteSurLaPile)
            return true;
        else if (carteSelectionne == carteSurLaPile - 10)
            return true;
        else
            return false;
    }

    // Verifie si on peut placer une carte sur une pile decroissante
    public boolean validerPileDecroissante(int carteSelectionne, int carteSurLaPile) {
        if (carteSelectionne < carteSurLaPile)
            return true;
        else if (carteSelectionne == carteSurLaPile + 10)
            return true;
        else
            return false;
    }

    public void regrouperValeurCartes (Context context, LinearLayout carte, List<Integer> listeCartes) {
        TextView textView = new TextView(context);
        textView = (TextView) carte.getChildAt(0);
        String valeurCarte = textView.getText().toString();
        listeCartes.add(Integer.parseInt(valeurCarte));
    }

    //pour verifier la condition d'arret de jeu, on verifie toute les cartes en main et tout les carte su le board
    public boolean verifCondArret(List<Integer> listeCartesJoueur, List<Integer> listeCartesCroissantes,List<Integer> listeCartesDecroissantes) {
        boolean firstHalf = false;
        boolean secondHalf = false;
        for (int i = 0; i < listeCartesJoueur.size(); i++) {
            for (int j = 0; j < listeCartesCroissantes.size(); j++)
            {
                if (listeCartesJoueur.get(i) > listeCartesCroissantes.get(j)) {
                    firstHalf = true;
                }
//                else {
//                    firstHalf = false;
//                }
                System.out.println("firstHalf : " + firstHalf + " i: " + i + " j: " + j);
            }
        }
        for (int i = 0; i < listeCartesJoueur.size(); i++) {
            for (int j = 0; j < listeCartesDecroissantes.size(); j++)
            {
                if (listeCartesJoueur.get(i) < listeCartesDecroissantes.get(j)) {
                    secondHalf = true;
                }
//                else
//                    secondHalf = false;
                System.out.println("secondHalf : " + secondHalf + " i: " + i + " j: " + j);
            }
        }
        System.out.println("firstHalf: " + firstHalf);
        System.out.println("secondHalf: " + secondHalf);
        System.out.println("firstHalf == true && secondHalf == true : " + (firstHalf == true && secondHalf == true));
        if (firstHalf == true || secondHalf == true) {
            System.out.println("verifCondArret returned: FALSE");
            return false;

        }
        System.out.println("verifCondArret returned: TRUE");
        return true;

    }

    public List<Integer> getListeDeCartes() {
        return listeDeCartes;
    }

}
