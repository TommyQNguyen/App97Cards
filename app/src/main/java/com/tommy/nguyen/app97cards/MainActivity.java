package com.tommy.nguyen.app97cards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int pointsProximite = 0;
    TextView nombreDeCartes, pointage;
    ConstraintLayout containerPilesCartes, containerCartesJoueur;
    LinearLayout pileCroissante_1, pileCroissante_2, pileDecroissante_1, pileDecroissante_2;
    LinearLayout carte1, carte2, carte3, carte4, carte5, carte6, carte7, carte8;
    // https://developer.android.com/reference/android/widget/Chronometer.html
    Chronometer chronometre;
    Intent intent;
    Partie partie;
    Ecouteur ec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperer les composantes
        containerPilesCartes = findViewById(R.id.containerPilesCartes);
        containerCartesJoueur = findViewById(R.id.containerCartesJoueur);
        nombreDeCartes = findViewById(R.id.nombreDeCartes);
        chronometre = findViewById(R.id.chronometre);
        pointage = findViewById(R.id.pointage);
        pileCroissante_1 = findViewById(R.id.pileCroissante_1);
        pileCroissante_2 = findViewById(R.id.pileCroissante_2);
        pileDecroissante_1 = findViewById(R.id.pileDecroissante_1);
        pileDecroissante_2 = findViewById(R.id.pileDecroissante_2);
        carte1 = findViewById(R.id.carte1);
        carte2 = findViewById(R.id.carte2);
        carte3 = findViewById(R.id.carte3);
        carte4 = findViewById(R.id.carte4);
        carte5 = findViewById(R.id.carte5);
        carte6 = findViewById(R.id.carte6);
        carte7 = findViewById(R.id.carte7);
        carte8 = findViewById(R.id.carte8);

        // Etape 1
        ec = new Ecouteur();
        partie = new Partie();

        // Etape 2
        pileCroissante_1.setOnDragListener(ec);
        pileCroissante_2.setOnDragListener(ec);
        pileDecroissante_1.setOnDragListener(ec);
        pileDecroissante_2.setOnDragListener(ec);
        carte1.setOnTouchListener(ec);
        carte1.setOnDragListener(ec);
        carte2.setOnTouchListener(ec);
        carte2.setOnDragListener(ec);
        carte3.setOnTouchListener(ec);
        carte3.setOnDragListener(ec);
        carte4.setOnTouchListener(ec);
        carte4.setOnDragListener(ec);
        carte5.setOnTouchListener(ec);
        carte5.setOnDragListener(ec);
        carte6.setOnTouchListener(ec);
        carte6.setOnDragListener(ec);
        carte7.setOnTouchListener(ec);
        carte7.setOnDragListener(ec);
        carte8.setOnTouchListener(ec);
        carte8.setOnDragListener(ec);

        // Distribuer les cartes a l'utilisateur
        partie.placerCarteAleatoire(carte1);
        partie.placerCarteAleatoire(carte2);
        partie.placerCarteAleatoire(carte3);
        partie.placerCarteAleatoire(carte4);
        partie.placerCarteAleatoire(carte5);
        partie.placerCarteAleatoire(carte6);
        partie.placerCarteAleatoire(carte7);
        partie.placerCarteAleatoire(carte8);
    }

    // Etape 3
    private class Ecouteur implements View.OnTouchListener, View.OnDragListener {

        @Override
        public boolean onTouch(View source, MotionEvent event) {
            // Parametre source est la source de l'evenement, c'est la carte sur laquelle on touche
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(source);
            // Pour permettre de deplacer l'ombre de la carte (carte: 3e parametre, local state)
            source.startDrag(null, shadowBuilder, source, 0);
            return true;
        }

        @Override
        public boolean onDrag(View source, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    // Pour mettre en contexte:
                    // Suggestion d'Eric que chaque pile aie son propre conteneur mais
                    // qu'egalement chaque carte du jeu (8) aie egalement son conteneur.

                    // Deux valeurs temporaires pour garder l'information des cartes jouees
                    // La premiere represente la carte pigee par l'utilisateur
                    // La deuxieme represente celle qui sera ajoute au prochain tour
                    TextView carteJoueurPige = new TextView(MainActivity.this);
                    carteJoueurPige.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    carteJoueurPige.setTextColor(Color.WHITE);
                    carteJoueurPige.setTextSize(40);

                    TextView carteJoueurAjoute = new TextView(MainActivity.this);
                    carteJoueurAjoute.setTextSize(40);
                    carteJoueurAjoute.setTextColor(Color.WHITE);
                    carteJoueurAjoute.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    // Pour pouvoir entreposer la carte choisie par l'utilisateur
                    // dans la valeur temporaire carteJoueurPige declaree ci-dessus
                    LinearLayout conteneurCarteJoueur = (LinearLayout) event.getLocalState();
                    TextView carteJoueurChoisie = (TextView) conteneurCarteJoueur.getChildAt(0);
                    carteJoueurPige.setText(carteJoueurChoisie.getText());
                    String valeurCartePige = carteJoueurPige.getText().toString();

                    // Pour pouvoir identifier la pile sur laquelle le joueur depose
                    // sa carte et extraire la valeur sur la pile pendant le meme tour
                    LinearLayout conteneurPileChoisie = (LinearLayout) source;
                    TextView cartePileChoisie = (TextView) conteneurPileChoisie.getChildAt(0);
                    String valeurPileChoisie =  cartePileChoisie.getText().toString();

                    // Si l'utilisateur depose la carte pigee sur une pile croissante,
                    // valider le mouvement de la pile croissante, vice-versa pour decroissante
                    if (conteneurPileChoisie == pileCroissante_1 || conteneurPileChoisie == pileCroissante_2) {
                        partie.setMouvementLegal(
                                partie.validerPileCroissante(Integer.parseInt(valeurCartePige), Integer.parseInt(valeurPileChoisie)));
                        pointsProximite = partie.getBASE_POINTS() - (Integer.parseInt(valeurCartePige) - Integer.parseInt(valeurPileChoisie));
                    }
                    else if (conteneurPileChoisie == pileDecroissante_1 || conteneurPileChoisie == pileDecroissante_2) {
                        partie.setMouvementLegal(
                                partie.validerPileDecroissante(Integer.parseInt(valeurCartePige), Integer.parseInt(valeurPileChoisie)));
                        pointsProximite = partie.getBASE_POINTS() - (Integer.parseInt(valeurPileChoisie) - Integer.parseInt(valeurCartePige));
                    }

                    if (partie.isMouvementLegal() == true) {
                        // Enlever les carte de leurs piles d'origine
                        conteneurCarteJoueur.removeView(carteJoueurChoisie);
                        conteneurPileChoisie.removeView(cartePileChoisie);

                        // Retirer une carte de la liste de cartes pour remplacer
                        // celle qui a ete pigee par l'utilisateur
                        carteJoueurAjoute.setText(Integer.toString(partie.getListeDeCartes().get(0)));
                        partie.getListeDeCartes().remove(0);
                        conteneurCarteJoueur.addView(carteJoueurAjoute);

                        // Ajouter la carte pigee sur la pile choisie
                        // par l'utilisateur
                        conteneurPileChoisie.addView(carteJoueurPige);

                        // Ici, on arrete temporairement le temps pour
                        // recueillir les millisecondes annulees
                        chronometre.stop();
                        int millisecondesEcoulees = (int) (SystemClock.elapsedRealtime() - chronometre.getBase());
                        chronometre.setBase(SystemClock.elapsedRealtime());
                        chronometre.start();

                        System.out.println("SystemClock.elapsedRealtime(): " + SystemClock.elapsedRealtime());
                        System.out.println("chronometre.getBase(): " + chronometre.getBase());

                        partie.calculerPoints(millisecondesEcoulees, pointsProximite);

                        // Affichage du nouveau pointage
                        pointage.setText(String.valueOf(partie.getNombreDePoints()));
                    }
                    nombreDeCartes.setText(String.valueOf(partie.getNombreDeCartes()));

                    partie.viderListesDeCartes();
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte1);
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte2);
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte3);
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte4);
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte5);
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte6);
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte7);
                    partie.extraireValeurCarteJoueur(MainActivity.this, carte8);
                    partie.extraireValeurPileCroissante(MainActivity.this, pileCroissante_1);
                    partie.extraireValeurPileCroissante(MainActivity.this, pileCroissante_1);
                    partie.extraireValeurPileDecroissante(MainActivity.this, pileDecroissante_1);
                    partie.extraireValeurPileDecroissante(MainActivity.this, pileDecroissante_2);

                    partie.setFinDeLaPartie(partie.validerMouvementPossible());
                    if (partie.isFinDeLaPartie() == true) {
                        intent = new Intent(MainActivity.this, Conclusion.class);
                        intent.putExtra("points", Integer.toString(partie.getNombreDePoints()));
                        startActivity(intent);
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    } // Fin de la classe Ecouteur
}