package zoot;

import zoot.analyse.AnalyseurLexical;
import zoot.analyse.AnalyseurSyntaxique;
import zoot.arbre.ArbreAbstrait;
import zoot.exceptions.AnalyseException;
import zoot.exceptions.AnalyseSemantiqueException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Zoot {
    
    public Zoot(String nomFichier) {
        try {
            StringBuilder message = new StringBuilder();

            AnalyseurSyntaxique analyseur = new AnalyseurSyntaxique(new AnalyseurLexical(new FileReader(nomFichier)));
            ArbreAbstrait arbre = (ArbreAbstrait) analyseur.parse().value;

            try {
                Programme.getInstance().verifierFonctions();
            }catch (Exception ex) {
                message.append(ex.getMessage());
            }

            try {
                arbre.verifier() ;
            }catch (Exception ex) {
                message.append(ex.getMessage());
            }

            if(message.length() > 0)
                throw new AnalyseException(message.toString()) {};

            System.out.println("COMPILATION OK");

            List<String> registres = new ArrayList<String>(4);
            registres.add("$v0");
            registres.add("$t1");
            registres.add("$t2");
            registres.add("$t3");

            String nomSortie = nomFichier.replaceAll("[.]zoot", ".mips") ;
            PrintWriter flot = new PrintWriter(new BufferedWriter(new FileWriter(nomSortie))) ;
            flot.println(Programme.getInstance().debutProgramme());
            flot.println(arbre.toMIPS(registres));
            flot.println(Programme.getInstance().finProgramme(registres));
            flot.close() ;
        }
        catch (FileNotFoundException ex) {
            System.err.println("Fichier " + nomFichier + " inexistant") ;
        }
        catch (AnalyseException ex) {
            System.err.println(ex.getMessage());
        }
        catch (Exception ex) {
            Logger.getLogger(Zoot.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Nombre incorrect d'arguments") ;
            System.err.println("\tjava -jar zoot.jar <fichierSource.zoot>") ;
            System.exit(1) ;
        }
        new Zoot(args[0]) ;
    }
    
}
