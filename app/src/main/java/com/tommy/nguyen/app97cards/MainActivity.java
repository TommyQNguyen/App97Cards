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

    TextView pointage;

    ConstraintLayout containerPilesCartes;
    ConstraintLayout containerCartesJoueur;

    LinearLayout pileCroissante_1;
    LinearLayout pileCroissante_2;
    LinearLayout pileDecroissante_1;
    LinearLayout pileDecroissante_2;

    LinearLayout carte1;
    LinearLayout carte2;
    LinearLayout carte3;
    LinearLayout carte4;
    LinearLayout carte5;
    LinearLayout carte6;
    LinearLayout carte7;
    LinearLayout carte8;

    Intent intent;
    // https://developer.android.com/reference/android/widget/Chronometer.html
    Chronometer chronometre;
    int points;

    List<Integer> listeCartesJoueur = new ArrayList<Integer>();
    List<Integer> listeCartesCroissant = new ArrayList<Integer>();
    List<Integer> listeCartesDecroissant = new ArrayList<Integer>();

    Partie partie = new Partie();
    Ecouteur ec;

    List<Integer> listeDeCartes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        containerPilesCartes = findViewById(R.id.containerPilesCartes);
        containerCartesJoueur = findViewById(R.id.containerCartesJoueur);

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


//        intent = new Intent(MainActivity.this, Conclusion.class);
        points = 0;

        listeDeCartes = partie.initialiserListeCartes(listeDeCartes);
        ec = new Ecouteur();

        // pour ajouter les ecouteurs au linear layouts et pour set les texts de debut de la partie superieur
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

//        listeDeCartes = partie.melangerListeCartes(listeDeCartes);
//        int valeurCarteAleatoire = listeDeCartes.remove(0);
//        ((TextView) carte1.getChildAt(0)).setText(Integer.toString(valeurCarteAleatoire));
        partie.placerCarteAleatoire(listeDeCartes, carte1);
        partie.placerCarteAleatoire(listeDeCartes, carte2);
        partie.placerCarteAleatoire(listeDeCartes, carte3);
        partie.placerCarteAleatoire(listeDeCartes, carte4);
        partie.placerCarteAleatoire(listeDeCartes, carte5);
        partie.placerCarteAleatoire(listeDeCartes, carte6);
        partie.placerCarteAleatoire(listeDeCartes, carte7);
        partie.placerCarteAleatoire(listeDeCartes, carte8);
    }

    private class Ecouteur implements View.OnTouchListener, View.OnDragListener {

        @Override
        public boolean onTouch(View source, MotionEvent event) {

            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(source);
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

                    //deux textviews placeholders pour les textviews quon va devoir modifier
                    TextView ancien = new TextView(MainActivity.this);
                    TextView nouveau = new TextView(MainActivity.this);
                    String selectedValue;
                    String boardValue;
                    // basic swapping pour sauvegarde des données avant de les changers
                    LinearLayout maCarte = (LinearLayout) event.getLocalState();
                    TextView textSelectioner = (TextView) maCarte.getChildAt(0);
                    ancien.setText(textSelectioner.getText());
                    // Recuperer la colonne de destination
                    LinearLayout destination = (LinearLayout) source;
                    TextView textDrop = (TextView)destination.getChildAt(0);
                    selectedValue = ancien.getText().toString();
                    boardValue =  textDrop.getText().toString();

                    //verification de si on place une carte au bon endroit
                    boolean validationMouvement = false;
                    if (destination == pileCroissante_1 || destination == pileCroissante_2) {
                        validationMouvement =
                                partie.validerPileCroissante(Integer.parseInt(selectedValue), Integer.parseInt(boardValue));
                    }
                    if (destination == pileDecroissante_1 || destination == pileDecroissante_2) {
                        validationMouvement =
                                partie.validerPileDecroissante(Integer.parseInt(selectedValue), Integer.parseInt(boardValue));
                    }

                    // si c'est un mouvement valide, on fait le swap des texts
                    if (validationMouvement == true) {
                        maCarte.removeView(textSelectioner);    // Enlever la carte de sa colonne d'origine
                        destination.removeView(textDrop);
                        partie.melangerListeCartes(listeDeCartes);
                        nouveau.setTextSize(36);
                        nouveau.setTextColor(Color.WHITE);
                        nouveau.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        nouveau.setText(Integer.toString(listeDeCartes.get(0)));

                        ancien.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        ancien.setTextColor(Color.WHITE);
                        ancien.setTextSize(36);
                        // Ajouter la carte sur sa colonne de destination
                        destination.addView(ancien);
                        maCarte.addView(nouveau);
                        //arret du chrono pour savoir le temps et quel score donner
                        chronometre.stop();

                        // Algorithme de distribution de points
                        int calculPointage = (int) (SystemClock.elapsedRealtime() - chronometre.getBase());
                        chronometre.setBase(SystemClock.elapsedRealtime());
                        chronometre.start();
                        if (calculPointage > 10000)
                            points = points + 1;
                        else if (calculPointage > 5000)
                            points = points + 5;
                        else if (calculPointage > 1000)
                            points = points + 10;
                        // Affichage du nouveau pointage
                        pointage.setText(String.valueOf(points));
                    }

                    // trois liste qu'on verifie a chaque tours pour voir si c la fin de partie
                    listeCartesJoueur.removeAll(listeCartesJoueur);

                    partie.regrouperValeurCartes(MainActivity.this, carte1, listeCartesJoueur);
                    partie.regrouperValeurCartes(MainActivity.this, carte2, listeCartesJoueur);
                    partie.regrouperValeurCartes(MainActivity.this, carte3, listeCartesJoueur);
                    partie.regrouperValeurCartes(MainActivity.this, carte4, listeCartesJoueur);
                    partie.regrouperValeurCartes(MainActivity.this, carte5, listeCartesJoueur);
                    partie.regrouperValeurCartes(MainActivity.this, carte6, listeCartesJoueur);
                    partie.regrouperValeurCartes(MainActivity.this, carte7, listeCartesJoueur);
                    partie.regrouperValeurCartes(MainActivity.this, carte8, listeCartesJoueur);

                    listeCartesCroissant.removeAll(listeCartesCroissant);

                    partie.regrouperValeurCartes(MainActivity.this, pileCroissante_1, listeCartesCroissant);
                    partie.regrouperValeurCartes(MainActivity.this, pileCroissante_2, listeCartesCroissant);

                    listeCartesDecroissant.removeAll(listeCartesDecroissant);

                    partie.regrouperValeurCartes(MainActivity.this, pileDecroissante_1, listeCartesDecroissant);
                    partie.regrouperValeurCartes(MainActivity.this, pileDecroissante_2, listeCartesDecroissant);
                    System.out.println(listeCartesJoueur);
                    System.out.println(listeCartesCroissant);
                    System.out.println(listeCartesDecroissant);
                    // c'est la fin de la partie si on ne peut plus rien jouer (verification des trois liste)
                    boolean finPartie = partie.verifCondArret(listeCartesJoueur, listeCartesCroissant, listeCartesDecroissant);
                    if(finPartie == true) {
                        intent = new Intent(MainActivity.this, Conclusion.class);
                        intent.putExtra("points", Integer.toString(points));
                        startActivity(intent);
                    }
                    if (listeDeCartes.isEmpty()) {
                        intent.putExtra("points", points);
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