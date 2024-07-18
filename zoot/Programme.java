package zoot;

import zoot.arbre.ArbreAbstrait;
import zoot.arbre.BlocDInstructions;
import zoot.exceptions.AnalyseException;
import zoot.tds.TDS;

import java.util.ArrayList;
import java.util.List;

public class Programme {

    private static Programme instance = new Programme();

    private ArrayList<BlocDInstructions> fonctions;

    public Programme(){

        fonctions = new ArrayList<>();
    }

    public static Programme getInstance(){ return instance;}

    public BlocDInstructions getFonctionActuelle() {
        return fonctions.get(TDS.getInstance().getBlocActuel()-1);
    }

    public void ajouterFonction(BlocDInstructions f){
        fonctions.add(f);
    }

    public void verifierFonctions() throws AnalyseException{

        StringBuilder message = new StringBuilder();

        for(BlocDInstructions b : fonctions){
            TDS.getInstance().setNoBlocActuel(b.getNoBloc());
            try {
                b.verifier();
            }catch (AnalyseException ex) {
                message.append(ex.getMessage());
            }
            TDS.getInstance().setNoBlocActuel(0);
        }

        if(message.length() > 0)
            throw new AnalyseException(message.toString()) {};
    }

    public String debutProgramme(){

        TDS.getInstance().setNoBlocActuel(0);
        StringBuilder sb = new StringBuilder();
        sb.append(".data\n");
        sb.append("vrai:\t.asciiz \"vrai\"\n");
        sb.append("faux:\t.asciiz \"faux\"\n");
        sb.append(".text\n");
        sb.append("main:\n");
        sb.append("move $s3,$sp\n");
        sb.append("addi $sp,$sp,-12\n");
        sb.append("# initialiser s7 avec sp (initialisation de la base des variables)\n");
        sb.append("move $s7,$sp\n");
        sb.append("# reservation de l'espace pour les variables\n");
        sb.append("addi $sp, $sp, -").append(TDS.getInstance().getTailleZoneVariables()).append("\n\n");

        return sb.toString();
    }

    public String finProgramme(List<String> registres){

        StringBuilder sb = new StringBuilder();
        sb.append("end:\n");
        sb.append("# fin du programme\n");
        sb.append("li $v0, 10      # retour au systeme\n");
        sb.append("syscall\n");

        sb.append(toMIPSFonctions(registres));

        return sb.toString();
    }

    private String toMIPSFonctions(List<String> registres){

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < fonctions.size(); i++) {
            TDS.getInstance().setNoBlocActuel(fonctions.get(i).getNoBloc());

            sb.append("\netif" + (i+1) + ":\n");

            sb.append("sw $ra, 0($sp) #sauvegarde de l'adresse de retour\n");
            sb.append("sw $s7, -4($sp) #sauvegarde du chainage dynamique\n");
            sb.append("addi $sp, $sp, -8 #mise à jour du sommet de pile\n");
            sb.append("move $s7, $sp #mise à jour de la base locale\n");
            sb.append("addi $sp, $sp, -"+TDS.getInstance().getTailleZoneVariables()+" #réserver la place pour les variables locales\n");
            sb.append("addi $sp, $sp, -4 #mise à jour du sommet de pile\n");

            sb.append("#instructions de la fonction\n");

            sb.append(fonctions.get(i).toMIPS(registres));
        }

        return sb.toString();
    }
}
