package fr.clement.game.sudoku;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Random;


public class Main extends Activity {

    String Lancement = "[Debug] Lancement du Sudoku en cours..";
    String Femeture  = "[Debug] Fermeture du Sudoku en cours..";
    String Lecture   = "[Debug] Lecture de la ligne ";
    String Ouverture = "[Debug] Erreur à l'ouverture du fichier : ";
    String VerifOk   = "[Debug] Le sudoku est correct !";
    String VerifOff  = "[Debug] Le sudoku est incorrect !";
    String Case      = "[Debug] Case numéro : ";
    String CasePris  = "[Debug] Case prise par : ";
    String Remplir   = "[Debug] Remplissement de la case par : ";

    String Fichier = "list_problem_1.txt";

    int seconde = 0;
    int minute = 0;
    int heure = 0;
    int seconde2 = 0;
    int minute2 = 0;
    int heure2 = 0;

    boolean pause = false;
    boolean clic = false;
    boolean verif = false;
    boolean horizonverif = false;
    boolean verticalverif = false;



    int tableausudo[] = {R.id.table1, R.id.table2, R.id.table3, R.id.table4, R.id.table5, R.id.table6, R.id.table7, R.id.table8, R.id.table9, R.id.table10, R.id.table11, R.id.table12, R.id.table13, R.id.table14, R.id.table15, R.id.table16, R.id.table17, R.id.table18, R.id.table19, R.id.table20, R.id.table21, R.id.table22, R.id.table23, R.id.table24, R.id.table25, R.id.table26, R.id.table27, R.id.table28, R.id.table29, R.id.table30, R.id.table31, R.id.table32, R.id.table33, R.id.table34, R.id.table35, R.id.table36, R.id.table37, R.id.table38, R.id.table39, R.id.table40, R.id.table41, R.id.table42, R.id.table43, R.id.table44, R.id.table45, R.id.table46, R.id.table47, R.id.table48, R.id.table49, R.id.table50, R.id.table51, R.id.table52, R.id.table53, R.id.table54, R.id.table55, R.id.table56, R.id.table57, R.id.table58, R.id.table59, R.id.table60, R.id.table61, R.id.table62, R.id.table63, R.id.table64, R.id.table65, R.id.table66, R.id.table67, R.id.table68, R.id.table69, R.id.table70, R.id.table71, R.id.table72, R.id.table73, R.id.table74, R.id.table75, R.id.table76, R.id.table77, R.id.table78, R.id.table79, R.id.table80, R.id.table81};

    int tableaugrille[] = new int[100];

    int horizon[][] = new int[9][9];

    int vertical[][] = new int[9][9];

    Random r = new Random();

    String cases;
    int resultat;

    int grille = 0;

    private Handler myHandler;

    int avant = 0;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println(Femeture);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (myHandler != null)
            myHandler.removeCallbacks(myRunnable);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tout);
        System.out.println(Lancement);
        init();
        if (myHandler == null) {
            myHandler = new Handler();
            myHandler.postDelayed(myRunnable, 1000);
        }

    }


    private Runnable myRunnable = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {

            if (!pause) {

                ++seconde;

                if (seconde == 10) {
                    ++seconde2;
                    seconde = 0;
                    if (seconde2 == 6) {
                        ++minute;
                        seconde2 = 0;
                        if (minute == 10) {
                            ++minute2;
                            minute = 0;
                            if (minute2 == 6) {
                                ++heure;
                                minute2 = 0;
                                if (heure == 10) {
                                    ++heure2;
                                    heure = 0;
                                }
                            }
                        }
                    }
                }

                TextView edt = findViewById(R.id.timer);

                edt.setText(String.format("%d%d:%d%d:%d%d", heure2, heure, minute2, minute, seconde2, seconde));
                edt.setTextSize(25);
                edt.setTextColor(Color.GREEN);
            }
            myHandler.postDelayed(this, 1);
        }
    };

    public void init() {
        heure2 = 0;
        heure = 0;
        minute2 = 0;
        minute = 0;
        seconde2 = 0;
        seconde = 0;
        String chaine = tirage(grille);

        for (int boucle = 0; boucle < 81; ++boucle) {
            TextView edt = findViewById(tableausudo[boucle]);

            tableaugrille[boucle] = Integer.parseInt("" + chaine.charAt(boucle));


            if (!chaine.substring(boucle, boucle + 1).equals("0")) {
                edt.setText(String.format("%s", chaine.charAt(boucle)));

            } else {
                edt.setText("");
            }
            edt.setTextSize(45);
            edt.setGravity(Gravity.CENTER);
            edt.setTextColor(Color.YELLOW);
        }
    }

    public String tirage(int nombre) {
        try {
            InputStream is = getAssets().open(Fichier);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;

            for (int compte = 0; (line = br.readLine()) != null; ++compte) {
                if (compte == nombre) {
                    System.out.println(Lecture + compte);
                    return line;
                }
            }
        } catch (IOException e) {
            System.out.println(Ouverture + e);
        }
        return null;
    }

    @SuppressLint("DefaultLocale")
    public boolean onTouchEvent(MotionEvent event) {

        if (!pause) {

            if (event.getAction() == MotionEvent.ACTION_UP) {


                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);

                double largeurMax = metrics.widthPixels;

                double hauteurMax = (metrics.heightPixels / 100) * 75;

                double debutTableau = (metrics.heightPixels / 100) * 10;

                double largeurCase = (metrics.widthPixels / 100) * 10.8;

                double hauteurCase = (hauteurMax / 100) * 10.8;

                double separateurGrosL = (metrics.widthPixels / 100) * 0.833;

                double separateurPetitL = (metrics.widthPixels / 100) * 0.166;

                double separateurGrosH = (metrics.heightPixels / 100) * 0.833;

                double separateurPetitH = (metrics.heightPixels / 100) * 0.166;

                double hauteurNombre = (metrics.heightPixels / 100) * 93;
                double largeurNombre = (metrics.widthPixels / 100) * 11.5;

                if ((event.getX() > 1 && event.getX() < metrics.widthPixels - 1) && (event.getY() > hauteurNombre && event.getY() < metrics.heightPixels - 1)) {
                    for (int nombrelarge = 0; nombrelarge <= 9; ++nombrelarge) {
                        if ((event.getX() > (largeurNombre * nombrelarge)) && (event.getX() < (largeurNombre * (nombrelarge + 1)))) {
                            if (clic) {

                                TextView edt = findViewById(tableausudo[resultat]);

                                if (edt.getText().equals("*") || (tableaugrille[resultat] == 0)) {

                                    edt.setText(String.format("%d", nombrelarge + 1));
                                    edt.setTextSize(45);
                                    edt.setGravity(Gravity.CENTER);

                                    if (verif) {
                                        edt.setTextColor(Color.MAGENTA);
                                    } else {
                                        edt.setTextColor(Color.RED);
                                    }



                                    System.out.println(Remplir + edt.getText());
                                }
                            }
                            break;
                        }
                    }

                    if (verif()){
                        ImageButton button = findViewById(R.id.correction);
                        button.setEnabled(true);
                        button.setImageDrawable(getDrawable(R.drawable.correction));
                    }
                    else {
                        ImageButton button = findViewById(R.id.correction);
                        button.setEnabled(false);
                        button.setImageDrawable(getDrawable(R.drawable.rien));
                    }

                }

                if ((event.getX() > separateurGrosL && event.getX() < largeurMax - separateurGrosL) && (event.getY() > (debutTableau + separateurGrosH) && event.getY() < (hauteurCase * 9) + (separateurGrosH * GrosTrai(3)) + (separateurPetitH * PetitTrai(6) + debutTableau))) {


                    for (int nombrelarge = 0; nombrelarge <= 9; ++nombrelarge) {
                        if ((event.getX() > (separateurGrosL)) && (event.getX() < 20 + (largeurCase * nombrelarge) + (separateurGrosL * GrosTrai(nombrelarge)) + (separateurPetitL * PetitTrai(nombrelarge)))) {
                            cases = nombrelarge + "";
                            break;
                        }
                    }
                    for (int nombre = 0; nombre <= 9; ++nombre) {
                        if (event.getY() > (separateurGrosH + debutTableau) && event.getY() < (hauteurCase * nombre) + (separateurGrosH * GrosTrai(nombre)) + (separateurPetitH * PetitTrai(nombre) + debutTableau)) {
                            cases = nombre + cases;
                            break;
                        }
                    }

                    resultat = Integer.parseInt(cases);
                    resultat = resultat - 10;
                    resultat = MultipleNeuf(resultat);

                    resultat = resultat - 1;

                    clic = true;


                    if (avant != resultat) {
                        TextView edt = findViewById(tableausudo[avant]);
                        if (edt.getText().equals("*")) {

                            edt.setText("");
                            edt.setTextSize(45);
                            edt.setGravity(Gravity.CENTER);
                            edt.setTextColor(Color.YELLOW);
                        } else if (tableaugrille[avant] == 0) {

                            edt.setTextColor(Color.MAGENTA);
                        }
                    } else {
                        verif = false;
                    }

                    if (resultat >= 0) {
                        TextView edt = findViewById(tableausudo[resultat]);

                        if (edt.getText().equals("")) {
                            System.out.println(Case + resultat);

                            edt.setText("*");
                            edt.setTextSize(45);
                            edt.setGravity(Gravity.CENTER);
                            edt.setTextColor(Color.RED);
                        } else {
                            System.out.println(CasePris + tableaugrille[resultat]);
                            if (tableaugrille[resultat] == 0) {
                                edt.setTextColor(Color.RED);
                            }
                        }

                    }

                    avant = resultat;

                }


            }
        }
        return true;
    }

    public int PetitTrai(int nombre) {
        if (nombre == 1) {
            return 1;
        } else if (nombre == 2 || nombre == 3) {
            return 2;
        } else if (nombre == 4) {
            return 3;
        } else if (nombre == 5 || nombre == 6) {
            return 4;
        } else if (nombre == 7) {
            return 5;
        } else if (nombre == 8) {
            return 6;
        } else {
            return 0;
        }
    }

    public boolean verif(){
        for (int correction = 0; correction <= 80; ++correction) {
            TextView edt = findViewById(tableausudo[correction]);
            if ((!edt.getText().equals("*")) && (!edt.getText().equals(""))){
                if (correction == 80){
                  return true;
                }
            }
            else {
                return false;
            }
        }
        return false;
    }

    public int GrosTrai(int nombre) {
        if (nombre <= 2) {
            return 1;
        } else if (nombre <= 5) {
            return 2;
        } else {
            return 3;
        }
    }

    public int MultipleNeuf(int nombre) {

        int calcul = (nombre) / 10;


        for (int boucle = 0; boucle <= 9; ++boucle) {
            if (calcul == boucle) {
                return (nombre - (boucle));
            }
        }

        return 0;
    }

    public void click_menu(View view) {

        finish();
    }
    @SuppressLint("DefaultLocale")
    public void click_reload(View view) {
        init();

        ImageButton buttone = findViewById(R.id.correction);
        buttone.setEnabled(false);
        buttone.setImageDrawable(getDrawable(R.drawable.rien));

        ImageButton button = findViewById(R.id.onoff);
        if (pause) {
            button.setImageDrawable(getDrawable(R.drawable.pause));
            pause = false;
        }
        TextView edt = findViewById(R.id.timer);
        edt.setText(String.format("%d%d:%d%d:%d%d", 0, 0, 0, 0, 0, 0));
        edt.setTextSize(25);
        edt.setTextColor(Color.RED);
    }

    public void click_pause(View view) {
        ImageButton button = findViewById(R.id.onoff);

        if (!pause){
            button.setImageDrawable(getDrawable(R.drawable.playe));
            pause = true;
        }
        else {
            button.setImageDrawable(getDrawable(R.drawable.pause));
            pause = false;
        }
    }

    @SuppressLint("DefaultLocale")
    public void click_correct(View view){

        ImageButton button = findViewById(R.id.correction);

        button.setEnabled(false);

        int largeur = 0;
        int point = 0;

        verticalverif = false;
        horizonverif = false;


        for (int boucle = 0; boucle < 81; ++boucle) {
            TextView edt = findViewById(tableausudo[boucle]);

            if (point >= 9) {
                point = 0;
                ++largeur;
            }
            String info = (String) edt.getText();

            horizon[largeur][point] = Integer.parseInt(info);

            ++point;

        }

        String ez;

        for (int boucle = 0; boucle <= 8; ++boucle) {
            ez = "";
            for (int bouclee = 0; bouclee <= 8; ++bouclee) {
                ez = String.format("%s%d", ez, horizon[boucle][bouclee]);
            }

            if (ez.contains(1 + "") && ez.contains(2 + "") && ez.contains(3 + "") && ez.contains(4 + "") && ez.contains(5 + "") && ez.contains(6 + "") && ez.contains(7 + "") && ez.contains(8 + "") && ez.contains(9 + "")) {
                if (boucle == 8){
                    horizonverif = true;
                    break;
                }
            }
            else {
                horizonverif = false;
                break;
            }
        }

        for (int boucle = 0; boucle <= 8; ++boucle) {
            int ezz = 0;

            for (int bouclee = 0; bouclee <= 8; ++bouclee) {

                if (bouclee == 0) {
                    ezz = boucle;
                } else {
                    ezz = ezz + 9;
                }
                TextView edt = findViewById(tableausudo[ezz]);

                String info = (String) edt.getText();

                vertical[boucle][bouclee] = Integer.parseInt(info);
            }
        }
        String za;

        for (int boucle = 0; boucle <= 8; ++boucle) {
            za = "";
            for (int bouclee = 0; bouclee <= 8; ++bouclee) {
                za = String.format("%s%d", za, vertical[boucle][bouclee]);
            }
            if (za.contains(1 + "") && za.contains(2 + "") && za.contains(3 + "") && za.contains(4 + "") && za.contains(5 + "") && za.contains(6 + "") && za.contains(7 + "") && za.contains(8 + "") && za.contains(9 + "")) {
                if (boucle == 8){
                    verticalverif = true;
                    break;
                }
            }
            else {
                verticalverif = false;
                break;
            }
        }
        if (horizonverif && verticalverif){
            System.out.println(VerifOk);
        }
        else {
            System.out.println(VerifOff);
        }
    }
}