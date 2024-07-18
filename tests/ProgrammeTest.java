package tests;


import org.junit.Test;
import zoot.Programme;
import zoot.arbre.expressions.ConstanteEntiere;
import zoot.tds.Entree;
import zoot.tds.EntreeVariable;
import zoot.tds.SymboleVariable;
import zoot.tds.TDS;
import zoot.types.Booleen;
import zoot.types.Entier;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ProgrammeTest {

    @Test
    public void debutProgramme() {

        TDS.getInstance().ajouter(new EntreeVariable("a", 0), new SymboleVariable(new Entier(), "a"));
        TDS.getInstance().ajouter(new EntreeVariable("b", 0), new SymboleVariable(new Booleen(), "b"));

        Programme p = new Programme();

        StringBuilder sb = new StringBuilder();
        sb.append(".data\n");
        sb.append("vrai:\t.asciiz \"vrai\"\n");
        sb.append("faux:\t.asciiz \"faux\"\n");
        sb.append(".text\n");
        sb.append("main:\n");
        sb.append("# initialiser s7 avec sp (initialisation de la base des variables)\n");
        sb.append("move $s7,$sp\n");
        sb.append("# reservation de l'espace pour les variables\n");
        sb.append("addi $sp, $sp, ").append("-8").append("\n\n");

        assertEquals(sb.toString(), p.debutProgramme());
    }

    @Test
    public void finProgramme() {

        Programme p = new Programme();

        StringBuilder sb = new StringBuilder();
        sb.append("end:\n");
        sb.append("# fin du programme\n");
        sb.append("li $v0, 10      # retour au systeme\n");
        sb.append("syscall\n");

        List<String> registres = new ArrayList<String>(4);
        registres.add("v0");
        registres.add("t1");
        registres.add("t2");
        registres.add("t3");

        assertEquals(sb.toString(), p.finProgramme(registres));
    }
}