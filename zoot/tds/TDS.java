package zoot.tds;

import zoot.exceptions.AnalyseSemantiqueException;
import zoot.exceptions.AnalyseSyntaxiqueException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TDS {

    private static TDS instance = new TDS();

    private ArrayList<HashMap<Entree, Symbole>> tds;
    private int deplacement;
    private int noDernierBloc;
    private int noBlocActuel;

    private TDS(){

        deplacement = 0;
        tds = new ArrayList<HashMap<Entree, Symbole>>();
        noDernierBloc = 0;
        noBlocActuel = 0;
        tds.add(new HashMap<>());
    }

    public static TDS getInstance(){ return instance;}

    public void ajouter(Entree e, Symbole s){

        /*
        System.out.println(e.getIdf() + " : " + e.getBloc());
        for(int i = 0; i < tds.size(); i++){
            System.out.println("Hashmap "+i+" :\n");
            for(Map.Entry entry : tds.get(i).entrySet()){
                System.out.println("clé : " + entry.getKey() + " | valeur : " + entry.getValue() + "\n");
            }
        }
        */
        if(tds.get(e.getBloc()).containsKey(e)) {

            throw new AnalyseSemantiqueException(0, "double declaration de l'identifiant " + e);
        }
        tds.get(e.getBloc()).put(e, s);

        if(s.estUneVariable())
            deplacement -= 4;
    }

    public Symbole identifier(Entree e) {

        /*
        System.out.println("Identification de " + e.getIdf() + " : bloc " + e.getBloc());
        for(int i = 0; i < tds.size(); i++){
            System.out.println("Hashmap "+i+" :\n");
            for(Map.Entry entry : tds.get(i).entrySet()){
                System.out.println("clé : " + entry.getKey() + " | valeur : " + entry.getValue() + "\n");
            }
        }
        */
        return tds.get(e.getBloc()).get(e);
    }

    public void entreeBloc(){
        noBlocActuel = noDernierBloc + 1;
        deplacement = 0;
        tds.add(new HashMap<>());

        //System.out.println("On entre dans le bloc " + noBlocActuel);
    }

    public void sortieBloc(){
        noDernierBloc = noBlocActuel;
        noBlocActuel = 0; // On revient forcément dans le bloc principal à la sortie du bloc de la fonction
    }

    public int getDeplacement(){
        return this.deplacement;
    }

    public int getBlocActuel(){
        return this.noBlocActuel;
    }

    public void setNoBlocActuel(int noBlocActuel) {
        this.noBlocActuel = noBlocActuel;
    }

    public int getTailleZoneVariables(){
        int taille = 0;
        for(Symbole s : tds.get(noBlocActuel).values()){
            if(s.estUneVariable())
                taille+=4;
        }

        if(noBlocActuel != 0) {
            for (Symbole s : tds.get(0).values()) {
                if (s.estUneFonction()) {
                    if (((SymboleFonction) s).getEtiquette() == noBlocActuel) {
                        taille -= ((SymboleFonction) s).getNbParams() * 4;
                    }
                }
            }
        }
        return taille;
    }
}
